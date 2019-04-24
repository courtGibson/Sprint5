package fx.test;

import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.ComboBoxMatchers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertThat;
import static org.testfx.assertions.api.Assertions.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import fx.choosePlan.ChoosePlanController;
import fx.homePageView.HomePageViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import loginView.LoginViewController;
import serverView.ServerViewController;
import software_masters.planner_networking.Client;
import software_masters.planner_networking.Main;
import software_masters.planner_networking.PlanFile;
import software_masters.planner_networking.Server;
import software_masters.planner_networking.ServerImplementation;



public class HomePageTest extends ApplicationTest
{
	private Server testServer;
	private Client testClient;
	private Server actualServer;
	private Registry registry;
	private Stage stage;

	@Override
	public void start(Stage stage) throws Exception
	{
		System.out.println("Start");
		this.stage = stage;
		
		registry = LocateRegistry.createRegistry(1077);
		
		ServerImplementation server = ServerImplementation.load();
		
		actualServer = server;
		Server stub = (Server) UnicastRemoteObject.exportObject(server, 0);
		registry.rebind("PlannerServer", stub);
		
		this.testServer = (Server) registry.lookup("PlannerServer");
		
		this.testClient = new Client(testServer);
		testClient.login("user", "user");
			

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/fx/homePageView/homePageView.fxml"));
		Scene s = new Scene(loader.load());
		HomePageViewController cont = loader.getController();
		
		cont.setPrimaryStage(stage);
		stage.setScene(s);
		stage.show();
		
		
		
	}
	
	
	@After
	public void tearDown () throws Exception
	{
		registry.unbind("PlannerServer");
		UnicastRemoteObject.unexportObject(registry, true);
		System.out.println("Closing RMI Server");
	}
	
	
	@Test
	public void testMenu()
	{
		//assertEquals(true, ((Node) lookup("#menu")).);
		assertEquals("Select plan", getComboBoxText("#menu"));
		clickOn("#menu");
		//clickOn("#menu");
		type(KeyCode.DOWN);
		//type(KeyCode.DOWN);
		type(KeyCode.ENTER);
		
		
		// Won't let me click on a menu item, cant convert node to combo box but
		// can't find another way to get it . . . 
		
		/*Node n =  (Node) ((ComboBox<?>) lookup("#menu")).getItems().get(0);
		((Node) n).setId("newItem");*/
		
		//clickOn(((ComboBox<String>) lookup("#menu")).getSelectionModel().selectFirst().get);
		
		//clickOn("#newItem");
		
		//assertEquals(getComboBoxText("#menu"), "2017");
		
		//clickOn("#submit");
		
	}
	
	
	
	private String getComboBoxText(String id)
	{

		ComboBox<String> c = lookup(id).query();
		return c.getPromptText();

	}
	
	@Test
	public void testLogout()
	{
		assertEquals("Logout", getButtonText("#logoutButton"));
		clickOn("#logoutButton");
		
	}
	
	private String getButtonText(String id)
	{
		Button b = lookup(id).query();
		return b.getText();
	}
	
	
	@Test
	public void testLabels()
	{
		// fxml doesn't have the labels??
		// should we just take it out or try to figure it out?
	}
	
	
	
	
}
