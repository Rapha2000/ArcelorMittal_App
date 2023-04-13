package view;


import controller.ControllerLogin;
import controller.ControllerWorker;
import donnees.Users;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import controller.ControllerProcessEng;

public class ViewLogin {
	
	private static ViewLogin instanceViewLogin = null;
	
	private ControllerLogin controllerLogin;
	private ControllerWorker controllerWorker;
	private ControllerProcessEng controllerProcessEng;
	
	private Stage stageLogin;
	private Label labelUsername;
	private TextField textFUsername;
	private Label labelPassword;
	private TextField textFPassword;
	private Button buttonValidate;
	private GridPane grid;
	private Scene sceneLogin;
	private Text messageProblem;
	
	private ViewLogin() {
		
	}
	
	public static ViewLogin getInstanceViewLogin() {
		if (instanceViewLogin == null) {
			instanceViewLogin = new ViewLogin();
		}
		return instanceViewLogin;
	}
	
	public void setViewLogin() {
		stageLogin = new Stage();
		
		labelUsername = new Label("Username");
		textFUsername = new TextField();
		labelPassword = new Label("Password");
		textFPassword = new TextField();
		buttonValidate = new Button("VALIDATE");
		buttonValidate.setOnAction(new ControllerLogin());
		messageProblem = new Text();
		
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.addRow(0, labelUsername);
		grid.addRow(1, textFUsername);
		grid.addRow(2, labelPassword);
		grid.addRow(3, textFPassword);
		grid.addRow(4, buttonValidate);		
		grid.addRow(5, messageProblem);
		
		sceneLogin = new Scene(grid,600,600);
		stageLogin.setTitle("Orowan");
		stageLogin.setScene(sceneLogin);
		stageLogin.show();
	}
	
	public String getUsername() {
		String username = textFUsername.getText();
		return username;
	}
	
	public String getPassword() {
		String password = textFPassword.getText();
		return password;
	}
	
	public void moveToMenu() {//si worker
		stageLogin.close();
		//ControllerMenu.show();
	}
	
	public void moveToWorker(Users user) {
		ControllerWorker controllerWorker = new ControllerWorker();
		stageLogin.close();
		controllerWorker.show(user);
	}
	
	public void moveToProcessEng(Users user) {
		controllerProcessEng = new ControllerProcessEng();
		stageLogin.close();
		controllerProcessEng.show(user);
	}
	
	public void setMessageIfProblem(String message) {
		this.messageProblem.setText(message);
	}

}

