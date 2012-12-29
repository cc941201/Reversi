package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Piece extends JPanel implements MouseListener {
	private Coordinate c;
	private Main frame;
	private boolean focus;
	public boolean turn;
	private Color halfBlackBlue = new Color(0x375E82),
			halfWhiteBlue = new Color(0xBDD7F0), halfBlackRed = new Color(
					0x9A4B49), halfWhiteRed = new Color(0xFFCAC9);

	public Piece(Main frame, Coordinate c) {
		addMouseListener(this);
		this.c = c;
		this.frame = frame;
	}

	public void paintComponent(Graphics g) {
		int width = getWidth(), height = getHeight();
		if (focus) {
			boolean[][] yours, enemys;
			if (frame.board.turn) {
				yours = frame.board.black;
				enemys = frame.board.white;
			} else {
				yours = frame.board.white;
				enemys = frame.board.black;
			}
			if (Determine.judge(c, yours, enemys).length != 0) {
				g.setColor(UIManager.getColor("Focus.color"));
				g.fillRect(0, 0, width, height);
				if (frame.board.turn)
					g.setColor(halfBlackBlue);
				else
					g.setColor(halfWhiteBlue);
				g.fillOval(width / 10, height / 10, width / 5 * 4,
						height / 5 * 4);
			} else {
				g.setColor(UIManager.getColor("Button.select"));
				g.fillRect(0, 0, width, height);
				if (!frame.board.black[c.x][c.y]
						&& !frame.board.black[c.x][c.y]) {
					if (frame.board.turn)
						g.setColor(halfBlackRed);
					else
						g.setColor(halfWhiteRed);
					g.fillOval(width / 10, height / 10, width / 5 * 4,
							height / 5 * 4);
				}
			}
		} else {
			if (turn)
				g.setColor(UIManager.getColor("Focus.color"));
			else
				g.setColor(Color.lightGray);
			g.fillRect(0, 0, width, height);
		}
		if (frame.board.black[c.x][c.y]) {
			g.setColor(Color.black);
			g.fillOval(width / 10, height / 10, width / 5 * 4, height / 5 * 4);
		}
		if (frame.board.white[c.x][c.y]) {
			g.setColor(Color.white);
			g.fillOval(width / 10, height / 10, width / 5 * 4, height / 5 * 4);
		}
		g.setColor(Color.black);
		if (c.x != 0)
			g.drawLine(0, 0, 0, height);
		if (c.y != 0)
			g.drawLine(0, 0, width, 0);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		boolean[][] yours, enemys;
		if (frame.board.turn) {
			yours = frame.board.black;
			enemys = frame.board.white;
		} else {
			yours = frame.board.white;
			enemys = frame.board.black;
		}
		Coordinate[] pieces = Determine.judge(c, yours, enemys);
		if (pieces.length != 0)
			frame.board.add(frame, c, yours, enemys, pieces);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		focus = true;
		update(getGraphics());
		boolean[][] yours, enemys;
		if (frame.board.turn) {
			yours = frame.board.black;
			enemys = frame.board.white;
		} else {
			yours = frame.board.white;
			enemys = frame.board.black;
		}
		Coordinate[] pieces = Determine.judge(c, yours, enemys);
		if (pieces.length != 0)
			for (int i = 0; i < pieces.length; i++)
				if (pieces[i] != null) {
					frame.panel[pieces[i].x][pieces[i].y].turn=true;
					frame.panel[pieces[i].x][pieces[i].y].repaint();
				}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		focus = false;
		update(getGraphics());
		for (int i=0;i<8;i++)
			for (int j=0;j<8;j++)
				if (frame.panel[i][j].turn) {
					frame.panel[i][j].turn=false;
					frame.panel[i][j].repaint();
				}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
