package view;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**

La classe Main est responsable du démarrage de l'application Orowan et de la création de la fenêtre principale avec le bouton de connexion.
*/
public class Main extends Application {

@Override
/**
* La méthode de démarrage est responsable de la création de la fenêtre principale de l'application avec le bouton de connexion.
*
* @param primaryStage la scène principale de l'application
*/
public void start(Stage primaryStage) {
GridPane root = new GridPane();

// Create label and button for login
Label labelBienvenue = new Label("BIENVENUE DANS OROWAN");
Button buttonLogin = new Button("Login");
buttonLogin.setOnAction(e -> {
primaryStage.close();
openLogInWindow();
});

/*VBox vBox1 = new VBox();
vBox1.getChildren().addAll(labelBienvenue, buttonLogIn);*/
// Add label and button to the grid pane
root.setAlignment(Pos.CENTER);
root.add(labelBienvenue, 0,0);
root.add(buttonLogin, 0, 1);
GridPane.setHalignment(labelBienvenue, HPos.CENTER);
GridPane.setValignment(labelBienvenue, VPos.CENTER);
GridPane.setHalignment(buttonLogin, HPos.CENTER);
GridPane.setValignment(buttonLogin, VPos.CENTER);

// Create and set the scene
Scene scene = new Scene(root,600,600);

primaryStage.setTitle("Orowan");
primaryStage.setScene(scene);
primaryStage.show();

/*ViewLogin login = ViewLogin.getInstanceViewLogin();
login.setViewLogin();

ViewWorker worker = ViewWorker.getInstanceViewWorker();
worker.setViewWorker();

ViewProcessEng processEng = ViewProcessEng.getInstanceViewProcessEng();
processEng.setViewProcessEng();

ViewMenu menu = ViewMenu.getInstanceViewMenu();
menu.setViewMenu();*/

}
/**
* La méthode openLogInWindow est responsable de l'ouverture de la fenêtre de connexion lorsqu'on clique sur le bouton de connexion
*/
public void openLogInWindow() {
    ViewMenu menu = ViewMenu.getInstanceViewMenu();
    menu.setViewMenu();
}

/**
* Le main est responsable du lancement de l'application.
*
* @param args les arguments de la ligne de commande
*/
public static void main(String[] args) {
launch(args);
}
}


