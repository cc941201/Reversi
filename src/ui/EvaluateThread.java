package ui;

import javax.swing.JOptionPane;

import ai.Determine;

class EvaluateThread extends Thread {
	private Main frame;
	private Chessboard board;

	EvaluateThread(Main frame) {
		this.frame = frame;
	}

	@Override
	public void run() {
		while (frame.evaluating) {
			board = frame.initialBoard.clone();
			while (!frame.finished) {
				if (board.turn) {
					Coordinate move = frame.invoke.blackPlayer.move(
							board.black, board.white);
					Coordinate[] pieces = Determine.judge(move, board.black,
							board.white);
					if (pieces.length == 0) {
						JOptionPane.showMessageDialog(frame, "黑方AI作弊", "错误",
								JOptionPane.ERROR_MESSAGE);
						System.exit(-1);
					} else {
						add(frame, move, board.black, board.white, pieces);
						finish(frame, board.black, board.white);
					}
				} else {
					Coordinate move = frame.invoke.whitePlayer.move(
							board.white, board.black);
					Coordinate[] pieces = Determine.judge(move, board.white,
							board.black);
					if (pieces.length == 0) {
						JOptionPane.showMessageDialog(frame, "白方AI作弊", "错误",
								JOptionPane.ERROR_MESSAGE);
						System.exit(-1);
					} else {
						add(frame, move, board.white, board.black, pieces);
						finish(frame, board.white, board.black);
					}
				}
			}
			frame.finished = false;
			frame.winner = 0;
		}
	}

	private void add(Main frame, Coordinate c, boolean[][] yours,
			boolean[][] enemys, Coordinate[] pieces) {
		yours[c.x][c.y] = true;
		if (board.turn)
			board.blackNum++;
		else
			board.whiteNum++;
		for (int i = 0; i < pieces.length; i++)
			if (pieces[i] != null) {
				yours[pieces[i].x][pieces[i].y] = true;
				enemys[pieces[i].x][pieces[i].y] = false;
				if (board.turn) {
					board.blackNum++;
					board.whiteNum--;
				} else {
					board.whiteNum++;
					board.blackNum--;
				}
			}
	}

	private void finish(final Main frame, boolean[][] yours, boolean[][] enemys) {
		if (Determine.canMoveNum(enemys, yours) != 0)
			board.turn = !board.turn;
		else if (Determine.canMoveNum(yours, enemys) == 0) {
			frame.board = board;
			frame.finished = true;
			if (board.blackNum > board.whiteNum)
				frame.winner = 1;
			if (board.blackNum < board.whiteNum)
				frame.winner = -1;
			frame.evaluateWindow.updateLabel(frame);
			if (frame.showBoard) {
				frame.infoWindow.updateLabel(frame);
				frame.repaint();
			}
		}
	}
}
