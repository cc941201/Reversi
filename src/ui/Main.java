package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jgoodies.forms.layout.*;
import java.util.Timer;
import java.rmi.registry.Registry;

import rmi.Interface;

@SuppressWarnings("serial")
public class Main extends JFrame {
	public PiecePane[][] panel = new PiecePane[8][8];
	public Chessboard board, initialBoard;
	public History history = new History();
	public InfoWindow infoWindow;
	public EvaluateWindow evaluateWindow;
	public Invoker invoke = new Invoker();
	public WaitingWindow waitingWindow;
	public Interface local, remote;
	public Registry hostRegistry, clientRegistry;
	public Timer startTimer, moveTimer;
	public boolean controllable = false, finished = false, evaluate = false,
			showBoard = true, evaluating = true, network = false,
			networkHostBlack = false, networkHost = false;
	// winner: 1 black, -1 white, 0 tie
	public int winner = 0;

	public Main() {
		super("黑白棋");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new ResetThread(Main.this).start();
			}
		});
		setBounds(100, 100, 500, 500);
		setMinimumSize(new Dimension(500, 500));

		// Choose mode
		try {
			new ModeFrame(this);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "配置文件错误", "错误",
					JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}

		getContentPane().add(boardPane());
	}

	public JPanel boardPane() {
		// Form Layout
		ColumnSpec[] colSpec = new ColumnSpec[8];
		for (int i = 0; i < 8; i++)
			colSpec[i] = ColumnSpec.decode("default:grow");
		RowSpec[] rowSpec = new RowSpec[8];
		for (int i = 0; i < 8; i++)
			rowSpec[i] = RowSpec.decode("default:grow");
		JPanel boardPane = new JPanel();
		boardPane.setLayout(new FormLayout(colSpec, rowSpec));

		// Add pieces
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				panel[i][j] = new PiecePane(this, new Coordinate(i, j));
				boardPane.add(panel[i][j], (i + 1) + ", " + (j + 1)
						+ ", fill, fill");
			}

		return boardPane;
	}

	public void start() {
		// Add info frame
		infoWindow = new InfoWindow(this);
		infoWindow.updateLabel(this);

		// Add evaluate frame, and backup chessboard
		if (evaluate) {
			evaluateWindow = new EvaluateWindow(this);
			evaluateWindow.updateLabel(this);
		}

		// Backup chessboard
		initialBoard = board.clone();

		// Invoke
		if (evaluate)
			new EvaluateThread(this).start();
		else if (!network)
			invoke.invoke(this);

		// Show window
		setVisible(true);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		// Launch
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main();
			}
		});
	}
}
