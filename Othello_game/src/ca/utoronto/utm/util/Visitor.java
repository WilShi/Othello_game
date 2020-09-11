package ca.utoronto.utm.util;

import ca.utoronto.utm.othello.model.OthelloBoard;
import ca.utoronto.utm.othello.model.TimerSingleton;

public interface Visitor {
	public abstract boolean visit(OthelloBoard visitable, int row, int col);
	public abstract void visit(TimerSingleton visitable);
}
