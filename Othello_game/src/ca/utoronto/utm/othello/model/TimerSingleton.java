package ca.utoronto.utm.othello.model;

import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import ca.utoronto.utm.util.TimerVisitable;
import ca.utoronto.utm.util.Visitor;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * The timer Singleton contains two timelines for player1 and 2, and
 * corresponding Labels for displaying the timelines
 */
public class TimerSingleton extends TimerVisitable implements Observer {
		
		private static TimerSingleton instance = new TimerSingleton();
		private Label minuteText1, secondText1, minuteText2, secondText2;
		private boolean startedTimer1 = false;
		private boolean startedTimer2 = false;
		private Timeline timer1, timer2;
		
		private TimerSingleton() {
			super();
		}
		
		/**
		 * set the label of the timer for the player
		 * @param min
		 * @param sec
		 * @param player
		 */
		public void setLabel(Label min, Label sec, char player) {
			if (player == OthelloBoard.P1) {
				this.minuteText1 = min;
				this.secondText1 = sec;
				this.timer1 = initTimer(min, sec);
			} else if (player == OthelloBoard.P2) {
				this.minuteText2 = min;
				this.secondText2 = sec;
				this.timer2 = initTimer(min, sec);
			}		
		}
		
		/**
		 * Initialize a timer with a min and a sec parameter
		 * @param min
		 * @param sec
		 */
		private Timeline initTimer(Label min, Label sec) {
			Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1),
					( ActionEvent ) -> {
						countDown(min, sec);
					}));
			timer.setCycleCount(Timeline.INDEFINITE);
			return timer;
		}
		
		/**
		 * return the instance of the timer singleton
		 * @return instance
		 */
		public static TimerSingleton getInstance() {
			return instance;
		}
		
		/**
		 * Start timer if it stops or stop if it starts
		 * for a player 
		 * @param player
		 */
		public boolean alternateTimer(char player) {
			if (player == OthelloBoard.P1) {
				if (this.startedTimer1) {
					this.startedTimer1 = false;
					this.timer1.stop();
				} else {
					this.startedTimer1 = true;
					this.timer1.play();
				}
				return this.startedTimer1;
			} else if (player == OthelloBoard.P2) {
				if (this.startedTimer2) {
					this.startedTimer2 = false;
					this.timer2.stop();
				} else {
					this.startedTimer2 = true;
					this.timer2.play();
				}
				return this.startedTimer2;
			} else {
				return false;
			}
		}
		
		/**
		 * Reset the timer for both players(used when restarting the game)
		 */
		public void resetTimer() {
			this.startedTimer1 = false;
			this.startedTimer2 = false;
			this.timer1.stop();
			this.timer2.stop();
			this.minuteText1.setText("05");
			this.secondText1.setText("00");
			this.minuteText2.setText("05");
			this.secondText2.setText("00");
		}
		
		/**
		 * Count down from the given min and sec
		 * @param min
		 * @param sec
		 */
		private void countDown(Label min, Label sec) {
			int minute = Integer.parseInt(min.getText());
			int second = Integer.parseInt(sec.getText());
			if (minute == 0 && second == 0) {
			} else if (second == 0) {
				minute -= 1;
				second = 59;
			} else {
				second -= 1;
			}
			String minText, secText;
			if (minute < 10 ) {
				minText = "0" + String.valueOf(minute);
			} else {
				minText = String.valueOf(minute);
			}
			
			if (second < 10 ) {
				secText = "0" + String.valueOf(second);
			} else {
				secText = String.valueOf(second);
			}
			min.setText(minText);
			sec.setText(secText);
		}

		/**
		 * Stops the timer if the game is over
		 */
		@Override
		public void update(Observable o) {
			Othello othello = (Othello)o;
			if (othello.isGameOver()) {
				this.stopAllTimer();
			}
		}
		
		/**
		 * Stops all timers
		 */
		public void stopAllTimer() {
			this.startedTimer1 = false;
			this.startedTimer2 = false;
			this.timer1.stop();
			this.timer2.stop();
		}

		@Override
		public void accept(Visitor visitor) {
			visitor.visit(this);
		}
	}
