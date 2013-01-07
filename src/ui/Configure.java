package ui;

import java.io.FileReader;
import java.util.Scanner;

public class Configure {
	public static String[][] read(String name) throws Exception {
		FileReader in = new FileReader(name + ".conf");
		Scanner scan = new Scanner(in);
		int num = 0;
		while (scan.hasNext()) {
			if ((scan.nextLine() != "") && (scan.nextLine() != "")) {
				if (scan.hasNext())
					scan.nextLine();
				num++;
			}
		}
		String[] list = new String[num], listName = new String[num];
		scan.close();
		in.close();
		in = new FileReader(name + ".conf");
		scan = new Scanner(in);
		for (int i = 0; i < num; i++) {
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
}
