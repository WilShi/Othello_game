package ca.utoronto.utm.othello.model;

import java.util.ArrayList;

import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;



public class TimerSetter extends TextField implements Observer {
	private ArrayList<Label> timerLabel;
	/**
	 * TimerSetter sets timer and display changes made
	 * for time limits.
	 * @param timer
	 */
	
	public TimerSetter(String timer) {
		super(timer);
		this.timerLabel = new ArrayList<Label>();
		this.setMaxWidth(70);
		this.setAlignment(Pos.CENTER);
	}
	
	/**
	 * keeps which labels to update
	 * @param label
	 */
	public void addLabel(Label label) {
		timerLabel.add(label);
	}
	
	/**
	 * Get all Labels
	 * @return timerLabel
	 */
	public ArrayList<Label> getLabels() {
		return this.timerLabel;
	}

	/**
	 * Disable timer when game starts.
	 */
	@Override
	public void update(Observable o) {
		Othello othello = (Othello)o;
		if (othello.isInGame()) {
			this.setDisable(true);
		} else {
			this.setDisable(false);
		}
	}
}