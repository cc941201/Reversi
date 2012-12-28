package ai;

import ui.Coordinate;

public class Judge {
	public static boolean Can(Coordinate c, boolean[][] yours, boolean[][] enemys) {
		if (yours[c.x][c.y] || enemys[c.x][c.y])
			return false;
		if ((c.x < 0) || (c.y < 0) || (c.x > 8) || (c.y > 8))
			return false;
		int i, j;
		int operationX[] = new int[3];
		int operationY[] = new int[3];
		for (i = 0; i < 3; i++) {
			operationX[i] = i - 1;
			operationY[i] = i - 1;
		}
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				if ((i == 1) && (j == 1))
					continue;
				int x = c.x, y = c.y;
				boolean enemysExist = false;
				while ((x + operationX[i] >= 0) && (x + operationX[i] < 8)
						&& (y + operationY[j] >= 0) && (y + operationY[j] < 8)
						&& (enemys[x + operationX[i]][y + operationY[j]])) {
					enemysExist = true;
					x += operationX[i];
					y += operationY[j];
				}
				if ((enemysExist) && (x + operationX[i] >= 0) && (x + operationX[i] < 8)
						&& (y + operationY[j] >= 0) && (y + operationY[j] < 8)
						&& (yours[x + operationX[i]][y + operationY[j]]))
					return true;
			}
		}
		return false;
	}
}
