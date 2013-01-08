package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.jgoodies.forms.layout.*;
import com.jgoodies.forms.factories.FormFactory;

import ai.AI;

@SuppressWarnings("serial")
public class ModeFrame extends JFrame {
	public ModeFrame(final Main frame) throws Exception {
		super("模式选择");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(320, 220);
		setMinimumSize(new Dimension(320, 220));
		setLocationRelativeTo(null);

		final String[][] aiList = Configure.readAI();
		final String[] mapList = Configure.readMapList();

		((JComponent) getContentPane()).setBorder(BorderFactory
				.createEmptyBorder(0, 10, 10, 10));

		final JPanel mapPane = new JPanel();
		getContentPane().add(mapPane, BorderLayout.CENTER);
		mapPane.setLayout(new BorderLayout(0, 0));

		JPanel mapBoxPane = new JPanel();
		mapPane.add(mapBoxPane, BorderLayout.NORTH);

		JLabel mapBoxLabel = new JLabel("地图：");
		mapBoxPane.add(mapBoxLabel);

		final JComboBox mapBox = new JComboBox(mapList);
		mapBoxLabel.setLabelFor(mapBox);
		mapBoxPane.add(mapBox);
		mapBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					frame.board = new Chessboard(mapList[mapBox
							.getSelectedIndex()]);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "配置文件错误", "错误",
							JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
				}
				mapPane.repaint();
			}
		});

		frame.board = new Chessboard(mapList[0]);
		mapPane.add(frame.boardPane(), BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec
				.decode("144px"), }, new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("bottom:default:grow"), }));

		JPanel networkPane = new JPanel();
		panel.add(networkPane, "1, 1, left, top");

		JRadioButton localButton = new JRadioButton("本地");
		networkPane.add(localButton);
		localButton.setSelected(true);

		JRadioButton networkButton = new JRadioButton("局域网");
		networkPane.add(networkButton);

		ButtonGroup networkGroup = new ButtonGroup();
		networkGroup.add(localButton);
		networkGroup.add(networkButton);

		JPanel blackPane = new JPanel();
		panel.add(blackPane, "1, 2");
		blackPane.setLayout(new BorderLayout(0, 0));

		JLabel blackLabel = new JLabel("黑方：");
		blackPane.add(blackLabel, BorderLayout.WEST);

		final JComboBox blackBox = new JComboBox(aiList[0]);
		blackLabel.setLabelFor(blackBox);
		blackPane.add(blackBox);

		JPanel whitePane = new JPanel();
		panel.add(whitePane, "1, 3");
		whitePane.setLayout(new BorderLayout(0, 0));

		JLabel whiteLabel = new JLabel("白方：");
		whitePane.add(whiteLabel, BorderLayout.WEST);

		final JComboBox whiteBox = new JComboBox(aiList[0]);
		whiteLabel.setLabelFor(whiteBox);
		whitePane.add(whiteBox);

		final JCheckBox evaluateBox = new JCheckBox("AI评估模式");
		evaluateBox.setEnabled(false);
		panel.add(evaluateBox, "1, 4, fill, top");

		class evaluateListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (blackBox.getSelectedIndex() == 0
						|| whiteBox.getSelectedIndex() == 0) {
					evaluateBox.setSelected(false);
					evaluateBox.setEnabled(false);
				} else
					evaluateBox.setEnabled(true);
			}
		}
		blackBox.addActionListener(new evaluateListener());
		whiteBox.addActionListener(new evaluateListener());

		JButton startButton = new JButton("开始");
		getRootPane().setDefaultButton(startButton);
		panel.add(startButton, "1, 5");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (blackBox.getSelectedIndex() == 0)
						frame.invoke.blackIsHuman = true;
					else
						frame.invoke.blackPlayer = (AI) Class.forName(
								"ai." + aiList[1][blackBox.getSelectedIndex()])
								.newInstance();
					if (whiteBox.getSelectedIndex() == 0)
						frame.invoke.whiteIsHuman = true;
					else
						frame.invoke.whitePlayer = (AI) Class.forName(
								"ai." + aiList[1][whiteBox.getSelectedIndex()])
								.newInstance();
					ModeFrame.this.dispose();
					frame.evaluate = evaluateBox.isSelected();
					frame.start(mapList[mapBox.getSelectedIndex()]);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "致命错误", "运行时错误",
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
					System.exit(-1);
				}
			}
		});

		setVisible(true);
	}
}
