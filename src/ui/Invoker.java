package ui;

import javax.swing.JOptionPane;

import ai.*;

public class Invoker {
	private AI blackPlayer, whitePlayer;
	public boolean blackIsHuman = false, whiteIsHuman = false;
	public static final String[] list = { "0. 玩家", "1. 随机AI", "2. 简单的贪心AI" };

	public Invoker(String blackPlayerString, String whitePlayerString) {
		int blackPlayerNum = Integer
				.parseInt(blackPlayerString.substring(0, 1)), whitePlayerNum = Integer
				.parseInt(whitePlayerString.substring(0, 1));
		switch (blackPlayerNum) {
		case 0:
			blackIsHuman = true;
			break;
		case 1:
			blackPlayer = new RandomAI();
			break;
		case 2:
			blackPlayer = new GreedAI();
		}
		switch (whitePlayerNum) {
		case 0:
			whiteIsHuman = true;
			break;
		case 1:
			whitePlayer = new RandomAI();
			break;
		case 2:
			whitePlayer = new GreedAI();
		}
	}

	public void invoke(Main frame) {
		if (frame.board.turn)
			if (blackIsHuman) {
				if (frame.history.move > 0)
					frame.infoWindow.undoButton.setEnabled(true);
				frame.controllable = true;
				frame.infoWindow.updateLabel(frame);
			} else {
				Coordinate move = blackPlayer.move(frame.board.black,
						frame.board.white);
				Coordinate[] pieces = Determine.judge(move, frame.board.black,
						frame.board.white);
				if (pieces.length == 0) {
					JOptionPane.showMessageDialog(frame, "黑方AI作弊", "错误",
							JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
				} else
					frame.board.add(frame, move, frame.board.black,
							frame.board.white, pieces);
			}
		else if (whiteIsHuman) {
			if (frame.history.move > 0)
				frame.infoWindow.undoButton.setEnabled(true);
			frame.controllable = true;
			frame.infoWindow.updateLabel(frame);
		} else {
			Coordinate move = whitePlayer.move(frame.board.white,
					frame.board.black);
			Coordinate[] pieces = Determine.judge(move, frame.board.white,
					frame.board.black);
			if (pieces.length == 0) {
				JOptionPane.showMessageDialog(frame, "白方AI作弊", "错误",
						JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
			} else
				frame.board.add(frame, move, frame.board.white,
						frame.board.black, pieces);
		}
	}
}
