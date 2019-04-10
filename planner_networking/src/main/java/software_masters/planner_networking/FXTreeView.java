package software_masters.planner_networking;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.swing.event.ChangeListener;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class FXTreeView extends Application
{
	VBox root;
	Text textTop;
	TextField textFieldCenter;
	Node planRoot;
	Node currNode;
	PlanFile planFile;

	BorderPane bp;
	Button btn1;
	Button btn2;
	Button btn3;
	
	static Server testServer;
	static Client testClient;
	static Server actualServer;
	static Registry registry;

	public static void main(String[] args)
	{
		Application.launch(args);
	}
	
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
			
			this.planFile = new PlanFile(null, false, centrePlan);
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

	@Override
	public void start(Stage primaryStage) throws RemoteException
	{
		primaryStage.close();
		startUp();
		
		// Makes the treeview and returns a VBox
		
		VBox treeRoot = makeTree();

		primaryStage.setTitle("Plan View");

		// Making a border pane
		this.bp = new BorderPane();

		// Create the Text Nodes
		Text topText = new Text("Plan View");
		BorderPane.setAlignment(topText, Pos.CENTER);
		
		Text leftText = new Text("This is where a tree should be");
		BorderPane.setAlignment(leftText, Pos.CENTER);
		BorderPane bpCenter = new BorderPane();

		bp.setCenter(bpCenter);
		bp.setTop(topText);
		bp.setLeft(leftText);

		Text topTextBPCenter = new Text("Strategy 1");
		
		//BorderPane.setAlignment(topTextBP, Pos.CENTER);
		bpCenter.setTop(topTextBPCenter);

		makeRightSide(bp);

		bp.setLeft(treeRoot);

		// Making the text field in the middle in bpCenter
		this.textTop = new Text();
		textTop.setText("Node Name");
		bpCenter.setTop(textTop);
	    
		

		this.textFieldCenter = new TextField();
		textFieldCenter.setPromptText("Node Contents");
		bpCenter.setCenter(textFieldCenter);
		
		

		// Set the Size of the BP
		bp.setPrefSize(1000, 600);
		// Create the Scene
		Scene scene = new Scene(bp);
		// Add the scene to the Stage
		primaryStage.setScene(scene);
		// Set the title of the Stage
		primaryStage.setTitle("Team Sport");
		// Display the Stage
		primaryStage.show();
		
		primaryStage.setOnCloseRequest( event -> {try
		{
			stop();
		} catch (RemoteException | NotBoundException e)
		{
			
			e.printStackTrace();
		}} );

	}
	
	@Override
	public void stop() throws AccessException, RemoteException, NotBoundException
	{
		registry.unbind("PlannerServer");
		// Unexport; this will also remove us from the RMI runtime
		UnicastRemoteObject.unexportObject(registry, true);
		System.out.println("Closing RMI Server");
	}
	
	
	
	private void removeNode(Button btn1) throws RemoteException
	{
		this.testClient.setCurrNode(this.currNode);
		this.testClient.removeBranch();
		
		VBox newTree = makeTree();
		this.bp.setLeft(newTree);
		this.textFieldCenter.clear();
		this.textTop.setText("");
		this.currNode = null;
		
		this.btn1.setDisable(true);
		this.btn3.setDisable(false);
		
	}
	
	private void addNode(Button btn2) throws RemoteException
	{
		this.testClient.setCurrNode(this.currNode);
		this.testClient.addBranch();
		
		VBox newTree = makeTree();
		this.bp.setLeft(newTree);
		this.textFieldCenter.clear();
		this.textTop.setText("");
		this.currNode = null;
		
		this.btn2.setDisable(true);
		this.btn3.setDisable(false);
	}
	
	private void save(Button btn3) throws RemoteException
	{
		this.testClient.setCurrNode(this.currNode);
		this.testClient.pushPlan(planFile);
		
		VBox newTree = makeTree();
		this.bp.setLeft(newTree);
		this.textFieldCenter.clear();
		this.textTop.setText("");
		this.currNode = null;
		
		this.btn2.setDisable(true);
	}

	private void makeRightSide(BorderPane bp)
	{
		// Building the buttons on the right
		if (planFile.isCanEdit())
		{
			VBox rightSide = new VBox();

			Text rightSideLabel = new Text("Editing Tools");
			rightSide.getChildren().add(rightSideLabel);

			this.btn1 = new Button("Remove");
			this.btn1.setDisable(true);
			this.btn1.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
			        try
					{
						removeNode(btn1);
					} catch (RemoteException e1)
					{
				
						e1.printStackTrace();
					}
			    }


			});
			


			this.btn2 = new Button("Add Subsection");
			this.btn2.setDisable(true);
			this.btn2.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
			        try
					{
						addNode(btn2);
					} catch (RemoteException e1)
					{
			
						e1.printStackTrace();
					}
			    }


			});

			this.btn3 = new Button("Save");
			this.btn3.setDisable(true);
			
			this.btn3.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
			        try
					{
						save(btn3);
					} catch (RemoteException e1)
					{
			
						e1.printStackTrace();
					}
			    }


			});

			rightSide.getChildren().addAll(btn1, btn2, btn3);

			bp.setRight(rightSide);
		}
	
	}

	private VBox makeTree() throws RemoteException
	{
		
		

		// Create the TreeViewHelper
//        TreeViewHelper helper = new TreeViewHelper();
//        // Get the Products
//
//        ArrayList<TreeItem> products = helper.getProducts(planRoot);

		TreeItem<Node> rootItem = getProducts(this.planRoot);

		// Create the TreeView
		TreeView<Node> treeView = new TreeView<Node>();
		treeView.setRoot(rootItem);
		
		treeView.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> handleTreeClick(newValue));
		// Create the Root TreeItem
		
		//TreeItem<Node> rootItem = new TreeItem<Node>(centrePlan.getRoot());

		// Add children to the root
		//rootItem.getChildren().addAll(products);
		// Set the Root Node

		// Create the VBox
		
		VBox treeRoot = new VBox();
		
		
		this.root = treeRoot;
		
		
        Text planNameText = new Text();
		
		planNameText.setText(testClient.getCurrPlanFile().getPlan().getName());
		treeRoot.setAlignment(Pos.CENTER);
		
		treeRoot.getChildren().add(planNameText);
		//VBox.setAlignment(planNameText, Pos.CENTER);
		
	
		// Add the TreeView to the VBox
		treeRoot.getChildren().add(treeView);

		return treeRoot;

	}

	// This method creates an ArrayList of TreeItems (Products)
	public TreeItem<Node> getProducts(Node root) throws RemoteException
	{

		// This will be the final ArrayList passed back to FXTreeView.java for build
		// (should only hold Mission for centre)
		//ArrayList<TreeItem<Node>> FinalNodeList = new ArrayList<TreeItem<Node>>();

		TreeItem<Node> currentTreeItem = new TreeItem<Node>(root);

		//FinalNodeList.add(currentTreeItem);

		getKids(root, currentTreeItem);

		return currentTreeItem;
	}

	private void getKids(Node parentNode, TreeItem<Node> parentTreeItem)
	{

		if (parentNode.getChildren().isEmpty())
		{
			return;
		}

		for (int i = 0; i < parentNode.getChildren().size(); i++)
		{
			TreeItem<Node> newChild = new TreeItem<Node>(parentNode.getChildren().get(i));

			parentTreeItem.getChildren().add(newChild);
			getKids(parentNode.getChildren().get(i), newChild);

		}

	}
	
	private void handleTreeClick(TreeItem<Node> newValue)
	{
		this.textTop.setText(newValue.getValue().getName());
		this.textFieldCenter.setText(newValue.getValue().getData());
		
		
		/*textField.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
            public void handle(KeyEvent ke) 
            {
                System.out.println(textField.getText()+ke.getText());//.add("Key Pressed: " + ke.getText()););
                currNode.setName(textField.getText()+ke.getText());
            }
        });*/
		
		textFieldCenter.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
            public void handle(KeyEvent ke) 
            {
                System.out.println(textFieldCenter.getText()+ke.getText());//.add("Key Pressed: " + ke.getText()););
                currNode.setData(textFieldCenter.getText()+ke.getText());
            }
        });
		
		/*textField.textProperty().addListener((obs, oldText, newText) -> {
		    System.out.println("Text changed from "+oldText+" to "+newText);
		    this.btn3.setDisable(false);
		    
		    String newData = textField.getText();
		    this.currNode.setName(newData);
		    // ...
		});
		
		textFieldCenter.textProperty().addListener((obs, oldText, newText) -> {
			System.out.println("Text changed from "+oldText+" to "+newText);
			this.btn3.setDisable(false);
			
		    String newData = textFieldCenter.getText();
		    this.currNode.setData(newData);
		    // ...
		});*/
		
		this.currNode = newValue.getValue();
		
		this.btn1.setDisable(false);
		this.btn2.setDisable(false);
		this.btn3.setDisable(false);

		
	}
	
	/*public void setServer(Server server)
	{
		this.testServer = server;
	}*/
	public Server getServer()
	{
		return this.testServer;
	}
	/*public void setClient(Client client)
	{
		this.testClient = client;
	}*/
	public Client getClient() 
	{
		return this.testClient;
	}

}
