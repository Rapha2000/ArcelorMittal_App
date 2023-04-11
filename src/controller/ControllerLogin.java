package controller;

import view.ViewLogin;
import donnees.Users;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.Event;

public class ControllerLogin implements EventHandler<ActionEvent>{

	private ConnectionBDD dao ;
	private ViewLogin vue;
	
//constructeur sans action
	public ControllerLogin() {
		this.dao = ConnectionBDD.getInstance();
		this.vue = ViewLogin.getInstanceViewLogin();
	}



//verification que le password respecte le pattern
	public static boolean verifyPassword(String password) {
	    // Définir le pattern du mot de passe
	    String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	    // Vérifier si le mot de passe respecte le pattern
	    return password.matches(pattern);
	}
	
	
	
//gestion des évenements
	
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		final Button butt = (Button) event.getSource();
		final String typeBouton = butt.getText();
		String utilisateur = vue.getUsername();
		String password = vue.getPassword();
		switch (typeBouton) {
		case "VALIDATE":
			if (verifyPassword(password) && verifyPassword(utilisateur)) {
				System.out.println("valide");
				Users user = dao.existenceEtRole(utilisateur, password);
				int login = user.getId();
				if (login != -1) {
					System.out.println("User exists in the BDD");
					switch (login) {
					case 0: 
						vue.moveToProcessEng(user);
					case 1:
						vue.moveToWorker(user);
						break;
					default:
						break;
					}
					// connexion OK
				} else {
					System.out.println("User not founded");
					vue.setMessageIfProblem("Username and/or Password incorrect");
				}
			} else {
				System.out.println("Username and/or Password incorrect");
				vue.setMessageIfProblem("Username and/or Password incorrect");
				}
				break;
			default:
				break;
			}
		}

	public void show() { //retirer user
		//ControllerMenu.utilisateur = user;
		//System.out.println("moveToMenu : " + user);
		vue.setViewLogin();
	}


}
