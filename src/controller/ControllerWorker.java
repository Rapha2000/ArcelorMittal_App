package controller;


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model2.Model2;
import view.ViewWorker;

import java.beans.PropertyChangeEvent;

//affichage des courbes

import donnees.Users;

public class ControllerWorker implements EventHandler<Event> , java.beans.PropertyChangeListener {
	
	private Model2 model;
	private ConnectionBDD dao;
	private ViewWorker vue;
	private String name;
	public static Users user;
	

	
	public ControllerWorker() {
		// TODO Auto-generated constructor stub
		dao =ConnectionBDD.getInstance();
		model = model.getModel();
		model.addPropertyChangeListener(this);
		vue = ViewWorker.getInstanceViewWorker();
	}
	
	
	public ControllerWorker(String name) { // We create one instance of controler per button
		System.out.println("in second controler constructor");
		this.name = name;
		dao =ConnectionBDD.getInstance();
		model = model.getModel();
		vue = ViewWorker.getInstanceViewWorker();
	}
	
	
	
	/**
	 * Affiche la page graphique
	 */
	public void show(Users user) {
		System.out.println("GraphCont : " + user);
		ControllerWorker.user = user;
		vue.setViewWorker();
		model.startSimulation();
	}
	
	/**
	 * Changement dans model
	 */
	public void propertyChange(PropertyChangeEvent event) {
		// TODO Auto-generated method stub
		String nom_propriete = event.getPropertyName();
		switch (nom_propriete) {
		case "newData": //nouvelle données a afficher
			String value;
			try {
				value = vue.getStandID().getValue();
			}catch (Exception e) {
				// TODO: handle exception
				value = "";
			}
			vue.importPointsForData(dao.recupererDonneesAffichage(value));
			//System.out.println("new data");
			break;
		case "OrowanComputeTime" : //compute time orowan
			String nomPoste = (String) event.getOldValue();
			long computeTime = (long) event.getNewValue();
			
			try {
				value = vue.getStandID().getValue();
			}catch (Exception e) {
				// TODO: handle exception
				value = "";
			}
			
			if(nomPoste.equals(value)) {
				vue.setComputeTimeValue(computeTime);
			}
		//afficher friction coef
			break;
		default:
			break;
		}
	}
	
	/**
	 * Gère les boutons de la vue
	 */
	@Override
	/*public void handle(Event event) {
		// TODO Auto-generated method stub
		final Button butt = (Button) event.getSource();
		final String typeBouton = butt.getText();
		switch (typeBouton) {
		case "refresh":
			vue.addAPoste(model.getPostesName());
			break;
		case "Roll Speed" :
			vue.setFlagRollSpeed(vue.isCheckedRollSpeed());
			break;
		case "Friction" :
			vue.setFlagFriction(vue.isCheckedFriction());
			break;
		case "Sigma" :
			vue.setFlagSigma(vue.isCheckedSigma());
			break;
		case "Logout" :
			stopSim();
			vue.moveToMenu();
			break;
		}
	}*/
	
	
	
	public void handle(Event event) {
		// TODO Auto-generated method stub
		System.out.println(name);
		switch (name) {
		case "refresh":
			vue.addAPoste(model.getPostesName());
			break;
		case "Roll Speed" :
			vue.setFlagRollSpeed(vue.isCheckedRollSpeed());
			break;
		case "Friction" :
			vue.setFlagFriction(vue.isCheckedFriction());
			break;
		case "Sigma" :
			vue.setFlagSigma(vue.isCheckedSigma());
			break;
		case "Logout" :
			stopSim();
			vue.moveToMenu();
			break;
		}
	}
	
	/**
	 * Arrete les thread de la simulation
	 */
	public void stopSim() {
		// TODO Auto-generated method stub
		model.stopSim();
	}
	
}
