package ca.utoronto.utm.othello.model;

import java.io.File;
import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TokenNumVBox extends VBox implements Observer {
	Label tokenNum1, tokenNum2;
	ImageView player1Token, player2Token;
	HBox player1TokenHBox, player2TokenHBox;
	/**
	 * TokenNumVBox is a VBox containing information
	 * about number of tokens on the field for each player. 
	 */
	public TokenNumVBox() {
		super();
		this.player1Token = createTokenImage("assets/TW.png");
		this.player2Token = createTokenImage("assets/TB.png");
		this.player1TokenHBox = new HBox();
		this.player2TokenHBox = new HBox();
		this.player1TokenHBox.setAlignment(Pos.CENTER);
		this.player2TokenHBox.setAlignment(Pos.CENTER);
		this.tokenNum1 = new Label();
		this.tokenNum2 = new Label();
		this.tokenNum1.setMinWidth(20);
		this.tokenNum2.setMinWidth(20);
		// Add to HBox
		this.player1TokenHBox.getChildren().add(this.player1Token);
		this.player1TokenHBox.getChildren().add(this.tokenNum1);
		this.player2TokenHBox.getChildren().add(this.player2Token);
		this.player2TokenHBox.getChildren().add(this.tokenNum2);
		this.setLabelStyle();
		
		Label tokenNum = new Label("TOKENS");
		tokenNum.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");
		this.getChildren().add(tokenNum);
		this.getChildren().add(this.player1TokenHBox);
		this.getChildren().add(this.player2TokenHBox);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(10);
	}
	
	/**
	 * Create token image
	 * @param imageName
	 * @return imageView
	 */
	private ImageView createTokenImage(String imageName) {
		ImageView imageView = new ImageView();
		imageView.setStyle("-fx-opacity: 1;-fx-background-color: rgb(25, 111, 61); -fx-border-color: rgb(23, 32, 42);");
		Image tokenImage = new Image(new File(imageName).toURI().toString(), 50, 50, false, false);
		imageView.setImage(tokenImage);
		return imageView;
	}
	
	/**
	 * Set styles for labels showing number of tokens
	 * for each player
	 */
	private void setLabelStyle() {
		this.tokenNum1.setStyle("-fx-padding: 10px; -fx-font-size: 30px; -fx-font-weight: bold;");
		this.tokenNum2.setStyle("-fx-padding: 10px; -fx-font-size: 30px; -fx-font-weight: bold;");
	}
	
	/**
	 * Set styles for images
	 */
	private void setImageStyle() {
		this.player1Token.setStyle("-fx-opacity: 1;-fx-background-color: rgb(25, 111, 61); -fx-border-color: rgb(23, 32, 42);");
		this.player2Token.setStyle("-fx-opacity: 1;-fx-background-color: rgb(25, 111, 61); -fx-border-color: rgb(23, 32, 42);");
	}
	
	/**
	 * Check number of tokens for each player
	 * and displays on the screen
	 * @param Observable
	 */
	@Override
	public void update(Observable o) {
		Othello othello = (Othello)o;
		int p1Num = othello.getCount(OthelloBoard.P1);
		int p2Num = othello.getCount(OthelloBoard.P2);
		this.tokenNum1.setText(String.valueOf(p1Num));
		this.tokenNum2.setText(String.valueOf(p2Num));	
		this.setLabelStyle();
		this.setImageStyle();
	}
}