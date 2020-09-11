package ca.utoronto.utm.util;

public abstract class BoardVisitable {
	public abstract boolean accept(Visitor visitor, int row, int col);
}
