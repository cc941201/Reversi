package ui;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Coordinate implements Serializable {
	public int x;
	public int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object a) {
		boolean t = false;
		if ((a instanceof Coordinate) && (((Coordinate) a).x == x)
				&& (((Coordinate) a).y == y))
			t = true;
		return t;
	}
}
