package ui;

public class History {
	public Chessboard[] board = new Chessboard[62];
	public int move = 0;

	public void add(Main frame) {
		board[move] = frame.board.clone();
		move++;
	}

	public void undo(Main frame) {
		if (frame.finished) {
			frame.finished = false;
			frame.winner = 0;
			if (frame.invoke.blackIsHuman)
				frame.board.turn = true;
			else
				frame.board.turn = false;
		}
		move--;
		frame.board = board[move].clone();
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				if (frame.panel[i][j].turn || frame.panel[i][j].focus
						|| frame.panel[i][j].canPlace) {
					frame.panel[i][j].turn = false;
					frame.panel[i][j].focus = false;
					frame.panel[i][j].canPlace = false;
				}
				frame.panel[i][j].repaint();
			}
		frame.invoke.invoke(frame);
	}
}
