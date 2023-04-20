package controller;

import java.util.ArrayList;

import view.ViewProcessEng;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import donnees.Users;

/**

classe du contrôleur pour la vue ProcessEng, qui gère les événements d'interaction avec les utilisateurs.
Il permet de récupérer les informations de la vue, d'effectuer des vérifications de validité,
et de déclencher des actions en fonction des choix de l'utilisateur, telles que l'ajout, la mise à jour ou la suppression d'un utilisateur.
*/
public class ControllerProcessEng implements EventHandler<ActionEvent> {

	
	private String nomUtilisateur;
	private int id;
	private static Users utilisateur;
	private ConnectionBDD connectionBDD;
	private ViewProcessEng viewProcessEng;

	/**
	 * Constructeur 
	 */
	public ControllerProcessEng() {
		this.viewProcessEng = ViewProcessEng.getInstanceViewProcessEng();
		connectionBDD = ConnectionBDD.getInstance();
	}

	/**
	 * Affichage des utilisateurs sur la vue.
	 * @param utilisateur l'utilisateur courant.
	 */
	public void montrerVue(Users utilisateur) {
		ControllerProcessEng.utilisateur = utilisateur;
		viewProcessEng.setViewProcessEng();
	}

	/**
	 * Vérification que le mot de passe respecte le pattern.
	 * @param mdp le mot de passe à vérifier.
	 * @return true si le mot de passe respecte le pattern, false sinon.
	 */
	public static boolean verifyPassword(String mdp) {
	    // Définir le pattern du mot de passe
	    String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	    
	    // Vérifier si le mot de passe respecte le pattern
	    return mdp.matches(regex);
	}
		
	/**
	 * Gestion des boutons.
	 * @param event l'événement déclenché par l'utilisateur.
	 */
	@Override
	public void handle(ActionEvent event) {
		// Récupération du bouton cliqué
		final Button butt = (Button) event.getSource();
		// Récupération du texte du bouton cliqué
		final String typeBouton = butt.getText();
		
		// Switch pour déterminer l'action à effectuer en fonction du bouton cliqué
		switch (typeBouton) {
			// Si le bouton cliqué est "Logout"
			case "Logout":
				// Déplacement de la vue vers le menu principal
				viewProcessEng.allerMenu();
				break;
			// Si le bouton cliqué est "Add"
			case "Add":
				// Vérification que le mot de passe et l'utilisateur respectent les contraintes
				if (!(verifyPassword(viewProcessEng.getTextPassword()) && verifyPassword(viewProcessEng.getTextUser()))) {
					// Affichage d'un message d'erreur
					viewProcessEng.setInfoText("le mot de passe que vous avez saisi ne respecte pas les contraintes");
				} else {
					// Vérification que les deux champs de mot de passe sont identiques
					if (viewProcessEng.getTextPassword().equals(viewProcessEng.getTextPassword())) {
						// Vérification que l'utilisateur n'existe pas déjà
						if (!connectionBDD.utilisateurExiste(viewProcessEng.getTextUser())) {
							// Création d'un nouvel utilisateur
							connectionBDD.creerNouvelUtilisateur(viewProcessEng.getTextUser(), viewProcessEng.getTextPassword(), viewProcessEng.getAdmin());
							// Fermeture de la fenêtre de création d'utilisateur
							viewProcessEng.closeStageProcessEng();
							// Affichage de la vue de gestion d'utilisateur
							montrerVue(utilisateur);
						} else {
							// Affichage d'un message d'erreur si l'utilisateur existe déjà
							viewProcessEng.setInfoText("L'utilisateur existe déjà ");
						}
					} else {
						// Affichage d'un message d'erreur si les deux champs de mot de passe ne sont pas identiques
						viewProcessEng.setInfoText("Les mots de passe fournis ne correspondent pas, veuillez réessayer");
					}
				}
				break;
			// Si le bouton cliqué est "Update"
			case "Update":
				// Récupération de l'utilisateur et du droit d'administration
				nomUtilisateur= viewProcessEng.getTextUser();
				id =  viewProcessEng.getAdmin();
				// Vérification que l'utilisateur existe
				if (connectionBDD.utilisateurExiste(nomUtilisateur) ){
					int newId;
					// Modification du droit d'administration
					if (id == 0)
						newId = 1;
					else
						newId= 0;
					connectionBDD.miseAJourDroits(nomUtilisateur, newId);
					// Fermeture de la fenêtre 
					viewProcessEng.closeStageProcessEng();
					// Affichage de la vue de gestion d'utilisateur
					montrerVue(utilisateur);
				}
				break;
			// Si le bouton cliqué est "Remove"
			case "Remove":
				// Récupération de l'utilisateur et du droit d'administration
				nomUtilisateur= viewProcessEng.getTextUser();
				id =  viewProcessEng.getAdmin();
				// Vérification que l'utilisateur existe
				if (connectionBDD.utilisateurExiste(nomUtilisateur)) {
					// Suppression de l'utilisateur
					connectionBDD.supprimerUtilisateur(nomUtilisateur);
					// Fermeture de la fenêtre 
					viewProcessEng.closeStageProcessEng();
				
	
}}}}
