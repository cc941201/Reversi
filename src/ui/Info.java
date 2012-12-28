package ui;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Info extends JFrame {
	public static JLabel label = new JLabel();

	public Info(Main frame) {
		super("状态");
		setBounds(600, 100, 200, 200);
		setResizable(false);
		updateLabel(frame);
		label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		getContentPane().add(label, BorderLayout.CENTER);
	}

	public static void updateLabel(Main frame) {
		String side;
		if (frame.turn)
			side = "黑";
		else
			side = "白";
		label.setText("<html><b>等待" + side + "方</b><br><br><br><b>黑方：</b>"
				+ frame.blackNum + "<br><b>白方：</b>" + frame.whiteNum
				+ "<br><br><b>空余：</b>" + frame.emptyNum + "</html>");
	}
}
