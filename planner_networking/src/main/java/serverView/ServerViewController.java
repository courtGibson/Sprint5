package serverView;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Server;
import software_masters.planner_networking.ServerImplementation;

public class ServerViewController
{
	@FXML
	private RadioButton DefaultServerButton;
	
	@FXML
	private RadioButton OtherServerButton;
	
	@FXML 
	private TextField OtherServerTextField;
	
	@FXML
	private Button ServerSubmitButton;
	
	static Server testServer;
	static Client testClient;
	static Server actualServer;
	static Registry registry;
	
	public void connectToServer() {
		if(DefaultServerButton.isSelected()) {
			try
			{
				registry = LocateRegistry.createRegistry(1075);
				ServerImplementation server = new ServerImplementation();
				actualServer = server;
				Server stub = (Server) UnicastRemoteObject.exportObject(server, 0);
				registry.rebind("PlannerServer", stub);
				testServer = (Server) registry.lookup("PlannerServer");
				testClient = new Client(testServer);
				
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else {
			
			String hostName = OtherServerTextField.getAccessibleText();
			try
			{
				registry = LocateRegistry.getRegistry(hostName, 1075);
				testServer = (Server) registry.lookup("PlannerServer");
				testClient = new Client(testServer);
				
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
