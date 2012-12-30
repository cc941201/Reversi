package ui;

import java.util.*;

public class Chessboard {
	// turn: true black, false white
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
		frame.history.add(frame);
		yours[c.x][c.y] = true;
		frame.panel[c.x][c.y].repaint();
		emptyNum--;
		if (turn)
			blackNum++;
		else
			whiteNum++;
		new Timer().schedule(new Move(frame, yours, enemys, pieces), 200);
		frame.infoWindow.updateLabel(frame);
	}
	
	public Chessboard clone() {
		Chessboard board=new Chessboard();
		board.turn=this.turn;
		board.blackNum=this.blackNum;
		board.whiteNum=this.whiteNum;
		board.emptyNum=this.emptyNum;
		for (int i=0;i<8;i++) {
			board.black[i]=this.black[i].clone();
			board.white[i]=this.white[i].clone();
		}
		return board;
	}
}
