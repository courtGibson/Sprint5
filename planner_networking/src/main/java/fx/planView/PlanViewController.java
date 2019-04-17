package fx.planView;

import java.io.IOException;
import java.rmi.RemoteException;

import fx.checkSave.CheckSaveController;
import fx.homePageView.HomePageViewController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import loginView.LoginViewController;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.FXTreeView;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.PlanFile;
import software_masters.planner_networking.PlanNode;

public class PlanViewController
{
	Stage primaryStage;
	BorderPane mainView;
	Client testClient;
	PlanNode currentNode;
	
	Boolean builtTree = false;
	
	@FXML 
	private Button logoutButton;
	
	@FXML 
	private Button homepageButton;
	
	@FXML
	private TreeView tree;

	@FXML
	private TextArea contents;
	
	

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
	private Object PlanNode;
	
	
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
		
		if(saveBtn.isDisabled())
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
		
		else
		{
			
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/fx/checkSave/checkSave.fxml"));
			this.mainView = loader.load();
		
			CheckSaveController cont = loader.getController();
			cont.setTestClient(testClient);
			cont.setPrimaryStage(primaryStage);
		
			primaryStage.setWidth(800);
			primaryStage.getScene().setRoot(mainView);
		}
			
			
			
			
			
		
		
		
		
	}
	
	@SuppressWarnings("unchecked")
	public void buildTree() throws RemoteException {
		
		if(!builtTree) {
			System.out.println("Here");
			contents.setText("");
			TreeItem<PlanNode> theRoot = makeTree();
			tree.setRoot(theRoot);
			
			tree.getSelectionModel().selectedItemProperty()
	        .addListener((observable, oldValue, newValue) -> handleTreeClick((TreeItem<PlanNode>) newValue));
			builtTree = true;
		}
	}
	
	private void handleTreeClick(TreeItem<PlanNode> newValue)
	{
		
		this.currentNode = newValue.getValue();
		setContents(currentNode.getData());
		
	}
	

	public TreeItem<PlanNode> makeTree() throws RemoteException
	{

		TreeItem<PlanNode> rootItem = getProducts(testClient.getCurrPlanFile().getPlan().getRoot());

		
		

		return rootItem;

	}

	// This method creates an ArrayList of TreeItems (Products)
	public TreeItem<PlanNode> getProducts(PlanNode root) throws RemoteException
	{

		// This will be the final ArrayList passed back to FXTreeView.java for build
		// (should only hold Mission for centre)
		//ArrayList<TreeItem<PlanNode>> FinalNodeList = new ArrayList<TreeItem<PlanNode>>();

		TreeItem<PlanNode> currentTreeItem = new TreeItem<PlanNode>(root);

		//FinalNodeList.add(currentTreeItem);

		getKids(root, currentTreeItem);

		return currentTreeItem;
	}
	

	private void getKids(PlanNode parentNode, TreeItem<PlanNode> parentTreeItem)
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

	}
	
	
	public void removeNode() throws RemoteException
	{
		
		
		this.testClient.setCurrNode(currentNode);
		
		this.testClient.removeBranch();
		
		builtTree = false;
		buildTree();
			
	}
	
	public void addNode() throws RemoteException
	{
		this.testClient.setCurrNode(currentNode);
		this.testClient.addBranch();
		
		builtTree = false;
		buildTree();
		
		
	}

	public void setContents(String stringContent) {
		
		contents.setText(stringContent);
	}
	
	

}