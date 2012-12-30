package ai;

import ui.Coordinate;

public class GreedAI implements AI {
	@Override
	public Coordinate move(boolean[][] yours, boolean[][] enemys) {
		int max = 0, maxx = 0, maxy = 0;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				Coordinate[] pieces = Determine.judge(new Coordinate(i, j),
						yours, enemys);
				if (pieces.length > max) {
					max = pieces.length;
					maxx = i;
					maxy = j;
				}
			}
		return new Coordinate(maxx, maxy);
	}
}
