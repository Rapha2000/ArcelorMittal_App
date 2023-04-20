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

/**

Cette classe est un singleton représentant la fenêtre "Process Engineer" de l'application.

Elle permet de gérer les utilisateurs et leurs droits d'accès.
*/
public class ViewProcessEng {

    private static ViewProcessEng instanceViewProcessEng = null;

    private ControllerProcessEng controllerProcessEng;
    private ControllerMenu controllerMenu;

    private Stage stageProcessEng;
    private Scene sceneProcessEng;
    private BorderPane paneProcessEng;

    private Label labelUser;
    private TextField textFUser;
    private HBox hBoxUser;

    private Label labelPassword;
    private PasswordField passwordF;
    private HBox hBoxPassword;

    private Label labelRight;

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



	
	/**
	Constructeur privé pour empêcher l'instanciation directe de la classe.
	*/
    private ViewProcessEng() {

    }
    /**
	Méthode qui renvoie l'unique instance de la classe ViewProcessEng.
	Si l'instance n'existe pas, elle est créée.
	@return l'instance unique de la classe ViewProcessEng
	*/
    public static ViewProcessEng getInstanceViewProcessEng() { //récupère l'instance courante car on est dans un pattern singleton
        if (instanceViewProcessEng == null) {
            instanceViewProcessEng = new ViewProcessEng();
        }
        return instanceViewProcessEng;
    }
    
    /**
	Méthode qui crée la fenêtre "Process Engineer" et affiche ses composants.
	Elle permet de gérer les utilisateurs et leurs droits d'accès.
	*/
    public void setViewProcessEng() {

        //Creation fenetre
        stageProcessEng = new Stage();
        paneProcessEng = new BorderPane();

        //Partie gauche (utilisateurs)
        labelUser = new Label("    User    ");
        textFUser = new TextField();
        hBoxUser = new HBox();
        hBoxUser.getChildren().addAll(labelUser, textFUser);

        labelPassword = new Label("Password");
        passwordF = new PasswordField();
        hBoxPassword = new HBox();
        hBoxPassword.getChildren().addAll(labelPassword, passwordF);

        checkBoxAdmin = new CheckBox("Admin");

        buttonUserAdd = new Button("Add");
        buttonUserAdd.setOnAction(new ControllerProcessEng());
        buttonUserRemove = new Button("Remove");
        buttonUserRemove.setOnAction(new ControllerProcessEng());
        buttonUserUpdate = new Button("Update");
        buttonUserUpdate.setOnAction(new ControllerProcessEng());

        vBoxField = new VBox();
        vBoxField.getChildren().addAll(hBoxUser, hBoxPassword);

        labelRight = new Label("Right");
        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add("Worker");
        choiceBox.getItems().add("Process Engineer");

        hBoxL = new HBox();
        hBoxL.getChildren().addAll(vBoxField, buttonUserAdd, buttonUserRemove, labelRight, choiceBox, buttonUserUpdate);

        listVUser = new ListView<>();

        vBoxL = new VBox();
        vBoxL.getChildren().addAll(hBoxL,listVUser);

        //Affichage fenetre
        paneProcessEng.setCenter(vBoxL);

        //Bouton deconnexion
        buttonLogout = new Button("Logout");
        paneProcessEng.setRight(buttonLogout);
        buttonLogout.setOnAction(new ControllerProcessEng());

        //Message info
        infoText = new Text();
        paneProcessEng.setTop(infoText);

        sceneProcessEng = new Scene(paneProcessEng, 1000, 600);

        stageProcessEng.setTitle("Process Engineer");
        stageProcessEng.setScene(sceneProcessEng);
        stageProcessEng.show();

    }

    public void afficheMessage(String message) { //affiche un message à l'écran si un problème est rencontré
        String affiche = message;
        //Rajouter label dans la scene
    }

    public void closeStageProcessEng() { //permet de fermer la vue de l'admin
        stageProcessEng.close();
    }

    public void allerMenu() { //permet de se rendre sur la vue du menu et de fermer la vue de l'admin
        controllerMenu = new ControllerMenu();
        System.out.println("hey");
        stageProcessEng.close();
        controllerMenu.montrerVue();
    }
    
    
    /**
	Modifie le texte de la zone d'affichage des messages d'informations.
	@param texte le texte à afficher
	*/
    public void setInfoText(String texte) {
        infoText.setText(texte);
    }

    public String getTextUser() {
        return textFUser.getText();
    }

    public String getTextPassword() {
        return passwordF.getText();
    }
    
    /**
	Renvoie 1 si la case "Admin" est cochée, 0 sinon.
	@return 1 si la case "Admin" est cochée, 0 sinon
	*/
    public int getAdmin() { //permet de récupérer le droit d'un utilisateur
        if(checkBoxAdmin.isSelected())
            return 1;
        else
            return 0;
    }
}
