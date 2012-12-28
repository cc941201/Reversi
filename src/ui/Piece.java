package ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Piece extends JPanel {
	private Coordinate c;

	public Piece(Coordinate c) {
		this.c = c;
	}

	public void paintComponent(Graphics g) {
		int width = getWidth(), height = getHeight();
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
}
