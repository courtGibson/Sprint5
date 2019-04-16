package fx.choosePlan;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javafx.fxml.FXML;
import javafx.scene.Node;
import software_masters.planner_networking.PlanNode;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import software_masters.planner_networking.Centre;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Plan;
import software_masters.planner_networking.PlanFile;
import software_masters.planner_networking.Server;
import software_masters.planner_networking.ServerImplementation;


public class ChoosePlan
{
	
	PlanFile planFile;
	//this.planRoot = centrePlan.getRoot();
	
	VBox root;
	Text textTop;
	TextArea textFieldCenter;
	PlanNode planRoot;
	PlanNode currNode;

	BorderPane bp;
	
	static Server testServer;
	static Client testClient;
	static Server actualServer;
	static Registry registry;
	
	private void startUp() throws IllegalArgumentException, RemoteException
	{
	
		
		Plan centrePlan = new Centre();
		//this.planRoot = centrePlan.getRoot();
		centrePlan.setName("Centre 2019");
		centrePlan.addNode(centrePlan.getRoot().getChildren().get(0).getChildren().get(0));
		centrePlan.getRoot().setData("This is the data of the root where we are hoping something will show up...");
		
		
		try
		{
			registry = LocateRegistry.createRegistry(1078);
			ServerImplementation server = new ServerImplementation();
			actualServer = server;
			Server stub = (Server) UnicastRemoteObject.exportObject(server, 0);
			registry.rebind("PlannerServer", stub);
			testServer = (Server) registry.lookup("PlannerServer");
			testClient = new Client(testServer);
			
			this.planFile = new PlanFile(null, true, centrePlan);
			planFile.setYear("2019");
			
			testClient.login("admin", "admin");
			actualServer.addDepartment("Centre", "0");
			actualServer.addPlanTemplate("Centre 2019", planFile);
			//testClient.login("user", "user");
			
			
			
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		testClient.getPlan("2019");
		
		this.planRoot = testClient.getCurrNode();
		
		
	}
	
	@FXML
	private VBox rightVBox;
	
	@FXML
	private TextArea contentsTextArea;
	
	@FXML
	private TreeView treeView;
	
	@FXML
	public void checkEdit()
	{
		if(!planFile.isCanEdit())
		{
			for(Node node:rightVBox.getChildren()) 
			{
				node.setOpacity(0);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
}
