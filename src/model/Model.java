package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Model implements java.beans.PropertyChangeListener{
	private PropertyChangeSupport pcs;
	private ArrayList<Simulation> simulation;
	private static Model notreModele;
	private ArrayList<Orowan> listeOro;
	
	/**
	 * Constructeur de la classe Model qui instancie la simulation ainsi que le module Orowan.
	 *
	 * @param aucun paramètre n'est requis pour cette méthode.
	 */
	private Model() {
		pcs = new PropertyChangeSupport(this);
		simulation = new ArrayList<>();
	    simulation.add(new Simulation("1939351_F2.txt"));
		simulation.add(new Simulation("1939351_F3.txt"));
		listeOro = new ArrayList<>();
		listeOro.add(new Orowan("1939351_F2.txt"));
		listeOro.add(new Orowan("1939351_F3.txt"));
		listeOro.get(0).addPropertyChangeListener(this);
		listeOro.get(1).addPropertyChangeListener(this);
	}
	

	
	/**
	 * Démarre la simulation en lançant les threads associés aux objets de la classe Simulation et Orowan.
	 * Cette méthode appelle la méthode start() pour chacun des objets de la liste simulation et orowan,
	 * ce qui permet d'exécuter les tâches correspondantes dans des threads séparés et simultanément.
	 * La méthode affiche également un message de démarrage sur la sortie standard.
	 * 
	 *@throws IllegalStateException si la simulation n'a pas été correctement initialisée.
	 *
	 */

	public void launchSim() {
		simulation.get(0).start(); 
		simulation.get(1).start(); 
		listeOro.get(0).start();
		listeOro.get(1).start();
	}
	
	/**
	 * Renvoie une liste de noms de postes de travail associés à chaque objet Orowan de la liste orowan.
	 * Cette méthode parcourt la liste orowan pour récupérer le nom de poste de travail de chacun des 
	 * objets Orowan à l'aide de la méthode getNomPoste(), puis stocke ces noms dans une nouvelle liste 
	 * nomPostes.
	 *
	 * @return une liste de noms de postes de travail associés à chaque objet Orowan de la liste orowan.
	 * @throws IllegalStateException si la simulation n'a pas été correctement initialisée.
	 */

	public ArrayList<String> getNomsDePostes(){
		// Crée une nouvelle liste vide pour stocker les noms de postes.
		ArrayList<String> standList = new ArrayList<>();
		
		// Itère à travers la liste d'objets "Oro" pour extraire le nom de poste de chaque objet.
		for(int i = 0; i < listeOro.size(); i++) {
			standList.add(listeOro.get(i).getStand());
		}
		
		// Renvoie la liste de noms de postes extraits.
		return standList;
	}


	
	
	/**
	 * Cette méthode arrête la simulation et termine toutes les threads en cours d'exécution.
	 * Elle réinitialise les tables et commence à remplir la BDD. Ensuite, elle
	 * attend la fin de toutes les threads et les recrée.
	 * 
    */
	public void arreterSimulation() {
		// Interrompt les threads de simulation et Oro.
		simulation.get(0).setInterrompu();
		simulation.get(1).setInterrompu(); 
		listeOro.get(0).setInterrompu();
		listeOro.get(1).setInterrompu();
		
		try {
			// Attends que les threads de simulation et Oro se terminent.
			simulation.get(0).join();
			simulation.get(1).join();
			listeOro.get(0).join();
			listeOro.get(1).join();
			
			// Redémarre les threads.
			relancerThread();
		} catch (InterruptedException e) {
			// Gère l'exception si une interruption se produit.
			e.printStackTrace();
		}
	}


	/**
	 * Cette méthode recrée les threads pour la simulation et la méthode Orowan en initialisant des 
	 * nouvelles instances.
	 * Elle réinitialise également les PropertyChangeListeners pour les objets Orowan.
	 *
    */
	private void relancerThread() {
		// Crée de nouveaux threads de simulation et Oro avec des fichiers différents.
		simulation = new ArrayList<>();
		simulation.add(new Simulation("1939351_F2.txt"));
		simulation.add(new Simulation("1939351_F3.txt"));
		listeOro = new ArrayList<>();
		listeOro.add(new Orowan("1939351_F2.txt"));
		listeOro.add(new Orowan("1939351_F3.txt"));
		
		// Ajoute le listener de changement de propriété pour les objets Oro.
		listeOro.get(0).addPropertyChangeListener(this);
		listeOro.get(1).addPropertyChangeListener(this);
}

	/**
	 * Méthode statique qui permet de récupérer l'instance unique de la classe Model.
	 * Si l'instance n'a pas encore été créée, elle est créée lors de l'appel de cette méthode.
	 * Cette méthode suit le patron de conception Singleton, qui garantit qu'il n'existe qu'une 
	 * seule instance de la classe Model dans l'application.
	 *
	 * @return l'instance unique de la classe Model.
	 */

	public static Model getInstance() {
		if (notreModele == null) {
			notreModele = new Model();
		}
		return notreModele;
	}
	
	/**
	 * Ajoute un écouteur de changement de propriété à la liste des écouteurs de changement de propriété 
	 * de l'objet courant.
	 * 
	 * @param listener l'objet qui implémente l'interface PropertyChangeListener et doit être ajouté à la 
	 *                 liste des écouteurs de changement de propriété de l'objet courant.
	 * @throws NullPointerException si l'objet listener est nul.
	 */

	public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }
	
	/**
	 * Supprime un écouteur de changement de propriété de la liste des écouteurs de changement de 
	 * propriété de l'objet courant.
	 *
	 * @param listener l'objet qui implémente l'interface PropertyChangeListener et doit être supprimé de la liste des
	 *                 écouteurs de changement de propriété de l'objet courant.
	 * @throws NullPointerException si l'objet listener est nul.
	 */

	public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }
	
	/**
	 * Implémentation de la méthode propertyChange de l'interface PropertyChangeListener.
	 * Cette méthode est appelée lorsqu'un événement de changement de propriété est détecté.
	 * En fonction du nom de la propriété modifiée, cette méthode déclenche un événement de changement 
	 * de propriété approprié sur l'objet courant de la classe Model.
	 * Si la propriété modifiée est "newData", un événement de changement de propriété est déclenché 
	 * pour indiquer que de nouvelles données ont été calculées par le module Orowan.
	 * Si la propriété modifiée est "OrowanComputeTime", un événement de changement de propriété est 
	 * déclenché pour indiquer le temps de calcul du module Orowan pour le poste de travail correspondant.
	 *
	 * @param evt l'événement de changement de propriété émis par l'objet Orowan.
	 * @throws NullPointerException si l'objet evt est nul.
	 */

	public void propertyChange(PropertyChangeEvent evt) {
		// Récupère le nom de la propriété modifiée.
		String propName = evt.getPropertyName();
		
		// Réagit en fonction de la propriété modifiée.
		switch (propName) {
		case "changementDonnee":
			// Informe les listeners qu'une donnée a été modifiée.
			pcs.firePropertyChange("changementDonnee", null, null);
			break;
		case "tempsOrowan" :
			// Informe les listeners que le temps de simulation Oro a été modifié.
			pcs.firePropertyChange("tempsOrowan", evt.getOldValue(), evt.getNewValue());
			break;
		default:
			break;
		}
	}


	
}