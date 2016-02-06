package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			 Parent parent = FXMLLoader.load(getClass().getResource("DefinitionsContainerBoundary.fxml"));
		        Scene scene = new Scene(parent);
		        primaryStage.setTitle("Process Enactment Tool");
		        primaryStage.setScene(scene);
		        primaryStage.show();
		        primaryStage.setResizable(false);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
