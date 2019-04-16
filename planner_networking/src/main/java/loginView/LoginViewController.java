package loginView;

import java.rmi.RemoteException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import software_masters.planner_networking.Client;



public class LoginViewController
{
	public Client client;
	@FXML
	private TextField UsernameTextField;
	
	@FXML 
	private TextField PasswordTextField;
	
	@FXML 
	private Button LoginSubmitButton;
	
	@FXML
	public void onButtonSubmit(ActionEvent event) throws IllegalArgumentException, RemoteException{
		String username = UsernameTextField.textProperty().get();
		String password = PasswordTextField.textProperty().get();
		
		System.out.println(username);
		System.out.println(password);
		
		client.login(username, password);
		
	}
	
	
	
	
	
	
	
	
}
