package fx.homePageView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import fx.choosePlan.ChoosePlanController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loginView.LoginViewController;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.PlanFile;

public class HomePageViewController
{
	
	public Client testClient;
	
	@FXML 
	private Button planSubmitButton;

	Stage primaryStage;
	BorderPane mainView;
	
	PlanFile selectedPlan;
	
	@FXML
	private MenuButton menu;
	
	
	public void makeMenu(Client testClient) throws RemoteException
	{
		//menu.getItems().addAll();
		
		ArrayList<PlanFile> plans = testClient.getPlans();
		
		
		 ArrayList<MenuItem> menuItemList = getMenuItemList(plans);
		 
		 menu.getItems().addAll(menuItemList);
		
		
		
		
		
	}
	
	public ArrayList<MenuItem> getMenuItemList(ArrayList<PlanFile> plans)
	{
		ArrayList<MenuItem> items = new ArrayList<MenuItem>();
		
		for(PlanFile p:plans)
		{
			MenuItem m = new MenuItem(p.getYear());
			items.add(m);
		}
		
		
		return items;
		
	}
	
	
	

	
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

	
	
	
	public void submitPlan(PlanFile selectedPlan) throws IOException
	{
		
		testClient.setCurrPlanFile(selectedPlan);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/choosePlan/choosePlan.fxml"));
		this.mainView = loader.load();
		
		ChoosePlanController cont = loader.getController();
		cont.setTestClient(testClient);
		
		primaryStage.getScene().setRoot(mainView);
	}
	
	
}
