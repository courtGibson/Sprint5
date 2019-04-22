package fx.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.assertions.api.Assertions.assertThat;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import serverView.ServerViewController;
import software_masters.planner_networking.Main;


public class ServerSelectionTest extends ApplicationTest
{
	
	//Model model = new Model();
	
	Stage stage;
	BorderPane mainView;
	ServerViewController cont;

	// test clicks
	// after submit, check to see that server was made
	
	
	/*@Before
	public void init()
	{
	   
		
	}*/
	
	
	
	
	@Override
	public void start(Stage stage) throws Exception 
	{
		this.stage=stage;
	
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/serverView/serverView.fxml"));
		try {
			Scene s = new Scene(loader.load());
			cont = loader.getController();
			stage.setScene(s);
			stage.show();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
			
	}
	
	
	//other methods
	public void checkRBText(String id, String val)
	{
		assertThat(lookup(id).queryAs(Text.class)).hasText(val);
	
	}
	

	public void type(String id, String val)
	{
		clickOn(id);
		push(javafx.scene.input.KeyCode.SHORTCUT, javafx.scene.input.KeyCode.A);
		write(val);
		
	}
	
	
	
    //@test methods
	@Test
	public void checkText()
	{
		
		clickOn("#DefaultServerButton");
		sleep(1000);

		checkRBText("#localText", "Default: Local Host");
		checkRBText("#otherText", "Other:");
		
		clickOn("#OtherServerButton");
		sleep(1000);
		
		type("#OtherServerTextField", "127.0.0.1");
		
		//cont.connectToServer();
		
		
		
		clickOn("#ServerSubmitButton");
		
		assertThat(cont.getTestClient().getServer()!=null);
		
	
	
	
	
	
	
	
	}
	
}
