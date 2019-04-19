package fx.test;

import static org.testfx.assertions.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import serverView.ServerViewController;
import software_masters.planner_networking.Main;


public class ServerSelectionTest extends ApplicationTest
{
	
	//Model model = new Model();
	
	Stage stage;
	BorderPane mainView;

	//
	// test clicks
	// after submit, check to see that server was made
	
	
	@Before
	public void init()
	{

		
	}
	
	
	
	@Override
	public void start(Stage stage) throws Exception 
	{
		this.stage=stage;
	
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("cashView/cashView.fxml"));
		try {
			Scene s = new Scene(loader.load());
			ServerViewController cont = loader.getController();
			stage.setScene(s);
			stage.show();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
			
	}
	
	
	//other methods
	
    //@test methods
	
}
