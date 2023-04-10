package Controller;

import Vue.ViewMenu;
import donnees.Users;
import javafx.scene.control.Button;

public class Menu_controlleur implements EventHandler<Event>{
	
	private ViewMenu vue;
	private static Users utilisateur;

	public Menu_controlleur() {
		// TODO Auto-generated constructor stub
		vue = ViewMenu.getInstanceViewMenu();
	}
	
	/**
	 * Affiche le menu
	 */
	public void show(Users user) { //retirer user
		Menu_controlleur.utilisateur = user;
		System.out.println("moveToMenu : " + user);
		vue.setViewMenu();
	}
	
	/**
	 * gère les clics
	 */
	@Override
	public void handle(Event event) {
		// TODO Auto-generated method stub
		final Button butt = (Button) event.getSource();
		final String typeBouton = butt.getText();
		System.out.println(typeBouton);
		switch (typeBouton) {
		case "Worker":
			vue.moveToLogin();;
			//MOVE TO CONFIG
			break;
		
		
		case "Process Engineer":
			vue.moveToLogin();
			break;
		default:
			break;
		}

	}

}
