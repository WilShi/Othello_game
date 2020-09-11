package ca.utoronto.utm.othello.model;
import ca.utoronto.utm.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Capture an Othello game. This includes an OthelloBoard as well as knowledge
 * of how many moves have been made, whosTurn is next (OthelloBoard.P1 or
 * OthelloBoard.P2). It knows how to make a move using the board and can tell
 * you statistics about the game, such as how many tokens P1 has and how many
 * tokens P2 has. It knows who the winner of the game is, and when the game is
 * over.
 * 
 * See the following for a short, simple introduction.
 * https://www.youtube.com/watch?v=Ol3Id7xYsY4
 * 
 * @author arnold
 *
 */
public class Othello extends Observable implements Visitor {
	public static final int DIMENSION=8; // This is an 8x8 game

	private OthelloBoard board=new OthelloBoard(Othello.DIMENSION);
	private char whosTurn = OthelloBoard.P1;
	private int numMoves = 0;
	private Player opponent = null;
	private List<OthelloBoard> boards = new ArrayList<>();
	List<Observer> observers = new ArrayList<>();
	private int hintRow = -1, hintCol = -1;
	private boolean showHint = false;
	private boolean inGame = false;

	
	public Othello() {
		this.boards.add(this.getBoard());
	}
	
	/**
	 * Return a hinted move if showHint is true.
	 * 
	 * @return a hinted Move for player or null
	 */
	public Move getHintPosition() {
		if (this.showHint) {
			this.MaxMove();
			return new Move(this.hintRow,this.hintCol);
		} else {
			return null;
		}
	}
	
	/**
	 * Set the showHint value to show.
	 * 
	 * @param show
	 */
	public void setShowHint(boolean show) {
		this.showHint = show;
	}
	
	/**
	 * Set the row and column for the hint.
	 * 
	 * @param int row
	 * @param int col
	 */
	public void setHintPosition(int row, int col) {
		this.hintRow = row;
		this.hintCol = col;
	}
	
	public void MaxMove() {
		PlayerGreedy greedy = new PlayerGreedy(this, this.whosTurn);
		Move greedyMove = greedy.getMove();
		this.hintRow = greedyMove.getRow();
		this.hintCol = greedyMove.getCol();
	}
	
	/**
	 * return a copy of the current state of the board.
	 * 
	 * @return a copy of this.board
	 */
	public OthelloBoard getBoard() {
		return this.board.copy();
	}
	
	/**
	 * return the array list boards
	 * 
	 * @return boards
	 */
	public List<OthelloBoard> getBoards() {
		return this.boards;
	}
	
	/**
	 * return P1,P2 or EMPTY depending on who moves next.
	 * 
	 * @return P1, P2 or EMPTY
	 */
	public char getWhosTurn() {
		return this.whosTurn;
	}
	
	/**
	 * return the opposing player(that's not this.whosTurn), EMPTY if whosTurn is empty.
	 * 
	 * @return P1, P2 or EMPTY
	 */
	public char getOpponent() {
		if (this.whosTurn == OthelloBoard.P1) {
			return OthelloBoard.P2;
		} else if (this.whosTurn == OthelloBoard.P2) {
			return OthelloBoard.P1;
		} else {
			return OthelloBoard.EMPTY;
		}
	}
	
	/**
	 * 
	 * @param row 
	 * @param col
	 * @return the token at position row, col.
	 */
	public char getToken(int row, int col) {
		return this.board.get(row, col);
	}

	/**
	 * Attempt to make a move for P1 or P2 (depending on whos turn it is) at
	 * position row, col. A side effect of this method is modification of whos turn
	 * and the move count.
	 * 
	 * @param row
	 * @param col
	 * @return whether the move was successfully made.
	 */
	
	public boolean move(int row, int col) {
		if(this.board.accept(this, row, col)) {
			TimerSingleton.getInstance().accept(this);
			this.whosTurn = OthelloBoard.otherPlayer(this.whosTurn);
			TimerSingleton.getInstance().accept(this);
			char allowedMove = board.hasMove();
			if(allowedMove!=OthelloBoard.BOTH)this.whosTurn=allowedMove;
			this.numMoves++;
			if (this.opponent != null) {
				Move move = this.opponent.getMove();
				this.board.accept(this, move.getRow(), move.getCol());
				// add boards when make a move
				this.boards.add(this.getBoard());
				TimerSingleton.getInstance().accept(this);
				this.whosTurn = OthelloBoard.otherPlayer(this.whosTurn);
				TimerSingleton.getInstance().accept(this);
			}
			char hasMove = this.board.hasMove(); 
			if (this.opponent != null) {
				if (hasMove != OthelloBoard.P1 && hasMove != OthelloBoard.BOTH) {
					this.whosTurn = OthelloBoard.EMPTY;
				}
			} else if (hasMove == OthelloBoard.EMPTY) {
				this.whosTurn = OthelloBoard.EMPTY;
			}
			// add boards when make a move
			this.boards.add(this.getBoard());
			if (!inGame) {
				this.inGame = true;
			}	
			this.notifyObservers();
			return true;
		} 
		else {
			return false;
		}
	}
	
	/**
	 * Return true if P1 or P2 or BOTH has a move on the board.
	 * 
	 * @param row
	 * @param col
	 * @param player
	 * @return if P1,P2 or BOTH has a move on the board
	 */
	public boolean hasMove(int row, int col, char player) {
		OthelloBoard boardCopy = this.getBoard();
		char possiblePlayer = boardCopy.hasMove(row, col, player);
		return (possiblePlayer == OthelloBoard.BOTH || possiblePlayer == player);
	}
	
	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens for player on the board
	 */
	public int getCount(char player) {
		return board.getCount(player);
	}
	
	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens for player in the middle 4 x 4 square on the board
	 */
	public int getMiddle4By4Count(char player) {
		return board.getMiddle4By4Count(player);
	}


	/**
	 * Returns the winner of the game.
	 * 
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public char getWinner() {
		if(!this.isGameOver())return OthelloBoard.EMPTY;
		if(this.getCount(OthelloBoard.P1)> this.getCount(OthelloBoard.P2))return OthelloBoard.P1;
		if(this.getCount(OthelloBoard.P1)< this.getCount(OthelloBoard.P2))return OthelloBoard.P2;
		return OthelloBoard.EMPTY;
	}


	/**
	 * 
	 * @return whether the game is over (no player can move next)
	 */
	public boolean isGameOver() {
		boolean isGameOver = this.whosTurn == OthelloBoard.EMPTY;
		if (isGameOver) {
			this.inGame = false;
		}
		return isGameOver;
	}

	/**
	 * 
	 * @return a copy of this. The copy can be manipulated without impacting this.
	 */
	public Othello copy() {
		Othello o= new Othello();
		o.board=this.board.copy();
		o.numMoves = this.numMoves;
		o.whosTurn = this.whosTurn;
		return o;
	}
	
	/**
	 * Change the opponent to Random/Greedy according to the gameModeSingleton, make a move for
	 * Random/Greedy if it's currently it's turn.
	 */
	public void switchOpponent() {
		String gameMode = GameModeSingleton.getInstance().getGameMode();
		if (gameMode == "Human VS Random") {
			this.opponent = new PlayerRandom(this, OthelloBoard.P2);
		} else if (gameMode == "Human VS Greedy") {
			this.opponent = new PlayerGreedy(this, OthelloBoard.P2);
		} else {
			this.opponent = null;
		}
		if (this.getWhosTurn() == OthelloBoard.P2 && this.opponent != null) {
			TimerSingleton.getInstance().accept(this);
			Move move = this.opponent.getMove();
			this.board.accept(this, move.getRow(), move.getCol());
			this.boards.add(this.board.copy());
			TimerSingleton.getInstance().accept(this);
			this.whosTurn = OthelloBoard.otherPlayer(this.whosTurn);	
			TimerSingleton.getInstance().accept(this);
		}
		notifyObservers();
	}
	
	/**
	 * Restart the Othello game
	 * Resets the board to a new board
	 * Resets numMoves to zero
	 * Resets whosTurn to P1
	 * Resets boards
	 * Resets timer
	 */
	public void restartGame() {
		this.board = new OthelloBoard(DIMENSION);
		this.numMoves = 0;
		this.whosTurn = OthelloBoard.P1;
		this.boards = new ArrayList<>();
		boards.add(this.getBoard());
		TimerSingleton timerSingleton = TimerSingleton.getInstance();
		timerSingleton.resetTimer();
		this.inGame = false;
		notifyObservers();
	}

	/**
	 * undo the last move made by player
	 */
	public void undo() {
		if (this.opponent == null) {
			this.removeBoard();
			this.board = this.boards.get(this.boards.size()-1).copy();
			TimerSingleton.getInstance().alternateTimer(this.whosTurn);
			this.whosTurn = OthelloBoard.otherPlayer(this.whosTurn);	
			TimerSingleton.getInstance().alternateTimer(this.whosTurn);
		} else {
			this.removeBoard();
			this.removeBoard();
			this.board = this.boards.get(this.boards.size()-1).copy();
		}
		if (!this.isInGame()) {
			this.inGame = true;
		}
		notifyObservers();
	}
	
	private void removeBoard() {
		if (this.boards.size() >= 1) {
			this.boards.remove(this.boards.size()-1);
		}
	}
	
	public boolean isInGame() {
		return this.inGame;
	}

	/**
	 * 
	 * @return a string representation of the board.
	 */
	public String getBoardString() {
		return board.toString()+"\n";
	}
	
	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}
	
	@Override
	public void attach(Observer observer) {
		this.observers.add(observer);
	}
	
	@Override
	public void detach(Observer observer) {
		this.observers.add(observer);
	}
	
	@Override
	public boolean visit(OthelloBoard visitable, int row, int col) {
		return visitable.move(row, col, this.whosTurn);
	}
	
	@Override
	public void visit(TimerSingleton visitable) {
		visitable.alternateTimer(this.whosTurn);
	}
	

	/**
	 * run this to test the current class. We play a completely random game. DO NOT
	 * MODIFY THIS!! See the assignment page for sample outputs from this.
	 * 
	 * @param args
	 */
	public static void main(String [] args) {
		Random rand = new Random();


		Othello o = new Othello();
		System.out.println(o.getBoardString());
		while(!o.isGameOver()) {
			int row = rand.nextInt(8);
			int col = rand.nextInt(8);

			if(o.move(row, col)) {
				System.out.println("makes move ("+row+","+col+")");
				System.out.println(o.getBoardString()+ o.getWhosTurn()+" moves next");
			}
		}

	}
}


