package controller;



import javafx.event.Event;
import javafx.event.EventHandler;

import model.Model;
import view.ViewWorker;

import java.beans.PropertyChangeEvent;

//affichage des courbes

import donnees.Users;


/**

Cette classe est le contrôleur de la vue de l'affichage des courbes.
Elle implémente l'interface java.beans.PropertyChangeListener pour détecter les changements dans le modèle et
l'interface javafx.event.EventHandler pour gérer les événements des boutons dans la vue.
*/


public class ControllerWorker implements java.beans.PropertyChangeListener, EventHandler<Event>{
	
	private Model model;
	private ConnectionBDD connectionBDD;
	private ViewWorker viewWorker;
	private String nomBouton; // = "Friction";  // ATTENTION NORMALEMENT NAME PREND LA VALEUR DU BOUTON CLIQUé
	public static Users utilisateur;
	

	

/**
 * Constructeur par défaut du contrôleur.
 * Il crée une instance du DAO, du modèle et de la vue, puis ajoute le contrôleur en tant qu'écouteur des changements de propriété du modèle.
 */
public ControllerWorker() {
	connectionBDD = ConnectionBDD.getInstance();
	model = model.getInstance();
	model.addPropertyChangeListener(this);
	viewWorker = ViewWorker.getInstanceViewWorker();
}

/**
 * Constructeur avec un nom de bouton en paramètre.
 * Il crée une instance du DAO, du modèle et de la vue.
 * @param name : nom du bouton cliqué
 */
public ControllerWorker(String name) {
	this.nomBouton = name;
	connectionBDD = ConnectionBDD.getInstance();
	model = model.getInstance();
	viewWorker = ViewWorker.getInstanceViewWorker();
}

/**
 * Affiche la page graphique.
 * @param user : utilisateur
 */
public void montrerVue(Users user) {
	ControllerWorker.utilisateur = user;
	viewWorker.setViewWorker();
	model.launchSim();;
}

/**
 * Méthode appelée lorsqu'un changement de propriété est détecté dans le modèle.
 * Elle met à jour la vue en fonction de la propriété qui a changé.
 * @param event : événement de changement de propriété
 */
public void propertyChange(PropertyChangeEvent event) {
	// On récupère le nom de la propriété qui a changé
	String nomEvent = event.getPropertyName();
	
	// On traite le changement de propriété en fonction de son nom
	switch (nomEvent) {
		// Si la propriété est "newData", cela signifie qu'il y a de nouvelles données à afficher
		case "changementDonnee":
			// On récupère la valeur de la propriété "standID" dans la vue, qui correspond à l'ID du poste
			String prop;
			try {
				prop = viewWorker.getComboBoxStandID().getValue();
			}catch (Exception e) {
				prop = "";
			}
			// On importe les nouvelles données à afficher en utilisant l'ID du poste pour récupérer les données appropriées depuis la DAO
			viewWorker.obtenirPointsCourbes(connectionBDD.recupererDonneesAffichage(prop));
			break;
		
		// Si la propriété est "OrowanComputeTime", cela signifie que le temps de calcul pour l'algorithme Orowan a été mis à jour
		case "tempsOrowan" :
			// On récupère l'ancienne valeur de la propriété, qui correspond au nom de l'équipement de production concerné
			String nomPoste = (String) event.getOldValue();
			// On récupère la nouvelle valeur de la propriété, qui correspond au temps de calcul mis à jour
			long tempsOrowan = (long) event.getNewValue();
			// On récupère la valeur de la propriété "standID" dans la vue, qui correspond à l'ID de l'équipement de production
			try {
				prop = viewWorker.getComboBoxStandID().getValue();
			}catch (Exception e) {
				prop = "";
			}
			// Si l'ID du poste concerné correspond à l'ID du poste actuellement affiché dans la vue
			if(nomPoste.equals(prop)) {
				// On met à jour la valeur affichée pour le temps de calcul de l'algorithme Orowan dans la vue
				viewWorker.setTempsExOrowan(tempsOrowan);
			}
			break;
		
		// Si la propriété ne correspond à aucune des propriétés attendues, on ne fait rien
		default:
			break;
	}
}
/**
 * Méthode appelée lorsqu'un événement de bouton est détecté dans la vue.
 * Elle gère les événements de tous les boutons dans la vue en fonction du nom du bouton cliqué.
 * @param event : événement de bouton
 */
public void handle(Event event) {
	switch (nomBouton) { // selon le nom du bouton sur lequel on a appuyé
	case "nomPoste": // Si le cas est "refresh", on ajoute un poste
		viewWorker.ajouterPoste(model.getNomsDePostes());
		break;
	case "Roll Speed" : // Si le cas est "Roll Speed", on définit le flag Roll Speed
		viewWorker.setBooleanRollSpeed(viewWorker.isSelectedRollSpeed());
		break;
	case "Friction" : // Si le cas est "Friction", on définit le flag Friction
		viewWorker.setBooleanCoefFriction(viewWorker.isSelectededFriction());
		break;
	case "Sigma" : // Si le cas est "Sigma", on définit le flag Sigma
		viewWorker.setBooleanSigma(viewWorker.isSelectedSigma());
		break;
	case "Logout" : // Si le cas est "Logout", on arrête la simulation et on retourne au menu
	//	stopSim();
		viewWorker.allerMenu();
		break;
	}
}
	
	/**
	 * arrête la simulation
	 */
	public void arreterSimulation() {
		// TODO Auto-generated method stub
		model.arreterSimulation();
	}



	
}
