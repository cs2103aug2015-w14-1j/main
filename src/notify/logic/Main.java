package notify.logic;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import notify.view.MainViewHandler;

public class Main extends Application {
	private static double MIN_SCREEN_WIDTH = 1020;
	private static double MIN_SCREEN_HEIGHT = 732;
	
	private static String MAIN_VIEW_PATH = "../view/MainView.fxml";
	
	private Logic logic;
	private Stage primaryStage;
	private BorderPane mainView;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Notify");
		this.logic = new Logic();
		
		initMainView();
	}
	
	public void initMainView() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(Main.class.getResource(MAIN_VIEW_PATH));

			mainView = (BorderPane)fxmlLoader.load();
			
			Scene scene = new Scene(mainView);
			primaryStage.setScene(scene);
			primaryStage.setMinHeight(MIN_SCREEN_HEIGHT);
			primaryStage.setMinWidth(MIN_SCREEN_WIDTH);

	        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	        	@Override
	            public void handle(WindowEvent event) {
	                Platform.runLater(new Runnable() {
	                    @Override
	                    public void run() {
	                    	logic.getStorage().saveTasks(logic.getTaskManager().getTask());
	                    }
	                });
	            }
	        });
			primaryStage.show();
			
			MainViewHandler mainViewHandler = fxmlLoader.getController();
			mainViewHandler.setLogic(logic);
			mainViewHandler.load();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}