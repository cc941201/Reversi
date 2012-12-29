package ui;

public class Chessboard {
	public boolean turn = true;
	public int blackNum = 2, whiteNum = 2, emptyNum = 60;
	public boolean[][] black = new boolean[8][8], white = new boolean[8][8];

	public Chessboard() {
		black[3][3] = true;
		black[4][4] = true;
		white[3][4] = true;
		white[4][3] = true;
	}

	public void add(Main frame, Coordinate c, boolean[][] yours,
			boolean[][] enemys) {
		yours[c.x][c.y] = true;
		frame.panel[c.x][c.y].repaint();
		emptyNum--;
		if (turn)
			blackNum++;
		else
			whiteNum++;
		int x, y;
		for (int i = -1; i < 2; i++)
			for (int j = -1; j < 2; j++) {
				if ((i == 0) && (j == 0))
					continue;
				x = c.x + i;
				y = c.y + j;
				while ((x >= 0) && (x < 8) && (y >= 0) && (y < 8)
						&& enemys[x][y]) {
					x += i;
					y += j;
				}
				if ((x >= 0) && (x < 8) && (y >= 0) && (y < 8) && yours[x][y]) {
					x = c.x + i;
					y = c.y + j;
					while ((x >= 0) && (x < 8) && (y >= 0) && (y < 8)
							&& enemys[x][y]) {
						yours[x][y] = true;
						enemys[x][y] = false;
						frame.panel[x][y].repaint();
						if (turn) {
							blackNum++;
							whiteNum--;
						} else {
							whiteNum++;
							blackNum--;
						}
						x += i;
						y += j;
					}
				}
			}
		turn = !turn;
		Info.updateLabel(frame);
	}
}
