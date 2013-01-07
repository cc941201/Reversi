package ai;

public class EvaluationPotentialMobility {
	public static int Score(boolean[][] a,boolean[][] b){
		int x,y,ans=0;
		for(x=0;x<8;x++){
			for(y=0;y<8;y++){
				if(!((a[x][y])||(b[x][y]))){
					if(!nearBy(x,y,a))
						ans++;
				}
			}
		}
		return ans;
	}
	public static boolean nearBy(int i,int j,boolean[][] player){
		if(i>0){
			if(player[i-1][j])
				return false;
			if((j>0)&&(player[i-1][j-1]))
				return false;
			if((j<7)&&(player[i-1][j+1]))
				return false;
		}
		if(i<7){
			if(player[i+1][j])
				return false;
			if((j>0)&&(player[i+1][j-1]))
				return false;
			if((j<7)&&(player[i+1][j+1]))
				return false;
		}
		if((j>0)&&(player[i][j-1]))
			return false;
		if((j<7)&&(player[i][j+1]))
			return false;
		return true;
	}
}
