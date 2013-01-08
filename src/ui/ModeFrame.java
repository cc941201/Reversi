package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.jgoodies.forms.layout.*;
import com.jgoodies.forms.factories.FormFactory;
import java.rmi.Naming;
import java.rmi.registry.*;

import ai.AI;
import rmi.*;

@SuppressWarnings("serial")
public class ModeFrame extends JFrame {
	public ModeFrame(final Main frame) throws Exception {
		super("模式选择");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(360, 220);
		setMinimumSize(new Dimension(360, 220));
		setLocationRelativeTo(null);

		final String[][] aiList = Configure.readAI();
		final String[] mapList = Configure.readMapList();

		final JPanel mapPane = new JPanel();
		mapPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
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

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.WEST);

		JPanel loaclPane = new JPanel();
		loaclPane.setOpaque(false);
		tabbedPane.addTab("本地", null, loaclPane, null);

		loaclPane.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec
				.decode("180px"), }, new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("bottom:default:grow") }));

		JPanel blackPane = new JPanel();
		blackPane.setOpaque(false);
		loaclPane.add(blackPane, "1, 1");
		blackPane.setLayout(new BorderLayout(0, 0));

		JLabel blackLabel = new JLabel("黑方：");
		blackPane.add(blackLabel, BorderLayout.WEST);

		final JComboBox blackBox = new JComboBox(aiList[0]);
		blackLabel.setLabelFor(blackBox);
		blackPane.add(blackBox);

		JPanel whitePane = new JPanel();
		whitePane.setOpaque(false);
		loaclPane.add(whitePane, "1, 2");
		whitePane.setLayout(new BorderLayout(0, 0));

		JLabel whiteLabel = new JLabel("白方：");
		whitePane.add(whiteLabel, BorderLayout.WEST);

		final JComboBox whiteBox = new JComboBox(aiList[0]);
		whiteLabel.setLabelFor(whiteBox);
		whitePane.add(whiteBox);

		final JCheckBox evaluateBox = new JCheckBox("AI评估模式");
		evaluateBox.setEnabled(false);
		loaclPane.add(evaluateBox, "1, 3");
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
		loaclPane.add(startButton, "1, 4");

		JPanel networkPane = new JPanel();
		networkPane.setOpaque(false);
		tabbedPane.addTab("局域网", null, networkPane, null);
		networkPane.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec
				.decode("180px"), }, new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("bottom:default:grow"), }));

		JButton hostButton = new JButton("作为主机");
		networkPane.add(hostButton, "1, 3");

		JPanel addressPane = new JPanel();
		addressPane.setOpaque(false);
		networkPane.add(addressPane, "1, 1");
		addressPane.setLayout(new BorderLayout(0, 0));

		JLabel addressLabel = new JLabel("地址：");
		addressPane.add(addressLabel, BorderLayout.WEST);

		final JTextField addressField = new JTextField();
		addressLabel.setLabelFor(addressField);
		addressPane.add(addressField);

		JButton connectButton = new JButton("连接");
		networkPane.add(connectButton, "1, 2, right, top");
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
					frame.start();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "致命错误", "运行时错误",
							JOptionPane.ERROR_MESSAGE);
					System.exit(-1);
				}
			}
		});

		hostButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (frame.hostRegistry == null)
						frame.hostRegistry = LocateRegistry
								.createRegistry(1099);
					else
						frame.hostRegistry.unbind("reversi");
					frame.local = new Server();
					frame.local.set(frame, mapList[mapBox.getSelectedIndex()]);
					frame.hostRegistry.rebind("reversi", frame.local);
					frame.networkHost = true;
					ModeFrame.this.dispose();
					frame.waitingWindow = new WaitingWindow(frame, true);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "创建服务器错误", "局域网模式错误",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (frame.clientRegistry == null)
						frame.clientRegistry = LocateRegistry
								.createRegistry(1100);
					else
						frame.clientRegistry.unbind("reversi");
					frame.local = new Server();
					frame.local.set(frame, null);
					frame.clientRegistry.rebind("reversi", frame.local);
					final Interface call = (Interface) Naming.lookup("//"
							+ addressField.getText() + "/reversi");
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								call.connect(false, frame.local, null);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "连接服务器错误",
										"局域网模式错误", JOptionPane.ERROR_MESSAGE);
							}
						}
					}).start();
					frame.networkHost = false;
					frame.remote = call;
					ModeFrame.this.dispose();
					frame.waitingWindow = new WaitingWindow(frame, false);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "连接服务器错误", "局域网模式错误",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		setVisible(true);
	}
}
