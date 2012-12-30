package ui;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

import ai.Determine;

public class MoveTimer extends TimerTask {
	private Main frame;
	private boolean[][] yours;
	private boolean[][] enemys;
	private Coordinate[] pieces;
	private Chessboard board;

	private class RepaintTimer extends TimerTask {
		private JPanel panel;
		private Chessboard board;

		public RepaintTimer(JPanel panel, Chessboard board) {
			this.panel = panel;
			this.board = board;
		}

		@Override
		public void run() {
			if (board.turn) {
				board.blackNum++;
				board.whiteNum--;
			} else {
				board.whiteNum++;
				board.blackNum--;
			}
			panel.repaint();
			Info.updateLabel(frame);
		}
	}

	private class Finish extends TimerTask {
		@Override
		public void run() {
			frame.controllable = false;
			if (Determine.canMove(enemys, yours))
				board.turn = !board.turn;
			else if ((board.emptyNum == 0) || (board.blackNum == 0)
					|| (board.whiteNum == 0)
					|| !Determine.canMove(yours, enemys)) {
				frame.finished = true;
				if (board.blackNum > board.whiteNum)
					frame.winner = 1;
				if (board.blackNum < board.whiteNum)
					frame.winner = -1;
			}
			Info.updateLabel(frame);
			if (!frame.finished)
				Invoker.invoke(frame, board);
		}
	}

	public MoveTimer(Main frame, boolean[][] yours, boolean[][] enemys,
			Coordinate[] pieces, Chessboard board) {
		this.frame = frame;
		this.yours = yours;
		this.enemys = enemys;
		this.pieces = pieces;
		this.board = board;
	}

	@Override
	public void run() {
		int time = 0;
		Timer timer = new Timer();
		for (int i = 0; i < pieces.length; i++)
			if (pieces[i] == null)
				time += 200;
			else {
				yours[pieces[i].x][pieces[i].y] = true;
				enemys[pieces[i].x][pieces[i].y] = false;
				timer.schedule(new RepaintTimer(
						frame.panel[pieces[i].x][pieces[i].y], board), time);
			}
		if (Invoker.blackIsHuman || Invoker.whiteIsHuman)
			time += 100;
		else
			time += 500;
		timer.schedule(new Finish(), time);
	}
}
