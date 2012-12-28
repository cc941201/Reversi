package ai;

import ui.AI;
import ui.Coordinate;

public class RandonAI implements AI {

	@Override
	public Coordinate Move(boolean[][] yours, boolean[][] enemys) {
		int x,y;
		x=0;
		y=1;
		return (new Coordinate(x,y));
	}

}
