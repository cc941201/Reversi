package ui;

import javax.swing.JOptionPane;

import ai.Determine;

public class RestartThread extends Thread {
	private Main frame;
	private boolean callRemote;

	public RestartThread(Main frame, boolean callRemote) {
		this.frame = frame;
		this.callRemote = callRemote;
	}

	@Override
	public void run() {
		frame.finished = false;
		frame.winner = 0;
		frame.board = frame.initialBoard.clone();
		if (frame.startTimer != null)
			frame.startTimer.cancel();
		if (frame.moveTimer != null)
			frame.moveTimer.cancel();
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (frame.panel[i][j].turn || frame.panel[i][j].focus
						|| frame.panel[i][j].canPlace) {
					frame.panel[i][j].turn = false;
					frame.panel[i][j].focus = false;
					frame.panel[i][j].canPlace = false;
				}
		frame.repaint();
		if (!frame.network) {
			frame.history = new History();
			frame.infoWindow.undoButton.setEnabled(false);
		}
		if (!frame.network)
			frame.invoke.invoke(frame);
		else {
			frame.infoWindow.restartButton.setEnabled(false);
			if (frame.networkHost == frame.networkHostBlack) {
				Coordinate[] places = Determine.canPlace(frame.board.black,
						frame.board.white);
				for (int i = 0; i < places.length; i++) {
					frame.panel[places[i].x][places[i].y].canPlace = true;
					frame.panel[places[i].x][places[i].y].repaint();
				}
				frame.controllable = true;
			}
			frame.infoWindow.updateLabel(frame);
			if (callRemote)
				try {
					frame.remote.restart();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame, "连接中断", "局域网模式错误",
							JOptionPane.ERROR_MESSAGE);
					new ResetThread(frame).start();
				}
		}
	}
}
