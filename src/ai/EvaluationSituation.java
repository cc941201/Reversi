package ai;

public class EvaluationSituation{
	public static int Score(boolean yours[][],boolean[][] enemys){
		int i,j,ans=0;
		for(i=0;i<8;i++){
			for(j=0;j<8;j++){
				if(yours[i][j]||enemys[i][j])
					ans++;
			}
		}
		return ans;
	}
}
