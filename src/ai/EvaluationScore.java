package ai;

public class EvaluationScore {
	public static int Score(boolean[][] player){
		int x,y,ans=0;
		if(player[0][0])
			ans+=750;
		if(player[7][0])
			ans+=750;
		if(player[0][7])
			ans+=750;
		if(player[7][7])
			ans+=750;
		for(x=2;x<6;x++){
			if(player[x][0])
				ans+=20;
			if(player[x][7])
				ans+=20;
			if(player[7][x])
				ans+=20;
			if(player[0][x])
				ans+=20;
			if(player[x][1])
				ans+=5;
			if(player[x][6])
				ans+=5;
			if(player[6][x])
				ans+=5;
			if(player[1][x])
				ans+=5;
			for(y=2;y<6;y++){
				if(player[x][y])
					ans++;
			}
		}
		if(player[1][1])
			ans-=55;
		if(player[6][1])
			ans-=55;
		if(player[1][6])
			ans-=55;
		if(player[6][6])
			ans-=55;
		if(player[1][0])
			ans-=24;
		if(player[6][0])
			ans-=24;
		if(player[1][7])
			ans-=24;
		if(player[6][7])
			ans-=24;
		if(player[0][1])
			ans-=24;
		if(player[7][1])
			ans-=24;
		if(player[0][6])
			ans-=24;
		if(player[7][6])
			ans-=24;
		return ans;
	}
}
