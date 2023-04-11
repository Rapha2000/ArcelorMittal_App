package controller;

import view.ViewMenu;
import donnees.Users;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ControllerMenu implements EventHandler<ActionEvent> {
	
	private ViewMenu vue;
	private static Users utilisateur;

	public ControllerMenu() {
		// TODO Auto-generated constructor stub
		vue = ViewMenu.getInstanceViewMenu();
	}
	
	/**
	 * Affiche le menu
	 */
	public void show() { //retirer user
		//ControllerMenu.utilisateur = user;
		//System.out.println("moveToMenu : " + user);
		vue.setViewMenu();
	}
	
	/**
	 * gère les clics
	 */
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		final Button butt = (Button) event.getSource();
		final String typeBouton = butt.getText();
		System.out.println(typeBouton);
		switch (typeBouton) {
		case "Worker":
			vue.moveToLogin();
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
