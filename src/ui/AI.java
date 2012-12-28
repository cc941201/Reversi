package ui;

/**
 * Pieces are stored in two 8x8 boolean arrays.
 * One for your pieces, one for the enemy's.
 */
public interface AI {
	Coordinate Move(boolean[][] yours, boolean[][] enemys);
}
