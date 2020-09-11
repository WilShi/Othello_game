package ca.utoronto.utm.othello.model;

import java.io.File;

import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Shows the token of the winner of the game, plays the victory sound
 */
public class WinnerToken extends VBox implements Observer {
	ImageView tokenImage;
	Label none;
	public boolean played = false;
	public WinnerToken() {
		super();
		this.tokenImage = new ImageView();
		Label winnerLabel = new Label("Winner");
		winnerLabel.setStyle("-fx-font-size: 20px; -fx-padding: 5px;");
		this.setAlignment(Pos.CENTER);
		this.none = new Label("NO PLAYER");
		this.none.setStyle("-fx-font-size: 20px;");
		this.none.setMinSize(70, 70);
		this.getChildren().add(winnerLabel);
		this.getChildren().add(this.tokenImage);
		this.getChildren().add(this.none);
	}
	
	/**
	 * Updates the winner token, change the image depending on the winner
	 */
	@Override
	public void update(Observable o) {
		Othello othello = (Othello)o;
		char winner = othello.getWinner();
		if (winner == OthelloBoard.P1) {
			Image tokenImage = new Image(new File("assets/TW.png").toURI().toString(), 50, 50, false, false);
			this.tokenImage.setImage(tokenImage);
			this.tokenImage.setFitHeight(70);
			this.tokenImage.setFitWidth(70);
			if (this.getChildren().contains(this.none)) {
				this.getChildren().remove(this.none);
			}
			this.playVictorySound();
		} else if (winner == OthelloBoard.P2) {
			Image tokenImage = new Image(new File("assets/TB.png").toURI().toString(), 50, 50, false, false);
			this.tokenImage.setImage(tokenImage);
			this.tokenImage.setFitHeight(70);
			this.tokenImage.setFitWidth(70);
			if (this.getChildren().contains(this.none)) {
				this.getChildren().remove(this.none);
			}
			this.playVictorySound();
		} else {
			this.tokenImage.setImage(null);
			if (!this.getChildren().contains(this.none)) {
				this.getChildren().add(this.none);
			}
			this.played = false;
		}
	}
	
	private void playVictorySound() {
		TimerSingleton.getInstance().stopAllTimer();
		if(!played) {
			played = true;
		}	
	}
}
