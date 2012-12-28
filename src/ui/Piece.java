package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ai.Judge;

@SuppressWarnings("serial")
public class Piece extends JPanel implements MouseListener {
	private Coordinate c;
	private Main frame;
	private boolean focus;

	public Piece(Main frame, Coordinate c) {
		addMouseListener(this);
		this.c = c;
		this.frame = frame;
	}

	public void paintComponent(Graphics g) {
		int width = getWidth(), height = getHeight();
		if (focus) {
			boolean[][] yours, enemys;
			if (frame.turn) {
				yours = Chessboard.black;
				enemys = Chessboard.white;
			} else {
				yours = Chessboard.white;
				enemys = Chessboard.black;
			}
			if (Judge.Can(c, yours, enemys))
				g.setColor(UIManager.getColor("Focus.color"));
			else
				g.setColor(UIManager.getColor("Button.select"));
		}
		else
			g.setColor(Color.lightGray);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		if (c.x != 0)
			g.drawLine(0, 0, 0, height);
		if (c.y != 0)
			g.drawLine(0, 0, width, 0);
		if (Chessboard.black[c.x][c.y])
			g.fillOval(width / 10, height / 10, width / 5 * 4, height / 5 * 4);
		if (Chessboard.white[c.x][c.y]) {
			g.setColor(Color.white);
			g.fillOval(width / 10, height / 10, width / 5 * 4, height / 5 * 4);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		boolean[][] yours, enemys;
		if (frame.turn) {
			yours = Chessboard.black;
			enemys = Chessboard.white;
		} else {
			yours = Chessboard.white;
			enemys = Chessboard.black;
		}
		if (Judge.Can(c, yours, enemys))
			Chessboard.add(frame, c, yours, enemys);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		focus = true;
		update(getGraphics());
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		focus = false;
		update(getGraphics());
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
