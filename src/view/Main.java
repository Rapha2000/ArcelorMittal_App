package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		
		Label labelBienvenue = new Label("BIENVENUE DANS OROWAN");
		Button buttonLogIn = new Button("Log In");
		/*
		 * buttonLogIn.setOnAction(e -> { primaryStage.close(); openLogInWindow(); });
		 */
		
		VBox vBox1 = new VBox();
		vBox1.getChildren().addAll(labelBienvenue, buttonLogIn);
		
		root.setCenter(vBox1);
		
		Scene scene = new Scene(root,600,600);
		
		primaryStage.setTitle("Orowan");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		ViewProcessEng processEng = ViewProcessEng.getInstanceViewProcessEng();
		processEng.setViewProcessEng();
		
		ViewMenu menu = ViewMenu.getInstanceViewMenu();
		menu.setViewMenu();
		
		ViewLogin login = ViewLogin.getInstanceViewLogin();
		login.setViewLogin();
		
		ViewWorker worker = ViewWorker.getInstanceViewWorker();
		worker.setViewWorker();
	}
	
	public void openLogInWindow() {
		Stage logInWindow = new Stage();
		
		Label labelUsername = new Label("Username");
		TextField textFUsername = new TextField();
		Label labelPassword = new Label("Password");
		TextField textFPassword = new TextField();
		Button buttonValidate = new Button("Validate");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.addRow(0, labelUsername);
		grid.addRow(1, textFUsername);
		grid.addRow(2, labelPassword);
		grid.addRow(3, textFPassword);
		grid.addRow(4, buttonValidate);		
		
		Scene scene = new Scene(grid,600,600);
		logInWindow.setTitle("Orowan");
		logInWindow.setScene(scene);
		logInWindow.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
