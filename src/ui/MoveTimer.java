package ui;

import javax.swing.JPanel;
import java.util.*;

import ai.Determine;

public class MoveTimer extends TimerTask {
	private Main frame;
	private boolean[][] yours;
	private boolean[][] enemys;
	private Coordinate[] pieces;

	private class Repaint extends TimerTask {
		private JPanel panel;

		public Repaint(JPanel panel) {
			this.panel = panel;
		}

		@Override
		public synchronized void run() {
			if (frame.board.turn) {
				frame.board.blackNum++;
				frame.board.whiteNum--;
			} else {
				frame.board.whiteNum++;
				frame.board.blackNum--;
			}
			panel.repaint();
			frame.infoWindow.updateLabel(frame);
		}
	}

	private class Finish extends TimerTask {
		@Override
		public void run() {
			if (Determine.canPlace(enemys, yours).length != 0)
				frame.board.turn = !frame.board.turn;
			else if (Determine.canPlace(yours, enemys).length == 0) {
				frame.finished = true;
				if (frame.board.blackNum > frame.board.whiteNum)
					frame.winner = 1;
				if (frame.board.blackNum < frame.board.whiteNum)
					frame.winner = -1;
				if (!frame.network
						&& (frame.invoke.blackIsHuman || frame.invoke.whiteIsHuman))
					frame.infoWindow.undoButton.setEnabled(true);
				if (frame.network)
					frame.infoWindow.restartButton.setEnabled(true);
			}
			if (frame.network
					&& !frame.finished
					&& ((frame.networkHost == frame.networkHostBlack) == frame.board.turn)) {
				Coordinate[] places;
				if (frame.board.turn)
					places = Determine.canPlace(frame.board.black,
							frame.board.white);
				else
					places = Determine.canPlace(frame.board.white,
							frame.board.black);
				for (int i = 0; i < places.length; i++) {
					frame.panel[places[i].x][places[i].y].canPlace = true;
					frame.panel[places[i].x][places[i].y].repaint();
				}
				frame.controllable = true;
			}
			frame.infoWindow.updateLabel(frame);
			if (!frame.finished && !frame.network)
				frame.invoke.invoke(frame);
		}
	}

	public MoveTimer(Main frame, boolean[][] yours, boolean[][] enemys,
			Coordinate[] pieces) {
		this.frame = frame;
		this.yours = yours;
		this.enemys = enemys;
		this.pieces = pieces;
	}

	@Override
	public void run() {
		int time = 0;
		frame.moveTimer = new Timer();
		for (int i = 0; i < pieces.length; i++)
			if (pieces[i] == null)
				time += 200;
			else {
				yours[pieces[i].x][pieces[i].y] = true;
				enemys[pieces[i].x][pieces[i].y] = false;
				frame.moveTimer.schedule(new Repaint(
						frame.panel[pieces[i].x][pieces[i].y]), time);
			}
		if (frame.invoke.blackIsHuman || frame.invoke.whiteIsHuman)
			time += 100;
		else
			time += 500;
		frame.moveTimer.schedule(new Finish(), time);
	}
}
