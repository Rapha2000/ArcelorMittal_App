package controller;

import view.ViewLogin;
import donnees.Users;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.Event;

/**

Cette classe implémente l'interface EventHandler pour gérer les événements

liés à l'interface utilisateur de la page de connexion.

 */
public class ControllerLogin implements EventHandler<ActionEvent>{

	private ConnectionBDD connectionBDD ;
	private ViewLogin viewLogin;

/**

Constructeur de la classe ControllerLogin.
Il initialise les attributs dao et vue.
*/
public ControllerLogin() {
		this.connectionBDD = ConnectionBDD.getInstance();
		this.viewLogin = ViewLogin.getInstanceViewLogin();
	}
	
/**
Vérifie si le mot de passe respecte le pattern défini.
@param mdp Le mot de passe à vérifier.
@return true si le mot de passe respecte le pattern, false sinon.
*/

public static boolean verifyPassword(String mdp) {
		// Définir le pattern du mot de passe
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

		// Vérifier si le mot de passe respecte le pattern
		return mdp.matches(regex);
	}


/**

Cette méthode gère les événements lorsqu'un utilisateur clique sur un bouton dans la vue de connexion.
@param event L'événement associé au clic sur un bouton
 */
public void handle(ActionEvent event) {
	// Récupération du bouton sur lequel l'utilisateur a cliqué
	final Button butt = (Button) event.getSource();
	// Récupération du texte sur le bouton
	final String typeBouton = butt.getText();
	// Récupération du nom d'utilisateur et du mot de passe saisis par l'utilisateur
	String utilisateur = viewLogin.getUsername();
	String password = viewLogin.getPassword();
	switch (typeBouton) {
	case "VALIDATE":
		// Vérification si le nom d'utilisateur et le mot de passe respectent le pattern
		if (verifyPassword(password) && verifyPassword(utilisateur)) {
		// Vérification de l'existence de l'utilisateur et de son rôle dans la base de données
		Users user = connectionBDD.existenceEtRole(utilisateur, password);
		int login = user.getId();
		if (login != -1) {
			// Affichage d'un message indiquant que l'utilisateur existe dans la base de données
			System.out.println("L'utilisateur existe dans la BDD");
			// Affichage de l'identifiant de l'utilisateur
			System.out.println("login : " + login);
			// Redirection vers la vue correspondante selon le rôle de l'utilisateur
			switch (login) {
			case 1:
				viewLogin.allerVueProcessEng(user);
				System.out.println("Ucas 1");
				break;
			case 0:
				viewLogin.allerVueWorker(user);
				System.out.println("Ucas 2");
				break;
			default:
				break;
			}
			// Si l'utilisateur n'existe pas dans la base de données
		} else {
			// Affichage d'un message indiquant que l'utilisateur n'existe pas dans la base de données
			System.out.println("Utilisateur n'existe pas dans la BDD");
			// Affichage d'un message d'erreur dans la vue de connexion
			viewLogin.afficherMessageVue("Mot de passe ou nom d'utilisateur incorrects, veuillez contacter l'administrateur");
		}
		break;}
	default:
		break;
	}
}


/**
Affiche la page de connexion.
*/
	public void montrerVue() {
		viewLogin.setViewLogin();
	}

}