package ui;

import java.io.*;
import java.util.Scanner;

public class Configure {
	public static String[][] readAI() throws Exception {
		FileReader in = new FileReader("AI.conf");
		Scanner scan = new Scanner(in);
		int num = 0;
		while (scan.hasNext()) {
			if ((scan.nextLine() != "") && (scan.nextLine() != "")) {
				if (scan.hasNext())
					scan.nextLine();
				num++;
			}
		}
		scan.close();
		in.close();
		in = new FileReader("AI.conf");
		scan = new Scanner(in);
		String[] list = new String[num + 1], listName = new String[num + 1];
		list[0] = "玩家";
		for (int i = 1; i <= num; i++) {
			list[i] = scan.nextLine();
			listName[i] = scan.nextLine();
			if (scan.hasNext())
				scan.nextLine();
		}
		scan.close();
		in.close();
		String[][] conf = { list, listName };
		return conf;
	}

	public static String[] readMapList() throws Exception {
		File directory = new File("Map/");
		File[] mapFile = directory.listFiles(new FileFilter() {
			@Override
			public boolean accept(File e) {
				boolean flag = false;
				if (e.getName().endsWith(".map"))
					flag = true;
				return flag;
			}
		});
		String[] map = new String[mapFile.length + 2];
		map[0] = "默认";
		map[1] = "镜像";
		for (int i = 0; i < mapFile.length; i++) {
			String fileName = mapFile[i].getName();
			map[i + 2] = fileName.substring(0, fileName.length() - 4);
		}
		return map;
	}

	public static void readMap(Chessboard board, String name) throws Exception {
		if (name.equals("默认")) {
			board.blackNum = 2;
			board.whiteNum = 2;
			board.black[3][4] = true;
			board.black[4][3] = true;
			board.white[3][3] = true;
			board.white[4][4] = true;
		} else if (name.equals("镜像")) {
			board.blackNum = 2;
			board.whiteNum = 2;
			board.black[3][3] = true;
			board.black[4][4] = true;
			board.white[3][4] = true;
			board.white[4][3] = true;
		} else {
			FileReader in = new FileReader("Map/" + name + ".map");
			Scanner scan = new Scanner(in);
			int tmpX, tmpY;
			for (tmpX = 0; tmpX < 8; tmpX++) {
				String tmpSt = scan.nextLine();
				for (tmpY = 0; tmpY < 8; tmpY++) {
					if (tmpSt.charAt(tmpY) == '-') {
						board.blackNum++;
						board.black[tmpX][tmpY] = true;
					}
					if (tmpSt.charAt(tmpY) == '+') {
						board.whiteNum++;
						board.white[tmpX][tmpY] = true;
					}
				}
			}
			scan.close();
			in.close();
		}
	}
}
