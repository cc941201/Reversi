package ui;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class Evaluate extends JFrame {
	private JLabel label = new JLabel();
	private int blackWin = 0, whiteWin = 0, draw = 0;

	public Evaluate(Main frame) {
		super("统计");
		setBounds(600, 300, 200, 150);
		setResizable(false);

		label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		getContentPane().add(label, BorderLayout.CENTER);

		JCheckBox checkBox = new JCheckBox("显示棋盘");
		checkBox.setSelected(true);
		getContentPane().add(checkBox, BorderLayout.SOUTH);

		setVisible(true);
	}

	public void updateLabel(Main frame) {
		if (frame.finished)
			switch (frame.winner) {
			case -1:
				whiteWin++;
				break;
			case 0:
				draw++;
				break;
			case 1:
				blackWin++;
			}
		int num = blackWin + whiteWin + draw;
		if (num==0)
			num=1;
		label.setText("<html><b>黑方赢：</b>" + blackWin + "局<br><b>白方赢：</b>"
				+ whiteWin + "局<br><b>平：</b>" + draw + "局<br><br><b>黑方：</b>"
				+ blackWin * 100 / num + "%<br><b>白方胜率：</b>" + whiteWin * 100
				/ num + "%</html>");
	}
}
