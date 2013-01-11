package ui;

import javax.swing.JOptionPane;

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
				frame.panel[i][j].repaint();
		if (!frame.network) {
			frame.history = new History();
			frame.infoWindow.undoButton.setEnabled(false);
		}
		frame.infoWindow.restartButton.setEnabled(false);
		if (!frame.network)
			frame.invoke.invoke(frame);
		else {
			frame.infoWindow.restartButton.setEnabled(false);
			if (frame.networkHost == frame.networkHostBlack)
				frame.controllable = true;
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
