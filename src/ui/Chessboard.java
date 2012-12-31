package ui;

import java.util.*;

public class Chessboard {
	// turn: true black, false white
	public boolean turn = true;
	public Coordinate last;
	public int blackNum = 2, whiteNum = 2;
	public boolean[][] black = new boolean[8][8], white = new boolean[8][8];

	public Chessboard() {
		black[3][3] = true;
		black[4][4] = true;
		white[3][4] = true;
		white[4][3] = true;
	}

	public void add(Main frame, Coordinate c, boolean[][] yours,
			boolean[][] enemys, Coordinate[] pieces) {
		frame.history.add(frame);
		yours[c.x][c.y] = true;
		Coordinate temp = last;
		last = c;
		frame.panel[c.x][c.y].repaint();
		if (temp != null)
			frame.panel[temp.x][temp.y].repaint();
		if (turn)
			blackNum++;
		else
			whiteNum++;
		new Timer().schedule(new Move(frame, yours, enemys, pieces), 200);
		frame.infoWindow.updateLabel(frame);
	}

	@Override
	public Chessboard clone() {
		Chessboard board = new Chessboard();
		board.turn = turn;
		board.last = last;
		board.blackNum = blackNum;
		board.whiteNum = whiteNum;
		for (int i = 0; i < 8; i++) {
			board.black[i] = black[i].clone();
			board.white[i] = white[i].clone();
		}
		return board;
	}
}
