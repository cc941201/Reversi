package ui;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.layout.*;

@SuppressWarnings("serial")
public class Main extends JFrame {
	public Piece[][] panel = new Piece[8][8];
	public Chessboard board = new Chessboard();
	public History history = new History();
	public Info infoWindow;
	public Invoker invoke;
	public boolean controllable = false, finished = false;
	// winner: 1 black, -1 white, 0 tie
	public int winner = 0;

	public Main() {
		super("黑白棋");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		setMinimumSize(new Dimension(500, 500));

		// Choose manual or AI
		String blackPlayer = (String) JOptionPane.showInputDialog(null, "黑方：",
				"请选择", JOptionPane.QUESTION_MESSAGE, null, Invoker.list,
				Invoker.list[0]);
		if (blackPlayer == null)
			System.exit(0);
		String whitePlayer = (String) JOptionPane.showInputDialog(null, "白方：",
				"请选择", JOptionPane.QUESTION_MESSAGE, null, Invoker.list,
				Invoker.list[0]);
		if (whitePlayer == null)
			System.exit(0);

		// Initialize the players
		invoke = new Invoker(blackPlayer, whitePlayer);

		// Form Layout
		ColumnSpec[] colSpec = new ColumnSpec[8];
		for (int i = 0; i < 8; i++)
			colSpec[i] = ColumnSpec.decode("default:grow");
		RowSpec[] rowSpec = new RowSpec[8];
		for (int i = 0; i < 8; i++)
			rowSpec[i] = RowSpec.decode("default:grow");
		Container contentPane = getContentPane();
		contentPane.setLayout(new FormLayout(colSpec, rowSpec));

		// Add pieces
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				panel[i][j] = new Piece(this, new Coordinate(i, j));
				contentPane.add(panel[i][j], (i + 1) + ", " + (j + 1)
						+ ", fill, fill");
			}

		// Add info panel
		infoWindow = new Info(this);
		infoWindow.setVisible(true);

		// Invoke
		invoke.invoke(this);
	}

	public static void main(String[] args) {
		// Launch
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Main frame = new Main();
				frame.setVisible(true);
			}
		});
	}
}
