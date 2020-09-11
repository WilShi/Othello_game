package ca.utoronto.utm.othello.model;

import ca.utoronto.utm.util.Observer;
import java.io.File;
import ca.utoronto.utm.util.Observable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class PlayerTurnImage extends VBox implements Observer {
	ImageView tokenImage;
	/**
	 * PlayerTurnImage represents who's turn it is
	 *
	 */
	public PlayerTurnImage() {
		super();
		this.tokenImage = new ImageView(new Image(new File("assets/TW.png").toURI().toString(), 50, 50, false, false));
		this.tokenImage.setFitHeight(70);
		this.tokenImage.setFitWidth(70);
		Label turnLabel = new Label("Who's Turn");
		turnLabel.setStyle("-fx-font-size: 20px; -fx-padding: 5px;");
		this.setAlignment(Pos.CENTER);
		this.getChildren().add(turnLabel);
		this.getChildren().add(this.tokenImage);
	}
	
	/**
	 * PlayerTurnImage displays proper image of
	 * token of current player. If it is a turn of P1, shows a white token
	 * or shows a black token otherwise if it is turn of P2
	 */
	@Override
	public void update(Observable o) {
		Othello othello = (Othello)o;
		char whosTurn = othello.getWhosTurn();
		if (whosTurn == OthelloBoard.P1) {
			Image tokenImage = new Image(new File("assets/TW.png").toURI().toString(), 50, 50, false, false);
			this.tokenImage.setImage(tokenImage);
		} else if (whosTurn == OthelloBoard.P2) {
			Image tokenImage = new Image(new File("assets/TB.png").toURI().toString(), 50, 50, false, false);
			this.tokenImage.setImage(tokenImage);
		} else {
			this.tokenImage.setImage(null);
		}
	}
}
