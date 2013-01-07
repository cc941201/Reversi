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

	public Chessboard(String name) {
		try {
			FileReader in = new FileReader("Map/" + name + ".map");
			Scanner scan = new Scanner(in);
			int tmpX, tmpY;
			for (tmpX = 0; tmpX < 8; tmpX++) {
				String tmpSt = scan.nextLine();
				for (tmpY = 0; tmpY < 8; tmpY++) {
					if (tmpSt.charAt(tmpY) == '-') {
						blackNum++;
						black[tmpX][tmpY] = true;
					}
					if (tmpSt.charAt(tmpY) == '+') {
						whiteNum++;
						white[tmpX][tmpY] = true;
					}
				}
			}
			scan.close();
			in.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "配置文件错误", "错误",
					JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}

	public Chessboard() {
	}

	public void add(Main frame, Coordinate c, boolean[][] yours,
			boolean[][] enemys, Coordinate[] pieces) {
		yours[c.x][c.y] = true;
		if (turn)
			blackNum++;
		else
			whiteNum++;
		if (frame.evaluate)
			Move.move(frame, yours, enemys, pieces);
		else {
			frame.history.add(frame);
			Coordinate temp = last;
			last = c;
			frame.panel[c.x][c.y].repaint();
			if (temp != null)
				frame.panel[temp.x][temp.y].repaint();
			new Timer().schedule(new MoveTimer(frame, yours, enemys, pieces), 200);
			frame.infoWindow.updateLabel(frame);
		}
	}

	@Override
	public Chessboard clone() {
		Chessboard board = new Chessboard();
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
