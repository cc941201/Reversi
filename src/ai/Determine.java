package ai;

import ui.Coordinate;

public class Determine {
	public static Coordinate[] judge(Coordinate c, boolean[][] yours,
			boolean[][] enemys) {
		Coordinate[] pieces;
		if ((c.x < 0) || (c.x > 7) || (c.y < 0) || (c.y > 7) || yours[c.x][c.y]
				|| enemys[c.x][c.y])
			pieces = new Coordinate[0];
		else {
			boolean[][] direction = new boolean[3][3];
			int x, y, totalNum = 0, maxLineNum = 1, LineNum, n = 0;
			for (int i = -1; i < 2; i++)
				for (int j = -1; j < 2; j++) {
					if ((i == 0) && (j == 0))
						continue;
					x = c.x + i;
					y = c.y + j;
					while ((x >= 0) && (x < 8) && (y >= 0) && (y < 8)
							&& enemys[x][y]) {
						x += i;
						y += j;
					}
					if ((x >= 0) && (x < 8) && (y >= 0) && (y < 8)
							&& yours[x][y]) {
						x = c.x + i;
						y = c.y + j;
						LineNum = 0;
						while ((x >= 0) && (x < 8) && (y >= 0) && (y < 8)
								&& enemys[x][y]) {
							LineNum++;
							x += i;
							y += j;
						}
						if (LineNum != 0) {
							direction[i + 1][j + 1] = true;
							totalNum += LineNum;
							if (LineNum > maxLineNum)
								maxLineNum = LineNum;
						}
					}
				}
			pieces = new Coordinate[totalNum + maxLineNum - 1];
			for (int i = 0; i < maxLineNum; i++) {
				for (int j = -1; j < 2; j++)
					for (int k = -1; k < 2; k++)
						if (direction[j + 1][k + 1]) {
							pieces[n] = new Coordinate(c.x + j * (i + 1), c.y
									+ k * (i + 1));
							n++;
							if (yours[c.x + j * (i + 2)][c.y + k * (i + 2)])
								direction[j + 1][k + 1] = false;
						}
				n++;
			}
		}
		return pieces;
	}

	public static Coordinate[] canPlace(boolean[][] yours, boolean[][] enemys) {
		int num = 0;
		Coordinate[] temp = new Coordinate[64];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				Coordinate c = new Coordinate(i, j);
				if (judge(c, yours, enemys).length != 0) {
					temp[num] = c;
					num++;
				}
			}
		Coordinate[] places = new Coordinate[num];
		for (int i = 0; i < num; i++)
			places[i] = temp[i];
		return places;
	}
}
