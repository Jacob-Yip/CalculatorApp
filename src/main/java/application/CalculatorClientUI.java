package src.main.java.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 
 * 
 * Note: 
 * command in run configuration: 
 * Window: --module-path "\lib" --add-modules javafx.controls,javafx.media,javafx.fxml
 * Mac: --module-path "/lib" --add-modules javafx.controls,javafx.media,javafx.fxml
 * 
 */
public class CalculatorClientUI extends Application {
	private Scene scene;
	private Parent root;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader boardLoader = new FXMLLoader(getClass().getResource("Board.fxml"));
			
			root = boardLoader.load();
			
			BoardController boardController = boardLoader.getController();
			
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			// Close the app
			Platform.exit();
			System.exit(0);
		}
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				
				// For data cleaning (in memory)
				// Similar to scan.close()
				Platform.exit();
				System.exit(0);
			}
		});
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
