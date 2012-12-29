package ai;

import ui.AI;
import ui.Coordinate;
import ui.Determine;

public class GreedAI implements AI {

	@Override
	public Coordinate Move(boolean[][] yours, boolean[][] enemys) {
		int ans = 0;
		int x = 0, y = 0;
		int i, j, n, m;
		int interest[][] = new int[8][8];
		int operationX[] = new int[3];
		int operationY[] = new int[3];
		for (i = 0; i < 3; i++) {
			operationX[i] = i - 1;
			operationY[i] = i - 1;
		}
		for (i = 0; i < 8; i++) {
			for (j = 0; j < 8; j++) {
				if (Determine.judge(new Coordinate(i, j), yours, enemys).length!=0) {
					interest[i][j] = 0;
					for (n = 0; n < 3; n++) {
						for (m = 0; m < 3; m++) {
							if ((i == n) && (m == 1))
								continue;
							x = i;
							y = j;
							int tmp = 0;
							boolean enemysExist = false;
							while ((x + operationX[n] >= 0)
									&& (x + operationX[n] < 8)
									&& (y + operationY[m] >= 0)
									&& (y + operationY[m] < 8)
									&& (enemys[x + operationX[n]][y
											+ operationY[m]])) {
								enemysExist = true;
								x += operationX[n];
								y += operationY[m];
								tmp++;
							}
							if ((enemysExist) && (x + operationX[i] >= 0) && (x + operationX[i] < 8)
									&& (y + operationY[i] >= 0) && (y + operationY[i] < 8)
									&& (yours[x + operationX[i]][y + operationY[i]]))
								interest[i][j]+=tmp;
						}
					}
					if(interest[i][j]>ans){
						ans = interest[i][j];
						x = i;
						y = j;
					}
				}
			}
		}
		return (new Coordinate(x, y));
	}

}
