package ca.utoronto.utm.othello.model;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

/**
 * Handler for an event on the timesetter
 */
public class TimerSetterEventHandler implements EventHandler<ActionEvent> {
	/**
	 * TimerSetterEventHandler handles inputs to set timer.
	 */
	public TimerSetterEventHandler() {};
	
	@Override
	public void handle(ActionEvent event) {
		TimerSetter source = (TimerSetter)event.getSource();
		String text = source.getText();
		if (validate(text) && inRange(text)) {
			for (Label label : source.getLabels()) {
				label.setText(text);
			}
		}
	}
	
	/**
	 * Checks if input only contains integer
	 * @param text
	 * @return boolean
	 */
	private boolean validate(String text)
    {
        return text.matches("[0-9]*");
    }
	
	/**
	 * Only allow to set between 0 to 59
	 * @param text
	 * @return boolean
	 */
	private boolean inRange(String text) {
		int value = Integer.parseInt(text);
		return 0 <= value && value < 60;
	}
}
