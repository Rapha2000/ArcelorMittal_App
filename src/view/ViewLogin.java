package view;


import controller.ControllerLogin;
import controller.ControllerWorker;
import donnees.Users;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import controller.ControllerProcessEng;


/**

La classe ViewLogin est une classe qui gère l'interface utilisateur de la page de connexion.
Elle permet à l'utilisateur de rentrer son nom d'utilisateur et son mot de passe pour accéder au programme.
Elle possède des méthodes pour récupérer les informations entrées par l'utilisateur ainsi que pour afficher des messages d'erreur en cas de problème de connexion.
*/
public class ViewLogin {

    private static ViewLogin instanceViewLogin = null;

    private ControllerLogin controllerLogin;
    private ControllerWorker controllerWorker;
    private ControllerProcessEng controllerProcessEng;

    private Stage stageLogin;
    private Label labelUsername;
    private TextField textFUsername;
    private Label labelPassword;
    private PasswordField passwordF;
    private Button buttonValidate;
    private GridPane grid;
    private Scene sceneLogin;
    private Text messageProblem;
    
    
    /**
     * Constructeur privé pour empêcher la création d'instances multiples de la classe ViewLogin.
     */
    private ViewLogin() {

    }

    public static ViewLogin getInstanceViewLogin() { //récupère l'instance courante car on est dans un pattern singleton
        if (instanceViewLogin == null) {
            instanceViewLogin = new ViewLogin();
        }
        return instanceViewLogin;
    }

    /**
     * Initialise et affiche l'écran de connexion.
     */
    public void setViewLogin() {
        stageLogin = new Stage();

        labelUsername = new Label("Username");
        textFUsername = new TextField();
        labelPassword = new Label("Password");
        passwordF = new PasswordField();
        buttonValidate = new Button("VALIDATE");
        buttonValidate.setOnAction(new ControllerLogin());
        messageProblem = new Text();

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.addRow(0, labelUsername);
        grid.addRow(1, textFUsername);
        grid.addRow(2, labelPassword);
        grid.addRow(3, passwordF);
        grid.addRow(4, buttonValidate);
        GridPane.setHalignment(buttonValidate, HPos.CENTER);
        GridPane.setValignment(buttonValidate, VPos.CENTER);
        grid.addRow(5, messageProblem);

        sceneLogin = new Scene(grid,600,600);
        stageLogin.setTitle("Orowan");
        stageLogin.setScene(sceneLogin);
        stageLogin.show();
    }

 
    public void allerMenu() {//permet de fermer la vue login et de se rendre sur la vue du menu
        stageLogin.close();
        //ControllerMenu.show();
    }

    public void allerVueWorker(Users user) { //permet de se rendre sur la vue d'affichage des courbes et de fermer le login
        ControllerWorker controllerWorker = new ControllerWorker();
        stageLogin.close();
        controllerWorker.montrerVue(user);
    }

    public void allerVueProcessEng(Users user) {//permet de se rendre sur la vue de l'admin et de fermer le login
        controllerProcessEng = new ControllerProcessEng();
        stageLogin.close();
        controllerProcessEng.montrerVue(user);
    }

    public void afficherMessageVue(String message) { //affiche un message à l'écran si un problème est rencontré
        this.messageProblem.setText(message);
    }
    public String getUsername() { //permet de récupérer le nom d'utilisateur entré 
        String username = textFUsername.getText();
        return username;
    }

    public String getPassword() { //permet de récupérer le mot de passe tapé
        String password = passwordF.getText();
        return password;
    }


}
