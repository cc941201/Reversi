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
			boolean[][] enemys, Coordinate[] pieces) {
		yours[c.x][c.y] = true;
		frame.panel[c.x][c.y].repaint();
		emptyNum--;
		if (turn)
			blackNum++;
		else
			whiteNum++;
		for (int i = 0; i < pieces.length; i++) {
			if (pieces[i] != null) {
				yours[pieces[i].x][pieces[i].y] = true;
				enemys[pieces[i].x][pieces[i].y] = false;
				frame.panel[pieces[i].x][pieces[i].y].turn=false;
				frame.panel[pieces[i].x][pieces[i].y].repaint();
				if (turn) {
					blackNum++;
					whiteNum--;
				} else {
					whiteNum++;
					blackNum--;
				}
			}
		}
		turn = !turn;
		Info.updateLabel(frame);
	}
}
