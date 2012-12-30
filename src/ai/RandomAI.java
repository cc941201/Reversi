package ai;

import java.util.Random;

import ui.Coordinate;

public class RandomAI implements AI {
	@Override
	public Coordinate move(boolean[][] yours, boolean[][] enemys) {
		Random rand = new Random();
		int x, y;
		Coordinate[] pieces;
		do {
			x = rand.nextInt(8);
			y = rand.nextInt(8);
			pieces = Determine.judge(new Coordinate(x, y), yours, enemys);
		} while (pieces.length == 0);
		return new Coordinate(x, y);
	}
}
