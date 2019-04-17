package fx.planView;

import java.io.IOException;

import fx.homePageView.HomePageViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loginView.LoginViewController;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.PlanFile;

public class PlanViewController
{
	Stage primaryStage;
	BorderPane mainView;
	Client testClient;
	
	@FXML 
	private Button logoutButton;
	
	@FXML 
	private Button homepageButton;


	
	

	public Client getTestClient()
	{
	
		return testClient;
	}

	public void setTestClient(Client testClient)
	{
	
		this.testClient = testClient;
	}

	public Stage getPrimaryStage()
	{
	
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage)
	{
	
		this.primaryStage = primaryStage;
	}

	public BorderPane getMainView()
	{
	
		return mainView;
	}

	public void setMainView(BorderPane mainView)
	{
	
		this.mainView = mainView;
	}
	
	Button saveBtn;
	Button removeBtn;
	Button addBtn;
	
	
	public void logout() throws IOException {
		System.out.println("logout");
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/loginView/loginView.fxml"));
		this.mainView = loader.load();
		
		LoginViewController cont = loader.getController();
		cont.setTestClient(this.testClient);
		cont.setPrimaryStage(primaryStage);
		
		primaryStage.getScene().setRoot(mainView);
	}
	
	public void homepage() throws IOException {
		System.out.println("homepage");
		
		this.testClient = testClient;
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/fx/homePageView/homePageView.fxml"));
		this.mainView = loader.load();
		
		HomePageViewController cont = loader.getController();
		cont.setTestClient(testClient);
		cont.setPrimaryStage(primaryStage);
		
		primaryStage.setWidth(800);
		primaryStage.getScene().setRoot(mainView);
	}
	
	

}
