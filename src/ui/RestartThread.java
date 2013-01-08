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
		frame.invoke.invoke(frame);
	}
}
