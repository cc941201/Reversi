package ai;

import ui.Coordinate;
import java.util.*;

public class GreedAI implements AI {
	@Override
	public Coordinate move(boolean[][] yours, boolean[][] enemys) {
		int max = 0, maxx = 0, maxy = 0;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				Coordinate[] pieces = Determine.judge(new Coordinate(i, j),
						yours, enemys);
				if (pieces.length == 0)
					continue;
				if (pieces.length == max) {
					Random rand = new Random();
					int tmp = rand.nextInt(2);
					if(tmp%2 == 0){
						maxx = i;
						maxy = j;
					}
				}
				if (pieces.length > max) {
					max = pieces.length;
					maxx = i;
					maxy = j;
				}
			}
		return new Coordinate(maxx, maxy);
	}
}
