package ui;

import java.util.*;

public class Chessboard {
	// turn: true black, false white
	public boolean turn = true;
	public Coordinate last;
	public int blackNum = 0, whiteNum = 0;
	public boolean[][] black = new boolean[8][8], white = new boolean[8][8];

	public Chessboard(String name) throws Exception {
		Configure.readMap(this, name);
	}

	public Chessboard() {
	}

	public void add(Main frame, Coordinate c, boolean[][] yours,
			boolean[][] enemys, Coordinate[] pieces) {
		if (!frame.evaluate && !frame.network)
			frame.history.add(frame);
		yours[c.x][c.y] = true;
		if (turn)
			blackNum++;
		else
			whiteNum++;
		if (frame.evaluate)
			MoveEvaluation.move(frame, yours, enemys, pieces);
		else {
			Coordinate temp = last;
			last = c;
			frame.panel[c.x][c.y].repaint();
			if (temp != null)
				frame.panel[temp.x][temp.y].repaint();
			frame.startTimer = new Timer();
			frame.startTimer.schedule(new MoveTimer(frame, yours, enemys,
					pieces), 200);
			frame.infoWindow.updateLabel(frame);
		}
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
