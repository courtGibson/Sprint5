package software_masters.planner_networking;


import java.rmi.RemoteException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
 
public class FXTreeView extends Application
{
    public static void main(String[] args) 
    {
        Application.launch(args);
    }
         
    @Override
    public void start(Stage stage) throws RemoteException
    {
    	Plan centrePlan = new Centre();
    	centrePlan.setName("Centre 2019");
    	centrePlan.addNode(centrePlan.getRoot().getChildren().get(0).getChildren().get(0));
    	Node planRoot = centrePlan.getRoot();
    	
    	
        // Create the TreeViewHelper
        TreeViewHelper helper = new TreeViewHelper();
        // Get the Products
<<<<<<< HEAD
        ArrayList<TreeItem> products = helper.getProducts(planRoot);
=======
        ArrayList<TreeItem> nodes = helper.getNodes(root);
>>>>>>> 84f8bbf0bd5d4a59cff6a110741f6874c1a4ef44
         
        // Create the TreeView
        TreeView treeView = new TreeView();
        // Create the Root TreeItem
<<<<<<< HEAD
        TreeItem rootItem = new TreeItem(centrePlan.getName());
=======
        TreeItem rootItem = new TreeItem(root);
>>>>>>> 84f8bbf0bd5d4a59cff6a110741f6874c1a4ef44
        // Add children to the root
        rootItem.getChildren().addAll(nodes);
        // Set the Root Node
        treeView.setRoot(rootItem);
         
        // Create the VBox
        VBox root = new VBox();
        // Add the TreeView to the VBox
        root.getChildren().add(treeView);       
         
        // Create the Scene
        Scene scene = new Scene(root,400,400);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title for the Scene
        stage.setTitle("TreeView Example 1");
        // Display the stage
        stage.show();       
    }
}


