package cs.notify.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage primaryStage;
	private BorderPane mainView;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Notify");
		
		initMainView();
	}
	
	public void initMainView() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(Main.class.getResource("../view/MainView.fxml"));
			
			mainView = (BorderPane)fxmlLoader.load();
			
			Scene scene = new Scene(mainView);
			primaryStage.setScene(scene);
			primaryStage.setMinHeight(732);
			primaryStage.setMinWidth(1230);
			primaryStage.show();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
