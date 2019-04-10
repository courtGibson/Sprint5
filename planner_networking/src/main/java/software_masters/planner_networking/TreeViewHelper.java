package software_masters.planner_networking;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
 
public class TreeViewHelper 
{
    public TreeViewHelper()
    {
    
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
    		
    		newChild.setOnAction(new EventHandler<ActionEvent>()  {
    			public void handle(ActionEvent e) {
    				System.out.println("Here");
    			}
    		});
    		
    		parentTreeItem.getChildren().add(newChild);
    		getKids(parentNode.getChildren().get(i), newChild);
    		
    	}
    	

    }

 

}
