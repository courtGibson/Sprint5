package loginView;

import java.rmi.RemoteException;

import fx.homePageView.HomePageViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import software_masters.planner_networking.Account;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Department;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.Server;




public class LoginViewController
{
	Stage primaryStage;
	BorderPane mainView;
	Client testClient;
	Server server;
	
	public void setServer(Server server)
	{
		this.server = server;
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
	public void onButtonSubmit() throws Exception{
		String username = UsernameTextField.textProperty().get();
		String password = PasswordTextField.textProperty().get();
		
		System.out.println(username);
		System.out.println(password);
		// sends to login function
		testClient.login(username, password);
		System.out.print(testClient.getCookie());
		System.out.print("logged in");
		
		getConnected(testClient, username);
		
		
		
		
	}

	// switch scenes
	private void getConnected(Client testClient, String username) throws Exception
	{

		this.testClient = testClient;
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/fx/homePageView/homePageView.fxml"));
		this.mainView = loader.load();
		
		HomePageViewController cont = loader.getController();
		cont.setUser(username);

		//String deptName = this.server.getCookieMap().get(testClient.getCookie()).getDepartment().getDeptName();
		//System.out.println("deptName: "+deptName);
		//cont.setDept(deptName);
		cont.setTestClient(testClient);
		//System.out.println(this.server.getCookieMap().get(testClient.getCookie()).getDepartment().getDeptName());
		//String deptName = this.server.getCookieMap().get(testClient.getCookie()).getDepartment().getDeptName();
		
		
		//Account userAccount = this.cookieMap.get(cookie);
		// department = userAccount.getDepartment();
		
		
		cont.setPrimaryStage(primaryStage);
		
		
		
		
	
	
		
		
		primaryStage.setWidth(800);
		primaryStage.getScene().setRoot(mainView);
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
}
