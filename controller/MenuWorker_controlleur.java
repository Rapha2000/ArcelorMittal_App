package Controller;

import donnees.Users;
import javafx.scene.control.Button;

public class MenuWorker_controlleur implements EventHandler<Event>{
	
	private VueMenu vue;
	private static Users utilisateur;

	public MenuWorker_controlleur() {
		// TODO Auto-generated constructor stub
		vue = VueMenu.getInstance();
	}
	
	/**
	 * Affiche le menu
	 */
	public void show(Users user) {
		MenuWorker_controlleur.utilisateur = user;
		System.out.println("moveToMenu : " + user);
		vue.setVue(user.getId());
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
		case "moveToConfig":
			vue.moveToConfig(utilisateur);
			//MOVE TO CONFIG
			break;
		case "moveToGraph":
			vue.moveToGraph(utilisateur);
			//MOVE TO GRAPH
			break;
		case "disconnect":
			vue.moveToLogin();
			break;
		default:
			break;
		}

	}

}
