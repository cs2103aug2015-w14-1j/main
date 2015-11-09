//@@author A0125364J
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
	private static double MIN_SCREEN_WIDTH = 1100;
	private static double MIN_SCREEN_HEIGHT = 732;
	
	private static String MAIN_VIEW_PATH = "/notify/view/MainView.fxml";
	private static String ROBOTO_PATH = "https://fonts.googleapis.com/css?family=Roboto:400,900"
												+ "italic,900,700italic,700,500italic,500,400italic,300italic,300,100italic,100";
	private static String ROBOTO_SLAB_PATH = "https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700";
	private static String ROBOTO_CONDENSED_PATH = "https://fonts.googleapis.com/css?family=Roboto+Condensed:400,300,"
												+ "300italic,400italic,700,700italic";
	
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
			
			scene.getStylesheets().add(ROBOTO_PATH);
			scene.getStylesheets().add(ROBOTO_SLAB_PATH);
			scene.getStylesheets().add(ROBOTO_CONDENSED_PATH);
			
			primaryStage.setScene(scene);
			primaryStage.setMinHeight(MIN_SCREEN_HEIGHT);
			primaryStage.setMinWidth(MIN_SCREEN_WIDTH);

	        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	        	
	        	@Override
	            public void handle(WindowEvent event) {
	        		
	                Platform.runLater(new Runnable() {
	                	
	                    @Override
	                    public void run() {
	                    	logic.save();
	                    }
	                    
	                });
	                
	            }
	        	
	        });
	        
			primaryStage.show();
			
			MainViewHandler mainViewHandler = fxmlLoader.getController();
			mainViewHandler.setLogic(logic);
			mainViewHandler.load();
			
		} catch(IOException e) {
			
			e.printStackTrace();
			
		} catch(Exception e) {
			
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
		
	}

	public static void main(String[] args) {
		
		launch(args);
		
	}
	
}