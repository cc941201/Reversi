package ui;

import java.io.FileReader;
import java.util.*;

import javax.swing.JOptionPane;

public class Chessboard {
	// turn: true black, false white
	public boolean turn = true;
	public Coordinate last;
	public int blackNum = 0, whiteNum = 0;
	public boolean[][] black = new boolean[8][8], white = new boolean[8][8];

	public Chessboard(boolean first) {
		if (first)
		try {
			FileReader in = new FileReader("Map.conf");
			Scanner scan = new Scanner(in);
			int num = 0;
			while (scan.hasNext()) {
				if ((scan.nextLine() != "") && (scan.nextLine() != "")) {
					if (scan.hasNext())
						scan.nextLine();
					num++;
				}
			}
			String[] list = new String[num], listMap = new String[num];
			scan.close();
			in.close();
			in = new FileReader("Map.conf");
			scan = new Scanner(in);
			for (int i = 0; i < num; i++) {
				list[i] = i + ". " + scan.nextLine();
				listMap[i] = scan.nextLine();
				if (scan.hasNext())
					scan.nextLine();
			}
			scan.close();
			in.close();
			String map = (String) JOptionPane.showInputDialog(null, "地图：",
					"请选择", JOptionPane.QUESTION_MESSAGE, null, list, list[0]);
			if (map == null)
				System.exit(0);
			int mapNum = Integer.parseInt(map.substring(0, 1));
			
			scan.close();
			in.close();
			in = new FileReader("Map/"+listMap[mapNum]+".map");
			scan = new Scanner(in);
			int tmpX, tmpY;
			for (tmpX = 0; tmpX < 8; tmpX++) {
				String tmpSt = scan.nextLine();
				for (tmpY = 0; tmpY < 8; tmpY++) {
					if (tmpSt.charAt(tmpY) == '-') {
						blackNum++;
						black[tmpX][tmpY]=true;
					}
					if (tmpSt.charAt(tmpY) == '+') {
						whiteNum++;
						white[tmpX][tmpY]=true;
					}
				}
			}
			scan.close();
			in.close();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "配置文件错误", "错误",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void add(Main frame, Coordinate c, boolean[][] yours,
			boolean[][] enemys, Coordinate[] pieces) {
		frame.history.add(frame);
		yours[c.x][c.y] = true;
		Coordinate temp = last;
		last = c;
		frame.panel[c.x][c.y].repaint();
		if (temp != null)
			frame.panel[temp.x][temp.y].repaint();
		if (turn)
			blackNum++;
		else
			whiteNum++;
		new Timer().schedule(new Move(frame, yours, enemys, pieces), 200);
		frame.infoWindow.updateLabel(frame);
	}

	@Override
	public Chessboard clone() {
		Chessboard board = new Chessboard(false);
		board.turn = turn;
		board.last = last;
		board.blackNum = blackNum;
		board.whiteNum = whiteNum;
		for (int i = 0; i < 8; i++) {
			board.black[i] = black[i].clone();
			board.white[i] = white[i].clone();
		}
		return board;
	}
}
