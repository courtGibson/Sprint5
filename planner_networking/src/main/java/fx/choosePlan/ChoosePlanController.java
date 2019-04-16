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
	Button btn1;
	Button btn2;
	Button btn3;
	
	
	
	
	/*private void startUp() throws IllegalArgumentException, RemoteException
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
	
	
	public void stop() throws AccessException, RemoteException, NotBoundException
	{
		System.out.println("unbinding server");
		registry.unbind("PlannerServer");
		// Unexport; this will also remove us from the RMI runtime
		UnicastRemoteObject.unexportObject(registry, true);
		System.out.println("Closing RMI Server");
	}
	
	
	@FXML
	private VBox rightVBox;
	
	@FXML
	private TextArea contentsTextArea;
	
	@FXML
	private TreeView<PlanNode> treeView;
	
	
	
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
	
	private VBox makeTree() throws RemoteException
	{
		TreeItem<PlanNode> rootItem = getProducts(this.planRoot);

		// Create the TreeView
		treeView.setRoot(rootItem);
		
		treeView.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> handleTreeClick(newValue));

		// Create the VBox
		
		VBox treeRoot = new VBox();
		
		
		this.root = treeRoot;
		
		
        Text planNameText = new Text();
		
		Label treeViewLabel = new Label();
		
		treeViewLabel.setText(testClient.getCurrPlanFile().getPlan().getName());
		
		
		treeRoot.getChildren().add(planNameText);
		//VBox.setAlignment(planNameText, Pos.CENTER);
		
	
		// Add the TreeView to the VBox
		treeRoot.getChildren().add(treeView);

		return treeRoot;

	}

	// This method creates an ArrayList of TreeItems (Products)
	
	/**
	 * gets items for TreeView
	 * 
	 * @param root node
	 * @return TreeItem 
	 * @throws RemoteException if unable to perform action
	 */
	/*public TreeItem<PlanNode> getProducts(PlanNode root) throws RemoteException
	{

		// This will be the final ArrayList passed back to FXTreeView.java for build
		// (should only hold Mission for centre)
		//ArrayList<TreeItem<PlanNode>> FinalNodeList = new ArrayList<TreeItem<PlanNode>>();

		TreeItem<PlanNode> currentTreeItem = new TreeItem<PlanNode>(root);

		//FinalNodeList.add(currentTreeItem);

		getKids(root, currentTreeItem);

		return currentTreeItem;
	}
	
	/**
	 * gets items to add to treeView
	 * 
	 * @param parentTreeItem TreeITem
	 * @param parentNode node
	 */
	/*private void getKids(PlanNode parentNode, TreeItem<PlanNode> parentTreeItem)
	{

	if (parentNode.getChildren().isEmpty())
		{
			return;
		}

		for (int i = 0; i < parentNode.getChildren().size(); i++)
		{
			TreeItem<PlanNode> newChild = new TreeItem<PlanNode>(parentNode.getChildren().get(i));

			parentTreeItem.getChildren().add(newChild);
			getKids(parentNode.getChildren().get(i), newChild);

		}

	}*/
	
	/**
	 * handles tree item click events
	 * 
	 * @param newValue treeItem
	 */
	/*private void handleTreeClick(TreeItem<PlanNode> newValue)
	{
		this.textTop.setText(newValue.getValue().getName());
		this.textFieldCenter.setText(newValue.getValue().getData());
		

		textFieldCenter.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
            public void handle(KeyEvent ke) 
            {
                //System.out.println(textFieldCenter.getText()+ke.getText());//.add("Key Pressed: " + ke.getText()););
                currNode.setData(textFieldCenter.getText()+ke.getText());
            }
        });

		
		this.currNode = newValue.getValue();
		
		this.btn1.setDisable(false);
		this.btn2.setDisable(false);
		this.btn3.setDisable(false);

		
	}*/

	
	
	
	
	
	
	
	
	
	
	
}
