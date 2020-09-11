package ca.utoronto.utm.othello.model;

import java.io.File;

import ca.utoronto.utm.util.Observable;
import ca.utoronto.utm.util.Observer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

// Grid is an observer of Othello
public class Grid extends Button implements Observer {
	int row, col;
	char token;
	/**
	 * Create a new Grid with row,col and token
	 * @param char token
	 *  @param int row
	 *  @param int col
	 */
	public Grid(char token, int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.token = token;
	}
	
	public void setValue(char token) {
		this.token = token;
	}
	
	/**
	 * updates the image of board, plays the flipping animation
	 */
	@Override
	public void update(Observable o) {
		Othello othello = (Othello)o;
		OthelloBoard board = ((Othello)othello).getBoard();
		char token = board.get(this.row, this.col);
				
		if ((this.row % 2 == 0 && this.col % 2 == 0) || (this.row % 2 == 1 && this.col % 2 == 1)) {
			this.setStyle("-fx-opacity: 1;-fx-background-color: rgb(25, 111, 61); -fx-border-color: rgb(23, 32, 42);");
		} else {
			this.setStyle("-fx-opacity: 1;-fx-background-color: rgb(25, 111, 61); -fx-border-color: rgb(23, 32, 42);");
		}
		if(othello.getCount(OthelloBoard.P1) == 2 && othello.getCount(OthelloBoard.P2) == 2) {
			if(token == OthelloBoard.P1) {
				   Image imageDecline = new Image(new File("assets/TW.png").toURI().toString());
				   this.setGraphic(new ImageView(imageDecline));
				  }
			else if(token == OthelloBoard.P2) {
				   Image imageDecline = new Image(new File("assets/TB.png").toURI().toString());
				   this.setGraphic(new ImageView(imageDecline));
			} else {
				this.setGraphic(null);
			}
		} else {
			if (this.token != token) {
				this.token = token;
				if(token == OthelloBoard.P1) {
					Timeline timer = new Timeline();
					Duration frameGap = Duration.millis(10);
					Duration frameTime = Duration.ZERO;
					for (int i = 0; i < 8; i++) {
						String image = new File("assets/fb" + String.valueOf(i) + ".png").toURI().toString();
						Image imageDecline = new Image(image);
						frameTime = frameTime.add(frameGap);
						KeyFrame keyFrame = new KeyFrame(frameTime,( ActionEvent ) -> {
							this.setGraphic(new ImageView(imageDecline));
						});
						timer.getKeyFrames().add(keyFrame);
					}
					for (int i = 7; i > -1 ; i--) {
						String image = new File("assets/fw" + String.valueOf(i) + ".png").toURI().toString();
						Image imageDecline = new Image(image);
						frameTime = frameTime.add(frameGap);
						KeyFrame keyFrame = new KeyFrame(frameTime,( ActionEvent ) -> {
							this.setGraphic(new ImageView(imageDecline));
						});
						timer.getKeyFrames().add(keyFrame);
					}
					timer.setCycleCount(1);
					timer.play();
				} else if(token == OthelloBoard.P2) {
					Timeline timer = new Timeline();
					
					Duration frameGap = Duration.millis(10);
					Duration frameTime = Duration.ZERO;
					
					for (int i = 0; i < 8; i++) {
						String image = new File("assets/fw" + String.valueOf(i) + ".png").toURI().toString();
						Image imageDecline = new Image(image);
						frameTime = frameTime.add(frameGap);
						KeyFrame keyFrame = new KeyFrame(frameTime,( ActionEvent ) -> {
							this.setGraphic(new ImageView(imageDecline));
						});
						timer.getKeyFrames().add(keyFrame);
					}
					for (int i = 7; i > -1 ; i--) {
						String image = new File("assets/fb" + String.valueOf(i) + ".png").toURI().toString();
						Image imageDecline = new Image(image);
						frameTime = frameTime.add(frameGap);
						KeyFrame keyFrame = new KeyFrame(frameTime,( ActionEvent ) -> {
							this.setGraphic(new ImageView(imageDecline));
						});
						timer.getKeyFrames().add(keyFrame);
					}
					timer.setCycleCount(1);
					timer.play();
				}
				else {
					this.setGraphic(null);
				}
			} else if (this.token == OthelloBoard.EMPTY) {
				this.setGraphic(null);
			}
		}
		if (othello.getWhosTurn() != OthelloBoard.EMPTY) {
			if (othello.hasMove(this.row, this.col, othello.getWhosTurn())) {
				this.setDisable(false);
				this.setStyle("-fx-opacity: 1;-fx-background-color: rgba(202, 207, 210, 0.2); -fx-border-color: rgba(0, 0, 0, 0.2);");	
			}
		}		
		
		Move hintMove = othello.getHintPosition();
		if (hintMove != null && this.row == hintMove.getRow() &&  this.col == hintMove.getCol() && !othello.isGameOver()) {
			this.setStyle("-fx-opacity: 1;-fx-background-color: rgba(0, 255, 255, 0.2); -fx-border-color: rgba(0, 0, 0, 0.2);");
		}
	}
}
