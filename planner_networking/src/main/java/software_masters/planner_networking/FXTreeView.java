package software_masters.planner_networking;


import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.event.ChangeListener;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
 
public class FXTreeView extends Application
{
	VBox root;
	
    public static void main(String[] args) 
    {
        Application.launch(args);
    }
         
    @Override
    public void start(Stage primaryStage) throws RemoteException
    {
    	//Makes the treeview and returns a VBox
    	VBox treeRoot = makeTree();
    	
    	
        primaryStage.setTitle("Plan View");
        
        //Making a border pane 
        BorderPane bp = new BorderPane(); 
        
        //Create the Text Nodes
        Text topText = new Text("Plan View");
      
        Text leftText = new Text("This is where a tree should be");
        BorderPane bpCenter = new BorderPane();
        
        bp.setCenter(bpCenter);
        bp.setTop(topText);
        bp.setLeft(leftText);
       
        Text topTextBPCenter = new Text("Strategy 1");
        bpCenter.setTop(topTextBPCenter);
        
        makeRightSide(bp);
        
        bp.setLeft(treeRoot);
        
        
        
        //Making the text field in the middle in bpCenter
        TextField textFieldTop = new TextField();
        textFieldTop.setPromptText("Node Name");
        bpCenter.setTop(textFieldTop);
        
        TextField textFieldCenter = new TextField();
        textFieldCenter.setPromptText("Node Contents");
        bpCenter.setCenter(textFieldCenter);
        


        //Set the Size of the BP
        bp.setPrefSize(1000, 600);     
        // Create the Scene
        Scene scene = new Scene(bp);
        // Add the scene to the Stage
        primaryStage.setScene(scene);
        // Set the title of the Stage
        primaryStage.setTitle("Team Sport");
        // Display the Stage
        primaryStage.show(); 
     
    }
    
    private void makeRightSide(BorderPane bp) {
    	//Building the buttons on the right
        VBox rightSide = new VBox();
        
        Text rightSideLabel = new Text("Editing Tools");
        rightSide.getChildren().add(rightSideLabel);
        
        Button btn1 = new Button("Remove");
        
        Button btn2 = new Button("Add Subsection");
      
        Button btn3 = new Button("Save");
        
        rightSide.getChildren().addAll(btn1, btn2, btn3);
        
        
        bp.setRight(rightSide);
    }

	private VBox makeTree() throws RemoteException
	{

		Plan centrePlan = new Centre();
    	centrePlan.setName("Centre 2019");
    	centrePlan.addNode(centrePlan.getRoot().getChildren().get(0).getChildren().get(0));
    	centrePlan.getRoot().setData("This is the data of the root where we are hoping something will show up...");
    	Node planRoot = centrePlan.getRoot();
    	
    	
        // Create the TreeViewHelper
//        TreeViewHelper helper = new TreeViewHelper();
//        // Get the Products
//
//        ArrayList<TreeItem> products = helper.getProducts(planRoot);
    	
    	ArrayList<TreeItem> products = getProducts(planRoot);

        // Create the TreeView
        TreeView treeView = new TreeView();
        // Create the Root TreeItem

 
        treeView.getSelectionModel()
        .selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> System.out.println(newValue.toString()));

        
        TreeItem rootItem = new TreeItem(centrePlan.getName());

        // Add children to the root
        rootItem.getChildren().addAll(products);
        // Set the Root Node
        treeView.setRoot(rootItem);
        
        // Create the VBox
        VBox treeRoot = new VBox();
        this.root = treeRoot;
        // Add the TreeView to the VBox
        treeRoot.getChildren().add(treeView);
        
        return treeRoot;
		
	}
	
	
	 // This method creates an ArrayList of TreeItems (Products)
    public ArrayList<TreeItem> getProducts(Node root) throws RemoteException
    {	
    	
    	//This will be the final ArrayList passed back to FXTreeView.java for build (should only hold Mission for centre)
    	ArrayList<TreeItem> FinalNodeList = new ArrayList<TreeItem>();
         
    	
    	TreeItem currentTreeItem = new TreeItem(root.getName());
    	
    	FinalNodeList.add(currentTreeItem);
    	
    	getKids(root, currentTreeItem);
        
        return FinalNodeList;
    }
    
    private void getKids(Node parentNode, TreeItem parentTreeItem){
		
    	if(parentNode.getChildren().isEmpty()) {
    		return;
    	}
    	
    	for(int i=0; i<parentNode.getChildren().size(); i++)
    	{
    		TreeItem newChild = new TreeItem(parentNode.getChildren().get(i).getName());
    		
    		//newChild.setOnAction(new EventHandler<ActionEvent>()  {
    			//public void handle(ActionEvent e) {
    				//System.out.println("Here");
    			//}
    		//});
    		
    		parentTreeItem.getChildren().add(newChild);
    		getKids(parentNode.getChildren().get(i), newChild);
    		
    	}
    	

    }
    
    
}


