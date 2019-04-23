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
import java.rmi.registry.Registry;

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


public class LoginTest extends ApplicationTest
{
	
	static Server testServer;
	static Client testClient;
	static Server actualServer;
	static Registry registry;
	private Stage stage;
	
	@Override
	public void start(Stage stage) throws Exception 
	{
		this.stage=stage;
	
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/loginView/loginView.fxml"));
		try {
			Scene s = new Scene(loader.load());
			LoginViewController cont = loader.getController();
			cont.setPrimaryStage(stage);
			stage.setScene(s);
			stage.show();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
			
	}
	
	  @Test
	  public void testUsernameInput() {
		  
		  clickOn("#UsernameTextField");
		  write("This is the username");
		  
		  assertEquals(getText("#UsernameTextField"), "This is the username");

		  
	  }
	  
	  @Test
	  public void testPasswordInput() {
		  
		  clickOn("#PasswordTextField");
		  write("This is the password");
		  
		  assertEquals(getText("#PasswordTextField"), "This is the password");
		  
	  }
	  
	  @Test
	  public void testEnterButton() {
		  clickOn("#UsernameTextField");
		  write("user");
		  clickOn("#PasswordTextField");
		  write("user");
		  clickOn("#LoginSubmitButton");
		  
		  System.out.println("Here");
		  assertEquals(getText("#PasswordTextField"), "user");
		  
	  }
	  
	  @SuppressWarnings("unchecked")	  
	  String getText(String label)
	  {
		  TextField thisTextField = (TextField) lookup(label).query();
		  return thisTextField.textProperty().get();
			
	  }
	
}
