package ui;

public class Chessboard {
	public static boolean[][] black = new boolean[8][8],
			white = new boolean[8][8];

	public static void initialize() {
		black[3][3] = true;
		black[4][4] = true;
		white[3][4] = true;
		white[4][3] = true;
	}
}
