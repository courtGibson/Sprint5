package fx.choosePlan;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import software_masters.planner_networking.PlanNode;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import software_masters.planner_networking.Centre;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Plan;
import software_masters.planner_networking.PlanFile;
import software_masters.planner_networking.Server;
import software_masters.planner_networking.ServerImplementation;


public class ChoosePlanController
{
	public Plan plan;
	public Client testClient;
	// to commit
	public void setTestClient(Client testClient)
	{
		this.testClient = testClient;
		
	}
	
	
	// make the FXML stuff
	
	@FXML
	private RadioButton viewPlanBRtn;
	
	@FXML
	private RadioButton newPlanRBtn;
	
	@FXML
	private TextField newPlanYearText;
	
	
	
	public void choosePlanType()
	{
		
		newPlanYearText.setDisable(true);
		
		
		if(viewPlanBRtn.isSelected())
		{
			
			
			
			
			

			
			
		}
		else // newPlanButton selected
		{
			String planYear = newPlanYearText.getAccessibleText();
			plan.setYear(planYear);
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/fx/planView/planView.fxml"));
			this.mainView = loader.load();
			
			PlanViewController cont = loader.getController();
			cont.setTestClient(testClient);
			cont.setPrimaryStage(primaryStage);
			
			cont.setPlan(plan);
			cont.setPlan(plan);
			
			primaryStage.getScene().setRoot(mainView);

			
			
			
		}
		
		
		
		
	}
	
	
}
