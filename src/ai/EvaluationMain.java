package ai;

import ui.Coordinate;

public class EvaluationMain implements AI {

	@Override
	public Coordinate move(boolean[][] yours, boolean[][] enemys) {
		if(Determine.judge(new Coordinate(0, 0),yours, enemys).length>0)
			return new Coordinate(0, 0);
		if(Determine.judge(new Coordinate(0, 7),yours, enemys).length>0)
			return new Coordinate(0, 7);
		if(Determine.judge(new Coordinate(7, 0),yours, enemys).length>0)
			return new Coordinate(7, 0);
		if(Determine.judge(new Coordinate(7, 7),yours, enemys).length>0)
			return new Coordinate(7, 7);
		int current = EvaluationSituation.Score(yours,enemys);
		int x,y,ansX = 0,ansY = 0;
		double max=-9999.99;
		int deltaMobility[][] = new int[8][8];
		int deltaStableDiscs[][] = new int[8][8];
		int deltaPotentialMobility[][] = new int[8][8];
		int gainATempo[][] = new int[8][8];
		int gainNum;
		boolean[][] enemysCanMove = new boolean[8][8];
		double value[][] = new double[8][8];
		for(x=0;x<8;x++){
			for(y=0;y<8;y++){
				gainNum = Determine.judge(new Coordinate(x, y), yours, enemys).length;
				if(gainNum==0)
					continue;
				yours[x][y]=true;
				int i,j;
				for(i=0;i<8;i++){
					for(j=0;j<8;j++){
						enemysCanMove[x][y]=
								(Determine.judge(new Coordinate(x, y), enemys, yours).length>0);						
					}
				}
				int yourMobility = EvaluationMobility.Score(yours, enemys);
				int enemysMobility = EvaluationMobility.Score(enemys,yours);
				deltaMobility[x][y] = yourMobility - enemysMobility;
				int yourStableDiscs = EvaluationStableDiscs.Num(yours,enemys);
				int enemysStableDiscs = EvaluationStableDiscs.Num(enemys,yours);
				deltaStableDiscs[x][y] = yourStableDiscs - enemysStableDiscs;
				int yourPotentialMobility = EvaluationPotentialMobility.Score(yours, enemys);
				int enemysPotentialMobility = EvaluationPotentialMobility.Score(enemys, yours);
				deltaPotentialMobility[x][y] = yourPotentialMobility - enemysPotentialMobility;
				int yourGainATempo = EvaluationGainATempo.pass(yours, enemys);
				int enemysGainATempo = EvaluationGainATempo.pass(enemys, yours);
				gainATempo[x][y]=yourGainATempo - enemysGainATempo*3;
				yours[x][y]=false;
				value[x][y]=(double)deltaMobility[x][y]*(64-(double)current)/5
						+(double)(deltaStableDiscs[x][y]*current*current)/5
						+(double)deltaPotentialMobility[x][y]*(double)(64-current)/12
						+(double)gainATempo[x][y]*250+(double)gainNum/2
						+(double)EvaluationScore.Score(yours)-2.5*(double)EvaluationScore.Score(enemysCanMove);
				if(value[x][y]>max){
					max=value[x][y];
					ansX=x;
					ansY=y;
				}
			}
		}
		return new Coordinate(ansX, ansY);
	}

}
