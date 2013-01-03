package ui;

import java.io.FileReader;
import java.util.Scanner;
import javax.swing.JOptionPane;

import ai.*;

public class Invoker {
	private AI blackPlayer, whitePlayer;
	public boolean blackIsHuman = false, whiteIsHuman = false;

	public Invoker() {
		try {
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
			String[] list = new String[num + 1], listClass = new String[num + 1];
			list[0] = "0. 玩家";
			scan.close();
			in.close();
			in = new FileReader("AI.conf");
			scan = new Scanner(in);
			for (int i = 1; i <= num; i++) {
				list[i] = i + ". " + scan.nextLine();
				listClass[i] = scan.nextLine();
				if (scan.hasNext())
					scan.nextLine();
			}
			scan.close();
			in.close();
			String blackPlayerString = (String) JOptionPane.showInputDialog(
					null, "黑方：", "请选择", JOptionPane.QUESTION_MESSAGE, null,
					list, list[0]);
			if (blackPlayerString == null)
				System.exit(0);
			String whitePlayerString = (String) JOptionPane.showInputDialog(
					null, "白方：", "请选择", JOptionPane.QUESTION_MESSAGE, null,
					list, list[0]);
			if (whitePlayerString == null)
				System.exit(0);
			int blackPlayerNum = Integer.parseInt(blackPlayerString.substring(
					0, 1)), whitePlayerNum = Integer.parseInt(whitePlayerString
					.substring(0, 1));
			if (blackPlayerNum == 0)
				blackIsHuman = true;
			else
				blackPlayer = (AI) Class.forName(
						"ai." + listClass[blackPlayerNum]).newInstance();
			if (whitePlayerNum == 0)
				whiteIsHuman = true;
			else
				whitePlayer = (AI) Class.forName(
						"ai." + listClass[whitePlayerNum]).newInstance();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "配置文件错误", "错误",
					JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}

	public void invoke(Main frame) {
		if (frame.board.turn)
			if (blackIsHuman) {
				if (frame.history.move > 0)
					frame.infoWindow.undoButton.setEnabled(true);
				frame.controllable = true;
				frame.infoWindow.updateLabel(frame);
			} else {
				Coordinate move = blackPlayer.move(frame.board.black,
						frame.board.white);
				Coordinate[] pieces = Determine.judge(move, frame.board.black,
						frame.board.white);
				if (pieces.length == 0) {
					JOptionPane.showMessageDialog(frame, "黑方AI作弊", "错误",
							JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
				} else
					frame.board.add(frame, move, frame.board.black,
							frame.board.white, pieces);
			}
		else if (whiteIsHuman) {
			if (frame.history.move > 0)
				frame.infoWindow.undoButton.setEnabled(true);
			frame.controllable = true;
			frame.infoWindow.updateLabel(frame);
		} else {
			Coordinate move = whitePlayer.move(frame.board.white,
					frame.board.black);
			Coordinate[] pieces = Determine.judge(move, frame.board.white,
					frame.board.black);
			if (pieces.length == 0) {
				JOptionPane.showMessageDialog(frame, "白方AI作弊", "错误",
						JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
			} else
				frame.board.add(frame, move, frame.board.white,
						frame.board.black, pieces);
		}
	}
}
