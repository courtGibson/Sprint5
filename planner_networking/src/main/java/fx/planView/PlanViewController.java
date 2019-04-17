package fx.planView;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.PlanFile;

public class PlanViewController
{
	Stage primaryStage;
	BorderPane mainView;
	Client testClient;
	PlanFile plan;
	
	
	/**
	 * @return the plan
	 */
	public PlanFile getPlan()
	{
		return plan;
	}

	/**
	 * @param plan the plan to set
	 */
	public void setPlan(PlanFile plan)
	{
		this.plan = plan;
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
	
	Button saveBtn;
	Button removeBtn;
	Button addBtn;
	
	

}
