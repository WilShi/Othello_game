package ca.utoronto.utm.othello.viewcontroller;

import ca.utoronto.utm.othello.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OthelloApplication extends Application {
	// REMEMBER: To run this in the lab put 
	// --module-path "/usr/share/openjfx/lib" --add-modules javafx.controls,javafx.fxml
	// in the run configuration under VM arguments.
	// You can import the JavaFX.prototype launch configuration and use it as well.

	@Override
	public void start(Stage stage) throws Exception {
		// Create and hook up the Model, View and the controller
		
		// MODEL
		Othello othello=new Othello();
		// add the initial board
		
		othello.getBoards().add(othello.getBoard());
		// VIEWCOMPONENTS
		// VIEW LAYOUT
		Scene scene = new OthelloView(othello).createScene();
		stage.setTitle("Othello");
		stage.setScene(scene);
				
		// LAUNCH THE GUI
		stage.show();
	}

	public static void main(String[] args) {
		OthelloApplication view = new OthelloApplication();
		launch(args);
	}
}

