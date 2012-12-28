package ui;

public class Invoker {
	private static AI player1,player2;
	public static boolean p1IsHuman=false,p2IsHuman=false;
	public static String[] list={"0. 玩家","1. 随机的AI","2. 简单的贪心AI"};
	public static boolean initPlayer(String player1,String player2) {
		int p1=Integer.parseInt(player1.substring(0, 1)),p2=Integer.parseInt(player1.substring(0, 1));;
		if (p1==0) p1IsHuman=true;
		if (p2==0) p2IsHuman=true;
		boolean autoMode=false;
		if ((p1!=0)&&(p2!=0)) autoMode=true;
		return autoMode;
	}
}
