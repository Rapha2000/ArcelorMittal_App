package view;

import controller.ControllerLogin;
import controller.ControllerMenu;
import controller.ControllerProcessEng;
import controller.ControllerWorker;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewMenu {
	
	private static ViewMenu instanceViewMenu = null;
	
	private ControllerMenu controllerMenu;
	private ControllerLogin controllerLogin;
	private ControllerWorker controllerWorker;
	private ControllerProcessEng controllerProcessEng;
	
	
	//Creation fenetre
	private Stage stageMenu;
	private Scene sceneMenu;
	private BorderPane paneMenu;
	
	//Creation message
	private Label label;
	
	private VBox vBox;
	
	//Boutons Worker & Process Eng	
	private Button buttonWorker;
	private Button buttonProcessEng;	
	
	
	private ViewMenu() {
		
	}
	
	public static ViewMenu getInstanceViewMenu() {
		if (instanceViewMenu == null) {
			instanceViewMenu = new ViewMenu();
		}
		return instanceViewMenu;
	}
	
	public void setViewMenu() {
		stageMenu = new Stage();
		paneMenu = new BorderPane();
		
		label = new Label("Merci de vous connecter en suivant la page de votre fonction.");
		
		vBox = new VBox();
		
		buttonWorker = new Button("Worker");
		buttonWorker.setOnAction(new ControllerMenu());
		buttonProcessEng = new Button("Process Engineer");
		buttonProcessEng.setOnAction(new ControllerMenu());
		
		vBox.getChildren().addAll(buttonWorker, buttonProcessEng);
		
		paneMenu.setTop(label);
		paneMenu.setCenter(vBox);
		//paneMenu.setAlignment(vBox, Pos.BOTTOM_CENTER);
		
		/*paneMenu = new GridPane();
		paneMenu.setAlignment(Pos.CENTER);
		paneMenu.addRow(0, buttonWorker);
		paneMenu.addRow(1, buttonProcessEng);*/
		
		sceneMenu = new Scene(paneMenu, 600, 600);
		
		stageMenu.setTitle("Menu");
		stageMenu.setScene(sceneMenu);
		stageMenu.show();
	}
		
	public void moveToLogin() {
		controllerLogin = new ControllerLogin();
		stageMenu.close();
		controllerLogin.show();
	}

}

