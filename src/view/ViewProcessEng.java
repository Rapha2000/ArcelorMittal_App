package view;

import controller.ControllerMenu;
import controller.ControllerProcessEng;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewProcessEng {
	
	private static ViewProcessEng instanceViewProcessEng = null;
	
	private ControllerProcessEng controllerProcessEng;
	private ControllerMenu controllerMenu;
	
	private Stage stageProcessEng;
	private Scene sceneProcessEng;
	private BorderPane paneProcessEng;
	
	private Label labelUser;
	private Label labelRight;
	private TextField textFUser;
	private PasswordField passwordF;
	private ChoiceBox<String> choiceBox;
	private Button buttonUserAdd;
	private Button buttonUserRemove;
	private Button buttonUserUpdate;
	private HBox hBoxL;
	private VBox vBoxField;
	private HBox hBoxR;
	private ListView<String> listVUser;
	private ListView<String> listVRight;
	private VBox vBoxL;
	private VBox vBoxR;
	private HBox hBoxCenter;
	private Text infoText;
	
	private Button buttonLogout;
	
	private CheckBox checkBoxAdmin;

	
	
	
	private ViewProcessEng() {
		
	}
	
	public static ViewProcessEng getInstanceViewProcessEng() {
		if (instanceViewProcessEng == null) {
			instanceViewProcessEng = new ViewProcessEng();
		}
		return instanceViewProcessEng;
	}
	
	public void setViewProcessEng() {
		
		//Creation fenetre
		stageProcessEng = new Stage();
		paneProcessEng = new BorderPane();
		
		//Partie gauche (utilisateurs)
		labelUser = new Label("User");
		textFUser = new TextField();
		passwordF = new PasswordField();
		
		buttonUserAdd = new Button("Add");
		buttonUserAdd.setOnAction(new ControllerProcessEng());
		buttonUserRemove = new Button("Remove");
		buttonUserRemove.setOnAction(new ControllerProcessEng());
		buttonUserUpdate = new Button("Update");
		buttonUserUpdate.setOnAction(new ControllerProcessEng());
		
		vBoxField = new VBox();
		vBoxField.getChildren().addAll(textFUser, passwordF);
		hBoxL = new HBox();
		hBoxL.getChildren().addAll(labelUser, vBoxField, buttonUserAdd, buttonUserRemove, buttonUserUpdate);
		
		listVUser = new ListView<>();
		
		vBoxL = new VBox();
		vBoxL.getChildren().addAll(hBoxL, passwordF,listVUser);
		
		//Partie droite (droits)
		labelRight = new Label("Right");
		choiceBox = new ChoiceBox<>();
		choiceBox.getItems().add("Worker");
		choiceBox.getItems().add("Process Engineer");
		
		hBoxR = new HBox();
		hBoxR.getChildren().addAll(labelRight, choiceBox);
		
		listVRight = new ListView<>();
		
		vBoxR = new VBox();
		vBoxR.getChildren().addAll(hBoxR, listVRight);
		//paneProcessEng.setRight(vBoxR);
		
		//Assemblage
		hBoxCenter = new HBox();
		hBoxCenter.getChildren().addAll(vBoxL, vBoxR);
		
		//Affichage fenetre
		paneProcessEng.setCenter(hBoxCenter);
		
		//Bouton deconnexion
		buttonLogout = new Button("Logout");
		paneProcessEng.setRight(buttonLogout);
		
		//Message info
		infoText = new Text();
		paneProcessEng.setTop(infoText);
		
		sceneProcessEng = new Scene(paneProcessEng, 1000, 600);
		
		stageProcessEng.setTitle("Process Engineer");
		stageProcessEng.setScene(sceneProcessEng);
		stageProcessEng.show();
		
		
		/*buttonUserAdd.setOnAction(e -> {
			String user = textFUser.getText();
			listVUser.getItems().add(user);
		});
		
		buttonRightAdd.setOnAction(e -> {
			String right = textFRight.getText();
			listVRight.getItems().add(right);
		});*/
		
	}
	
	public void afficheMessage(String message) {
		String affiche = message;
		//Rajouter label dans la scene
	}
	
	public void closeStageProcessEng() {
		stageProcessEng.close();
	}
	
	public void moveToMenu() {
		controllerMenu = new ControllerMenu();
		stageProcessEng.close();
		controllerMenu.show();
	}
	
	public void setInfoText(String texte) {
		infoText.setText(texte);
	}
	
	public String getUserData() {
		return textFUser.getText();
	}
	
	public String getPasswordData() {
		return passwordF.getText();
	}
	
	public int getAdmin() {
		if(checkBoxAdmin.isSelected())
			return 1;
		else 
			return 0;
	}
}

//Revoir mise en page
