package fx.test;

import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
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

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/loginView/loginView.fxml"));
		try
		{
			Scene s = new Scene(loader.load());
			LoginViewController cont = loader.getController();
			cont.setPrimaryStage(stage);
			stage.setScene(s);
			stage.show();
			
		} catch (IOException e)
		{

			e.printStackTrace();
		}
	}
	

	@Before
	public void setUp () throws Exception 
	{
		System.out.println("Starting Test");
		try
		{
			registry = LocateRegistry.createRegistry(1077);
			
			ServerImplementation server = ServerImplementation.load();
			
			actualServer = server;
			Server stub = (Server) UnicastRemoteObject.exportObject(server, 0);
			registry.rebind("PlannerServer", stub);
			
			this.testServer = (Server) registry.lookup("PlannerServer");
			this.testClient = new Client(testServer);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	@After
	public void tearDown () throws Exception
	{
		registry.unbind("PlannerServer");
		UnicastRemoteObject.unexportObject(registry, true);
		System.out.println("Closing RMI Server");
	}
}
