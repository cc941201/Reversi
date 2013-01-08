package ui;

public class EvaluateThread extends Thread {
	private Main frame;

	public EvaluateThread(Main frame) {
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
