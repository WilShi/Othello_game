package ca.utoronto.utm.othello.model;

import java.util.stream.IntStream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Handler for an event on the bestMove button
 */


public class BestMoveEventHandler implements EventHandler<ActionEvent> {
	private Othello othello;
	public BestMoveEventHandler(Othello othello) {
		this.othello = othello;
	}
	
	@Override
	/**
	 *  make a move and play the move sound
	 */
	public void handle(ActionEvent event) {
		Move betterMove = getBetterMove();
		this.othello.move(betterMove.getRow(), betterMove.getCol());		
	}
	
	/**
	 *  Generate a "best move" according to the strategy: Occupy corners > Occupy sides > make a move
	 *  that will result in most flips in the middle 4x4 gird > a move playerGreedy would make
	 */
	private Move getBetterMove() {
		char currPlayer = this.othello.getWhosTurn();
		// Check corners
		int[] corner = {0, Othello.DIMENSION - 1};
		for (int row : corner) {
			for (int col : corner) {
				if (this.othello.hasMove(row, col, currPlayer)) {
					return new Move(row, col);
				};
			}
		}
		// Check vertical side
		int[] num1 = {0, Othello.DIMENSION - 1};
		int[] num2 = IntStream.range(1, Othello.DIMENSION - 1).toArray();
		// Check first row and last row
		for (int row : num1) {
			for (int col : num2) {
				if (this.othello.hasMove(row, col, currPlayer)) {
					return new Move(row, col);
				};
			}
		}
		// Check first column and last column
		for (int col : num1) {
			for (int row : num2) {
				if (this.othello.hasMove(row, col, currPlayer)) {
					return new Move(row, col);
				};
			}
		}
		
		// Choose a position such that it maximizes player's
		// presence in middle 4 x 4 in the field.
		int[] middle = IntStream.range(1, 6).toArray();
		int bestMoveNum = 0;
		int currNum = this.othello.getMiddle4By4Count(currPlayer);
		int brow = 0, bcol = 0;
		for (int row : middle) {
			for (int col : middle) {
				Othello othelloCopy = this.othello.copy();
				if (othelloCopy.move(row, col) && currNum - othelloCopy.getMiddle4By4Count(currPlayer) > bestMoveNum) {
					brow = row;
					bcol = col;
				}
			}
		}
		if (brow != 0 && bcol != 0) {
			return new Move(brow, bcol);
		}
		
		// Choose a place where such that mover's presence
		// on the board will be maximized.
		return new PlayerGreedy(this.othello, currPlayer).getMove();
	}
}
