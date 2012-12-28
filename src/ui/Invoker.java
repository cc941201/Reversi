package ui;

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
			blackPlayer = new RandonAI();
			break;
		case 2:
			blackPlayer = new GreedAI();
		}
		switch (whitePlayerNum) {
		case 0:
			whiteIsHuman = true;
			break;
		case 1:
			whitePlayer = new RandonAI();
			break;
		case 2:
			whitePlayer = new GreedAI();
		}
	}
}
