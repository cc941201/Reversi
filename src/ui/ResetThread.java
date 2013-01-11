package ui;

public class ResetThread extends Thread {
	private Main frame;

	public ResetThread(Main frame) {
		this.frame = frame;
	}

	@Override
	public void run() {
		if (frame != null) {
			if (frame.network) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							frame.remote.close();
						} catch (Exception e1) {
						}
					}
				}).start();
				frame.network = false;
			}
			if (frame.evaluate) {
				frame.evaluateWindow.dispose();
				frame.evaluating = false;
			}
			try {
				if (frame.hostRegistry != null)
					frame.hostRegistry.unbind("reversi");
				if (frame.clientRegistry != null)
					frame.clientRegistry.unbind("reversi");
			} catch (Exception e) {
			}
			if (frame.infoWindow != null)
				frame.infoWindow.dispose();
			frame.dispose();
			Main main = new Main();
			main.hostRegistry = frame.hostRegistry;
			main.clientRegistry = frame.clientRegistry;
		} else
			new Main();
	}
}
