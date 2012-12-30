package ui;

import javax.swing.JOptionPane;

import ai.*;

public class Invoker {
	private static AI blackPlayer, whitePlayer;
	public static boolean blackIsHuman = false, whiteIsHuman = false;
	public static String[] list = { "0. 玩家", "1. 随机AI", "2. 简单的贪心AI" };

	public static void initPlayer(String blackPlayerString,
			String whitePlayerString) {
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

	public static void invoke(Main frame, Chessboard board) {
		if (board.turn)
			if (blackIsHuman) {
				frame.controllable = true;
				Info.updateLabel(frame);
			}
			else {
				Coordinate move = blackPlayer.move(board.black, board.white);
				Coordinate[] pieces = Determine.judge(move, board.black,
						board.white);
				if (pieces.length == 0) {
					JOptionPane.showMessageDialog(frame, "黑方AI作弊", "错误",
							JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
				} else
					board.add(frame, move, board.black, board.white, pieces);
			}
		else if (whiteIsHuman) {
			frame.controllable = true;
			Info.updateLabel(frame);
		}
		else {
			Coordinate move = whitePlayer.move(board.white, board.black);
			Coordinate[] pieces = Determine.judge(move, board.white,
					board.black);
			if (pieces.length == 0) {
				JOptionPane.showMessageDialog(frame, "白方AI作弊", "错误",
						JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
			} else
				board.add(frame, move, board.white, board.black, pieces);
		}
	}
}
