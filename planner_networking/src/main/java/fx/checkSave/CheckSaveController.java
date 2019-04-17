package fx.checkSave;

import java.io.IOException;

import fx.homePageView.HomePageViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.PlanFile;

public class CheckSaveController
{

	
	public Client testClient;
	
	Stage primaryStage;
	
	BorderPane mainView;
	
	@FXML
	private Button checkSave;
	
	@FXML
	private Button checkExit;	
	
	boolean send;
	
	
	
	
	
	
	public void exit() throws IOException
	{
		
		

			
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/fx/homePageView/homePageView.fxml"));
			this.mainView = loader.load();
			
			HomePageViewController cont = loader.getController();



			cont.setTestClient(testClient);
	
			
			
			cont.setPrimaryStage(primaryStage);
			

			
			
			primaryStage.setWidth(800);
			primaryStage.getScene().setRoot(mainView);
			

		
	}
	
	public void save()
	{
		
		
		
		
	}
	
	
	
}
