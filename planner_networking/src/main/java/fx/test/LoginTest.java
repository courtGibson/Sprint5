package fx.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.testfx.assertions.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loginView.LoginViewController;
import serverView.ServerViewController;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.Server;
import software_masters.planner_networking.ServerImplementation;

public class LoginTest extends ApplicationTest
{

	static Server testServer;
	static Client testClient;
	static Server actualServer;
	static Registry registry;
	private Stage primaryStage;
	BorderPane mainView;
	


	@Override
	public void start(Stage stage) throws Exception
	{
		System.out.println("Start");
		this.primaryStage = stage;

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/loginView/loginView.fxml"));
		try
		{
			Scene s = new Scene(loader.load());
			LoginViewController cont = loader.getController();
			cont.setPrimaryStage(primaryStage);
			primaryStage.setScene(s);
			primaryStage.show();
			
		} catch (IOException e)
		{

			e.printStackTrace();
		}
	}
	
	

	@Test
	public void testUsernameInput()
	{

		clickOn("#UsernameTextField");
		write("This is the username");

		assertEquals(getText("#UsernameTextField"), "This is the username");

	}

	@Test
	public void testPasswordInput()
	{

		clickOn("#PasswordTextField");
		write("This is the password");

		assertEquals(getText("#PasswordTextField"), "This is the password");

	}



	@SuppressWarnings("unchecked")
	String getText(String label)
	{

		TextField thisTextField = (TextField) lookup(label).query();
		return thisTextField.textProperty().get();

	}

}
