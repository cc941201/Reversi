package ui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Info extends JFrame {
	private JLabel label = new JLabel();
	public JButton undoButton;

	public Info(Main frame) {
		super("状态");
		setBounds(600, 100, 200, 200);
		setResizable(false);
		updateLabel(frame);
		label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		getContentPane().add(label, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		undoButton = new JButton("撤销");
		undoButton.addActionListener(new Undo(frame));
		undoButton.setEnabled(false);
		panel.add(undoButton);

		JButton restartButton = new JButton("重新开始");
		restartButton.addActionListener(new Restart(frame));
		panel.add(restartButton);
	}

	public void updateLabel(Main frame) {
		String side, line;
		if (frame.finished) {
			if (frame.winner == 1)
				line = "黑方赢了";
			else if (frame.winner == -1)
				line = "白方赢了";
			else
				line = "平局";
		} else if (frame.controllable) {
			if (frame.board.turn)
				side = "黑";
			else
				side = "白";
			line = "等待" + side + "方...";
		} else
			line = "请稍候...";
		label.setText("<html><b>" + line + "</b><br><br><br><b>黑方：</b>"
				+ frame.board.blackNum + "个<br><b>白方：</b>"
				+ frame.board.whiteNum + "个<br><br><b>空余：</b>"
				+ (64 - frame.board.blackNum - frame.board.whiteNum)
				+ "个<br><br></html>");
	}

	private class Restart implements ActionListener {
		private Main frame;

		public Restart(Main frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			frame.infoWindow.dispose();
			frame.dispose();
			new Main();
		}
	}

	private class Undo implements ActionListener {
		private Main frame;

		public Undo(Main frame) {
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			frame.infoWindow.undoButton.setEnabled(false);
			frame.history.undo(frame);
		}
	}
}
