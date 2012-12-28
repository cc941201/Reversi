package ui;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.layout.*;

@SuppressWarnings("serial")
public class Main extends JFrame {
	private static boolean autoMode;

	public Main() {
		super("黑白棋");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		setMinimumSize(new Dimension(500, 500));

		// Form Layout
		ColumnSpec[] colSpec = new ColumnSpec[8];
		for (int i = 0; i < 8; i++)
			colSpec[i] = ColumnSpec.decode("default:grow");
		RowSpec[] rowSpec = new RowSpec[8];
		for (int i = 0; i < 8; i++)
			rowSpec[i] = RowSpec.decode("default:grow");
		getContentPane().setLayout(new FormLayout(colSpec, rowSpec));

		// Add pieces
		Piece[][] panel = new Piece[8][8];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				panel[i][j] = new Piece(new Coordinate(i, j));
				getContentPane().add(panel[i][j],
						(i + 1) + ", " + (j + 1) + ", fill, fill");
			}
	}

	public static void main(String[] args) {
		// Choose manual or AI
		String player1 = (String) JOptionPane.showInputDialog(null, "黑方：",
				"请选择", JOptionPane.QUESTION_MESSAGE, null, Invoker.list,
				Invoker.list[0]);
		if (player1 == null)
			System.exit(0);
		String player2 = (String) JOptionPane.showInputDialog(null, "白方：",
				"请选择", JOptionPane.QUESTION_MESSAGE, null, Invoker.list,
				Invoker.list[0]);
		if (player2 == null)
			System.exit(0);
		
		// Initialize the players
		autoMode=Invoker.initPlayer(player1,player2);
		
		// Show the chessboard
		Chessboard.initialize();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setIconImage(new ImageIcon("").getImage());
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "启动失败", "错误",
							JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
				}
			}
		});
	}
}
