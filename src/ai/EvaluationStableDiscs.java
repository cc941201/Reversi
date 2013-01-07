package ai;

public class EvaluationStableDiscs {
	public static int Num(boolean[][] player,boolean[][] opp){
		@SuppressWarnings("unused")
		int i,x,y,ans=0;
		for(x=0;x<8;x++){
			for(y=0;y<8;y++){
				if (!player[x][y])
					continue;
				/*if (special(x,y,player,opp)){
					ans++;
					continue;
				}*/
				boolean flag=true;
				boolean direction[] = new boolean[9];
				direction[1] = judgeStable(player,x,y,0,1);
				direction[2] = judgeStable(player,x,y,0,-1);
				if( (y==0)||((y>0)&&(opp[x][y-1])) && ((y==7)||((y<7)&&(opp[x][y+1]))) ){
					direction[1]=true;
					direction[2]=true;					
				}
				direction[3] = judgeStable(player,x,y,1,0);
				direction[4] = judgeStable(player,x,y,-1,0);
				if( (x==0)||((x>0)&&(opp[x-1][y])) && ((x==7)||((x<7)&&(opp[x+1][y]))) ){
					direction[3]=true;
					direction[4]=true;	
				}
				direction[5] = judgeStable(player,x,y,1,1);
				direction[6] = judgeStable(player,x,y,-1,-1);
				if( (y==0)||(x==0)||((y>0)&&(x>0)&&(opp[x-1][y-1])) 
				&& ((y==7)||(x==7)||((y<7)&&(x<7)&&(opp[x+1][y+1]))) ){
					direction[5]=true;
					direction[6]=true;					
				}
				direction[7] = judgeStable(player,x,y,-1,1);
				direction[8] = judgeStable(player,x,y,1,-1);
				if( (y==7)||(x==0)||((y<7)&&(x>0)&&(opp[x-1][y+1])) 
				&& ((y==0)||(x==7)||((y>0)&&(x<7)&&(opp[x+1][y-1]))) ){
					direction[7]=true;
					direction[7]=true;					
				}
				for(i=1;i<5;i++){
					if(!(direction[2*i-1]||direction[2*i])){
						flag=false;
						break;
					}
				}	
				if(flag)
					ans++;					
			}
		}
		return 0;
	}
	public static boolean judgeStable(boolean[][] player,int x,int y,int operationX,int operationY){
		while(inBorder(x,y,operationX,operationY)){
			x+=operationX;
			y+=operationY;
			if(!player[x][y])
				return false;
		}
		return true;
	}
	public static boolean inBorder(int x,int y,int operationX,int operationY){
		if((x+operationX<0)||(x+operationX>7))
			return false;
		if((y+operationY<0)||(y+operationY>7))
			return false;
		return true;
	}
	/*public static boolean special(int x,int y,boolean[][] player,boolean[][] opp){
		
		return false;
	}*/
}
