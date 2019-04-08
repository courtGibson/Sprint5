package software_masters.planner_networking;

import java.rmi.RemoteException;
import java.util.ArrayList;
import javafx.scene.control.TreeItem;
 
public class TreeViewHelper 
{
    public TreeViewHelper()
    {
    }
     
    public ArrayList<TreeItem> getProducts(Node root) throws RemoteException
    {
    	ArrayList<TreeItem> initialArray = new ArrayList<TreeItem>();
    	TreeItem rootItem = new TreeItem(root);
    	return nodeSearch(rootItem, root, initialArray);
    	
    }
    
    private ArrayList<TreeItem> nodeSearch(TreeItem currItem, Node currNode, ArrayList<TreeItem> bigArray)
    {
    	
    	// Kid is TreeItem
    	// child is Node
    	
    	if(currNode.getChildren().size() == 0) 
    	{
    		return bigArray;
    	}
    	
    	ArrayList<TreeItem> currentArray = new ArrayList<TreeItem>();
    	
    	ArrayList<TreeItem> kidArray = new ArrayList<TreeItem>();
    	ArrayList<Node> nodeArray = new ArrayList<Node>();
    	
    	for(Node child : currNode.getChildren()) 
    	{
    		TreeItem kid = new TreeItem(child.getName());
    		kidArray = nodeSearch(kid, child, currentArray);
    		nodeArray.add(child);
    		
    	}
    	currNode.getChildren().addAll(nodeArray);
		//currentArray.add(kidArray);
    	
    	return currentArray;
    }
    
    
/*    // This method creates an ArrayList of TreeItems (Products)
    public ArrayList<TreeItem> getProducts(Node root) throws RemoteException
    {	
    	
    	//This will be the final ArrayList passed back to FXTreeView.java for build
    	ArrayList<TreeItem> FinalNodeList = new ArrayList<TreeItem>();
         
    	
    	
    	TreeItem currentNode = new TreeItem(root.getName());
    	
    	ArrayList<TreeItem> firstNodeList = new ArrayList<TreeItem>();
    	
    	TreeItem thisNode = new TreeItem(root.getChildren().get(0).getName());
    	TreeItem thisNode2 = new TreeItem(root.getChildren().get(0).getChildren().get(0).getName());
    	
    	firstNodeList.add(thisNode);
    	firstNodeList.add(thisNode2);
    	currentNode.getChildren().addAll(firstNodeList);
    	
    	
    	
    	
    	TreeItem secondNode = new TreeItem(root.getChildren().get(0).getName());
    	secondNode.getChildren().addAll(currentNode.getChildren());
    	
    	
    	FinalNodeList.add(currentNode);
    	FinalNodeList.add(secondNode);
    	

        
        return FinalNodeList;
    }*/
    
//    private ArrayList<TreeItem> getKids(Node root, ArrayList<TreeItem> nodes)
//    {
//    	ArrayList<Node> children = root.getChildren();
//    	
//    	TreeItem firstNode = new TreeItem(root.getName());
//        firstNode.getChildren().addAll(firstNode.getChildren());
//        
//         
//        nodes.add(firstNode);
//         
//        for(int i = 0; i < children.size(); i++) {
//        	root = children.get(i);
//        	getKids(root, nodes);
//        }
//		return nodes;
//    }
 

}
