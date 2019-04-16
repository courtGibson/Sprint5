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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	TextArea textFieldCenter;
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
	
	
	/**
	 * Sets up initial data
	 * 
	 * 
	 * @throws IllegalArgumentException 
	 * @throws RemoteException
	 */
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

	/**
	 * makes stage and other window elements
	 * 
	 *
	 * @param primaryStage stage
	 * @throws RemoteException if unable to perform action
	 */
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
		topText.setStyle("-fx-font-size: 40px;");
		BorderPane.setAlignment(topText, Pos.CENTER);
		
		Text leftText = new Text("This is where a tree should be");
		BorderPane.setAlignment(leftText, Pos.CENTER);
		BorderPane bpCenter = new BorderPane();
		//BorderPane bpTop = new BorderPane();
		
		

		bp.setCenter(bpCenter);
		bp.setTop(topText);
		bp.setLeft(leftText);

		//Text topTextBPCenter = new Text("Strategy 1");
		
		//BorderPane.setAlignment(topTextBP, Pos.CENTER);
		//bpCenter.setTop(topTextBPCenter);

		makeRightSide(bp);

		bp.setLeft(treeRoot);

		// Making the text field in the middle in bpCenter
		this.textTop = new Text();
		textTop.setText("Node Name");
		((BorderPane) bpCenter.getTop()).setAlignment(textTop, Pos.CENTER);
		textTop.setStyle("-fx-font-size: 20px;");
		bpCenter.setTop(textTop);
	    
		

		this.textFieldCenter = new TextArea();
		
		textFieldCenter.setMaxWidth(400);
		textFieldCenter.setMaxHeight(75);
		textFieldCenter.setWrapText(true);
		textFieldCenter.setStyle("-fx-font-size: 14px;\n-fx-font-color: black;");
		
		Label l1= new Label("Contents:");
		l1.setStyle("-fx-font-size: 18px;");
		
		HBox hb = new HBox();
		hb.getChildren().addAll(l1, textFieldCenter);
		hb.setSpacing(10);
		
		BorderPane.setMargin(bpCenter, new Insets(100,25,10,25));
		BorderPane.setMargin(bpCenter.getTop(), new Insets(10,100,20,25));
		textFieldCenter.setPromptText("Node Contents");
		bpCenter.setCenter(hb);
	
		
		

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
		
		/*primaryStage.setOnCloseRequest( event -> {try
		{
			stop();
		} catch (RemoteException | NotBoundException e)
		{
			
			e.printStackTrace();
		}} );*/

	}
	
	/**
	 * shuts down server connection when window closed
	 * 
	 * @throws AccessException if unable to perform action
	 * @throws RemoteException if unable to perform action
	 * @throws NotBoundException if unable to perform action
	 */
	@Override
	public void stop() throws AccessException, RemoteException, NotBoundException
	{
		System.out.println("unbinding server");
		registry.unbind("PlannerServer");
		// Unexport; this will also remove us from the RMI runtime
		UnicastRemoteObject.unexportObject(registry, true);
		System.out.println("Closing RMI Server");
	}
	
	
	/**
	 * removes node and branch
	 * 
	 * @param btn1 remove button
	 * @throws RemoteException
	 */
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
	
	/**
	 * adds node and branch
	 * 
	 * @param btn2 add button
	 * @throws RemoteException
	 */
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
	
	/**
	 * saves plan file
	 * 
	 * @param btn3 save button
	 * @throws RemoteException
	 */
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
	
	
	/**
	 * Makes edit buttons, if plan is not editable, will not be visable
	 * 
	 * @param bp borderpane
	 * 
	 */
	private void makeRightSide(BorderPane bp)
	{
		// Building the buttons on the right
		if (planFile.isCanEdit())
		{
			VBox rightSide = new VBox();
			
			rightSide.setPrefWidth(100);

			
			rightSide.setPadding(new Insets(30, 30, 50, 30));

			Text rightSideLabel = new Text("Editing Tools");
			rightSideLabel.setStyle("-fx-font-size: 20px;");
			rightSide.getChildren().add(rightSideLabel);

			this.btn1 = new Button("Remove");
			btn1.setMinWidth(rightSide.getPrefWidth());
			
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
			btn2.setMinWidth(rightSide.getPrefWidth());
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
			btn3.setMinWidth(rightSide.getPrefWidth());
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
			
			rightSide.setSpacing(30.0);
			
			rightSide.setStyle("-fx-border-color: lightgrey;\n-fx-border-insets: 5;\n-fx-border-width: 3;\n-fx-background-color: lightgrey;");
			    

			bp.setRight(rightSide);
			
			
		}
	
	}
	
	/**
	 * makes tree view for the left side of the borderpane
	 * 
	 * @return VBox
	 * @throws RemoteExcpetion
	 */
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
	
	/**
	 * gets items for TreeView
	 * 
	 * @param root node
	 * @return TreeItem 
	 * @throws RemoteException if unable to perform action
	 */
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
	
	/**
	 * gets items to add to treeView
	 * 
	 * @param parentTreeItem TreeITem
	 * @param parentNode node
	 */
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
	
	/**
	 * handles tree item click events
	 * 
	 * @param newValue treeItem
	 */
	private void handleTreeClick(TreeItem<Node> newValue)
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
