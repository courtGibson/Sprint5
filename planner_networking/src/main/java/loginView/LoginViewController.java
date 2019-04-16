package loginView;

import java.rmi.RemoteException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import software_masters.planner_networking.Client;



public class LoginViewController
{
	Stage primaryStage;
	BorderPane mainView;
	Client testClient;
	
	
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



	
	public Client getTestClient()
	{
	
		return testClient;
	}


	public void setTestClient(Client testClient)
	{
	
		this.testClient = testClient;
	}


	
	@FXML
	private TextField UsernameTextField;
	
	@FXML 
	private TextField PasswordTextField;
	
	@FXML 
	private Button LoginSubmitButton;
	
	
	@FXML
	public void onButtonSubmit() throws IllegalArgumentException, RemoteException{
		String username = UsernameTextField.textProperty().get();
		String password = PasswordTextField.textProperty().get();
		
		System.out.println(username);
		System.out.println(password);
		// sends to login function
		testClient.login(username, password);
		System.out.print(testClient.getCookie());
		
	}



	
	
	
	
	
	
	
	
}
