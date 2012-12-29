package ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class Info extends JFrame implements ActionListener {
	public static JLabel label = new JLabel();
	private Main frame;

	public Info(Main frame) {
		super("状态");
		setBounds(600, 100, 200, 200);
		setResizable(false);
		updateLabel(frame);
		label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		getContentPane().add(label, BorderLayout.CENTER);

		this.frame = frame;
		JButton restartButton = new JButton("重新开始");
		restartButton.addActionListener(this);
		getContentPane().add(restartButton, BorderLayout.NORTH);
	}

	public static void updateLabel(Main frame) {
		String side, line;
		if (frame.finished) {
			if (frame.winner == 1)
				line = "黑方赢了";
			else if (frame.winner == -1)
				line = "白方赢了";
			else
				line = "平局";
		} else if (frame.moving)
			line = "请稍候...";
		else {
			if (frame.board.turn)
				side = "黑";
			else
				side = "白";
			line = "等待" + side + "方...";
		}
		label.setText("<html><b>" + line + "</b><br><br><br><b>黑方：</b>"
				+ frame.board.blackNum + "个<br><b>白方：</b>"
				+ frame.board.whiteNum + "个<br><br><b>空余：</b>"
				+ frame.board.emptyNum + "个<br><br></html>");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
		frame.dispose();
		frame = new Main();
		frame.setVisible(true);
	}
}
