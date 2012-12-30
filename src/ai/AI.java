package ai;

import ui.Coordinate;

/**
 * Pieces are stored in two 8x8 boolean arrays.
 * One for your pieces, one for the enemy's.
 */
public interface AI {
	Coordinate move(boolean[][] yours, boolean[][] enemys);
}
