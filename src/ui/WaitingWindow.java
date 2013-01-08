package ui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;

@SuppressWarnings("serial")
public class WaitingWindow extends JFrame {
	public WaitingWindow(final Main frame, final boolean host) {
		super("局域网对战");
		setSize(200, 100);
		setResizable(false);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (!host)
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								frame.remote.close();
							} catch (Exception e1) {
							}
						}
					}).start();
				new ResetThread(frame).start();
			}
		});

		JLabel label;
		if (host)
			label = new JLabel("<html><b>主机已成功建立</b><br>正在等待传入连接...</html>");
		else
			label = new JLabel("<html><b>连接已成功建立</b><br>正在等待主机响应...</html>");
		label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		getContentPane().add(label, BorderLayout.CENTER);

		JButton cancelButton = new JButton("取消");
		getContentPane().add(cancelButton, BorderLayout.SOUTH);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				if (!host)
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								frame.remote.close();
							} catch (Exception e1) {
							}
						}
					}).start();
				new ResetThread(frame).start();
			}
		});

		setVisible(true);
	}
}
