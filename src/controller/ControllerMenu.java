package controller;

import view.ViewMenu;

import donnees.Users;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**

La classe ControllerMenu gère les interactions de l'utilisateur avec le menu.
Elle implémente l'interface EventHandler<ActionEvent> pour gérer les événements liés aux clics de boutons.
*/


public class ControllerMenu implements EventHandler<ActionEvent> {
	private ViewMenu viewMenu;

	/**
	 * Constructeur de la classe ControllerMenu.
	 * Initialise la vue du menu.
	 */
	public ControllerMenu() {
		viewMenu = ViewMenu.getInstanceViewMenu();
	}

	/**
	 * Affiche la vue du menu.
	 */
	public void montrerVue() { 
		viewMenu.setViewMenu();
	}

	/**
	 * Gère les clics de l'utilisateur sur les boutons du menu.
	 * 
	 * @param event L'événement généré par le clic sur le bouton.
	 */
	@Override
	public void handle(ActionEvent event) {
		// Obtention du bouton déclencheur de l'événement
		final Button butt = (Button) event.getSource();
		// Obtention du texte affiché sur le bouton
		final String typeBouton = butt.getText();
		// Affichage du texte du bouton cliqué
		System.out.println(typeBouton);
		// Gestion du clic en fonction du texte du bouton cliqué
		switch (typeBouton) {
		case "Worker":
		// Déplacement vers la vue de login
		viewMenu.allerLogin();
		break;
		case "Process Engineer":
			 	// Déplacement vers la vue de login
			 	viewMenu.allerLogin();
		break;
		default:
		break;
	}
	}}
