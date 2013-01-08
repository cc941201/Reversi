package ui;

public class RestartThread extends Thread {
	private Main frame;

	public RestartThread(Main frame) {
		this.frame = frame;
	}

	@Override
	public void run() {
		frame.finished = false;
		frame.winner = 0;
		frame.board = frame.initialBoard.clone();
		if (!frame.evaluate) {
			if (frame.startTimer != null)
				frame.startTimer.cancel();
			if (frame.moveTimer != null)
				frame.moveTimer.cancel();
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++)
					frame.panel[i][j].repaint();
		}
		if (!frame.evaluate && !frame.network) {
			frame.history = new History();
			frame.infoWindow.undoButton.setEnabled(false);
		}
		if (!frame.network)
			frame.invoke.invoke(frame);
	}
}
