package ai;

import ui.Coordinate;
import java.util.Random;

public class Position implements AI {
	public static boolean judgeIt(int a, int b, boolean[][] yours,
			boolean[][] enemys) {
		return (Determine.judge(new Coordinate(a, b), yours, enemys).length > 0);
	}

	public Coordinate move(boolean[][] yours, boolean[][] enemys) {
		//Random rand = new Random();
		int x, y, ans = 0;
		Coordinate[] pieces = Determine.judge(new Coordinate(0, 0), yours,
				enemys);

		// 优先占角
		pieces = Determine.judge(new Coordinate(0, 0), yours, enemys);
		if (pieces.length > 0)
			return new Coordinate(0, 0);
		pieces = Determine.judge(new Coordinate(7, 7), yours, enemys);
		if (pieces.length > 0)
			return new Coordinate(7, 7);
		pieces = Determine.judge(new Coordinate(7, 0), yours, enemys);
		if (pieces.length > 0)
			return new Coordinate(7, 0);
		pieces = Determine.judge(new Coordinate(0, 7), yours, enemys);
		if (pieces.length > 0)
			return new Coordinate(0, 7);

		// 如果已经占角，则优先占与角相邻的点
		if (yours[0][0]) {
			pieces = Determine.judge(new Coordinate(0, 1), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(0, 1);
			pieces = Determine.judge(new Coordinate(1, 0), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(1, 0);
			pieces = Determine.judge(new Coordinate(0, 6), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(0, 6);
			pieces = Determine.judge(new Coordinate(6, 0), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(6, 0);
		}
		if (yours[7][0]) {
			pieces = Determine.judge(new Coordinate(7, 1), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(7, 1);
			pieces = Determine.judge(new Coordinate(6, 0), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(6, 0);
			pieces = Determine.judge(new Coordinate(7, 6), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(7, 6);
			pieces = Determine.judge(new Coordinate(1, 0), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(1, 0);
		}
		if (yours[0][7]) {
			pieces = Determine.judge(new Coordinate(0, 6), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(0, 6);
			pieces = Determine.judge(new Coordinate(1, 7), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(1, 7);
			pieces = Determine.judge(new Coordinate(0, 1), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(0, 1);
			pieces = Determine.judge(new Coordinate(6, 7), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(6, 7);
		}
		if (yours[7][7]) {
			pieces = Determine.judge(new Coordinate(6, 7), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(6, 7);
			pieces = Determine.judge(new Coordinate(7, 6), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(7, 6);
			pieces = Determine.judge(new Coordinate(1, 7), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(1, 7);
			pieces = Determine.judge(new Coordinate(7, 1), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(7, 1);
		}

		// 然后占不与角相邻的边
		for (x = 2; x < 6; x++) {
			pieces = Determine.judge(new Coordinate(0, x), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(0, x);
			pieces = Determine.judge(new Coordinate(7, x), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(7, x);
			pieces = Determine.judge(new Coordinate(x, 0), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(x, 0);
			pieces = Determine.judge(new Coordinate(x, 7), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(x, 7);
		}

		// 如果C点和其相邻的边上的点被占，则占对角线上与角相邻的点
		if (judgeIt(0, 7, yours, enemys)
				&& ((judgeIt(0, 6, yours, enemys) && judgeIt(0, 5, yours,
						enemys)) || (judgeIt(1, 7, yours, enemys) && judgeIt(2,
						7, yours, enemys))))
			return new Coordinate(1, 6);
		if (judgeIt(7, 7, yours, enemys)
				&& ((judgeIt(7, 6, yours, enemys) && judgeIt(7, 5, yours,
						enemys)) || (judgeIt(6, 7, yours, enemys) && judgeIt(5,
						7, yours, enemys))))
			return new Coordinate(6, 6);
		if (judgeIt(0, 0, yours, enemys)
				&& ((judgeIt(0, 1, yours, enemys) && judgeIt(0, 2, yours,
						enemys)) || (judgeIt(1, 0, yours, enemys) && judgeIt(2,
						0, yours, enemys))))
			return new Coordinate(1, 1);
		if (judgeIt(7, 0, yours, enemys)
				&& ((judgeIt(6, 0, yours, enemys) && judgeIt(5, 0, yours,
						enemys)) || (judgeIt(7, 1, yours, enemys) && judgeIt(7,
						2, yours, enemys))))
			return new Coordinate(6, 1);

		// 中间随机
		ans = 0;
		boolean has[][] = new boolean[8][8];
		for (x = 0; x < 8; x++) {
			for (y = 0; y < 8; y++) {
				has[x][y] = (yours[x][y] || enemys[x][y]);
				if (has[x][y])
					ans++;
			}
		}
		while (ans < 16) {
			Random rand = new Random();
			x = rand.nextInt(4) + 2;
			y = rand.nextInt(4) + 2;
			if (!has[x][y]) {
				ans++;
				has[x][y] = true;
				pieces = Determine.judge(new Coordinate(x, y), yours, enemys);
				if (pieces.length > 0) {
					return new Coordinate(x, y);
				}
			}
		}

		// 与边相邻的一排
		for (x = 2; x < 6; x++) {
			pieces = Determine.judge(new Coordinate(1, x), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(1, x);
			pieces = Determine.judge(new Coordinate(6, x), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(6, x);
			pieces = Determine.judge(new Coordinate(x, 1), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(x, 1);
			pieces = Determine.judge(new Coordinate(x, 6), yours, enemys);
			if (pieces.length > 0)
				return new Coordinate(x, 6);
		}

		// 与角相邻的8个点
		pieces = Determine.judge(new Coordinate(0, 1), yours, enemys);
		if (pieces.length > 0)
			return new Coordinate(0, 1);
		pieces = Determine.judge(new Coordinate(7, 1), yours, enemys);
		if (pieces.length > 0)
			return new Coordinate(7, 1);
		pieces = Determine.judge(new Coordinate(1, 0), yours, enemys);
		if (pieces.length > 0)
			return new Coordinate(1, 0);
		pieces = Determine.judge(new Coordinate(1, 7), yours, enemys);
		if (pieces.length > 0)
			return new Coordinate(1, 7);
		pieces = Determine.judge(new Coordinate(0, 1), yours, enemys);
		if (pieces.length > 0)
			return new Coordinate(0, 1);
		pieces = Determine.judge(new Coordinate(7, 6), yours, enemys);
		if (pieces.length > 0)
			return new Coordinate(7, 6);
		pieces = Determine.judge(new Coordinate(6, 0), yours, enemys);
		if (pieces.length > 0)
			return new Coordinate(6, 0);
		pieces = Determine.judge(new Coordinate(6, 7), yours, enemys);
		if (pieces.length > 0)
			return new Coordinate(6, 7);

		// 会被对方抢角
		pieces = Determine.judge(new Coordinate(1, 1), yours, enemys);
		if (pieces.length > 0)
			return new Coordinate(1, 1);
		pieces = Determine.judge(new Coordinate(6, 6), yours, enemys);
		if (pieces.length > 0)
			return new Coordinate(6, 6);
		pieces = Determine.judge(new Coordinate(6, 1), yours, enemys);
		if (pieces.length > 0)
			return new Coordinate(6, 1);
		pieces = Determine.judge(new Coordinate(1, 6), yours, enemys);
		if (pieces.length > 0)
			return new Coordinate(1, 6);

		y = 0;
		for(x=0;x<8;x++){
			for(y=0;y<8;y++){
				if (judgeIt(x,y,yours,enemys))
					return new Coordinate(x, y);
			}
		}
		return new Coordinate(x, y);
	}
}
