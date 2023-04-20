package view;

import controller.ControllerLogin;
import controller.ControllerMenu;
import controller.ControllerProcessEng;
import controller.ControllerWorker;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
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


/**

La classe ViewMenu représente l'interface graphique de l'application. Elle est utilisée pour afficher
le menu principal qui permet de se connecter en tant que Worker ou Process Engineer.
Elle utilise les classes ControllerLogin, ControllerMenu, ControllerProcessEng et ControllerWorker
pour gérer les actions liées à la connexion et la navigation dans l'application.
La classe implémente le design pattern Singleton pour s'assurer qu'il n'y a qu'une seule instance
de la classe dans l'application.
*/
public class ViewMenu {
	
	// Instance unique de la classe ViewMenu
	private static ViewMenu instanceViewMenu = null;
	
	// Contrôleurs utilisés par la vue
	private ControllerMenu controllerMenu;
	private ControllerLogin controllerLogin;
	private ControllerWorker controllerWorker;
	private ControllerProcessEng controllerProcessEng;
	
	

	// Fenêtre principale, scène et grille
	private Stage stageMenu;
	private Scene sceneMenu;
	private GridPane paneMenu;
	
	//Creation message
	private Label label;
	
	//Boutons Worker & Process Eng	
	private Button buttonWorker;
	private Button buttonProcessEng;	
	
	/**
	 * Constructeur privé pour implémenter le design pattern Singleton
	 */
	private ViewMenu() {
		
	}
	
	
	public static ViewMenu getInstanceViewMenu() { //récupère l'instance courante car on est dans un pattern singleton
		if (instanceViewMenu == null) {
			instanceViewMenu = new ViewMenu();
		}
		return instanceViewMenu;
	}
	
	/**
	 * Initialise la fenêtre et les éléments d'interface graphique du menu principal
	 */
	public void setViewMenu() {
		stageMenu = new Stage();
		paneMenu = new GridPane();
		
		label = new Label("Merci de vous connecter en suivant la page de votre fonction.");
		buttonWorker = new Button("Worker");
		buttonWorker.setOnAction(new ControllerMenu());
		buttonProcessEng = new Button("Process Engineer");
		buttonProcessEng.setOnAction(new ControllerMenu());
		
		paneMenu.add(label, 0, 0);
		GridPane.setHalignment(label, HPos.CENTER);
		GridPane.setValignment(label, VPos.CENTER);
		paneMenu.add(buttonWorker, 0, 1);
		GridPane.setHalignment(buttonWorker, HPos.CENTER);
		GridPane.setValignment(buttonWorker, VPos.CENTER);
		paneMenu.add(buttonProcessEng, 0, 2);
		GridPane.setHalignment(buttonProcessEng, HPos.CENTER);
		GridPane.setValignment(buttonProcessEng, VPos.CENTER);
		
		paneMenu.setAlignment(Pos.CENTER);

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
	
	
	/**
	Cette méthode permet de passer à la vue de connexion en fermant la fenêtre du menu.
	*/
	public void allerLogin() { 
		controllerLogin = new ControllerLogin();
		stageMenu.close();
		controllerLogin.montrerVue();
	}

}

