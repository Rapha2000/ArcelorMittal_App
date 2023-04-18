package Controller;

import java.beans.PropertyChangeEvent;

import Model.Model;
import Vue.ViewWorker;
import javafx.scene.control.Button;

//affichage des courbes

import donnees.Users;

public class View_Worker_controlleur1 implements EventHandler<Event> , java.beans.PropertyChangeListener {
	
	private Model model;
	private ConnectionBDD dao;
	private ViewWorker vue;
	public static Users user;
	
	public View_Worker_controlleur1(String fileName, String nomPoste) {
		// TODO Auto-generated constructor stub
		dao =ConnectionBDD.getInstance();
		model = Model.getModel(fileName, nomPoste);
		model.addPropertyChangeListener(this);
		vue = ViewWorker.getInstanceViewWorker();
	}
	
	
	/**
	 * Affiche la page graphique
	 */
	public void show(Users user) {
		System.out.println("GraphCont : " + user);
		View_Worker_controlleur1.user = user;
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
			
			break;
		default:
			break;
		}
	}
	
	/**
	 * Gère les boutons de la vue
	 */
	@Override
	public void handle(Event event) {
		// TODO Auto-generated method stub
		final Button butt = (Button) event.getSource();
		final String typeBouton = butt.getText();
		switch (typeBouton) {
		case "refresh":
			vue.addAPoste(model.getPostesName());
			break;
		case "rollSpeed" :
			vue.setFlagRollSpeed(vue.isCheckedRollSpeed());
			break;
		case "friction" :
			vue.setFlagFriction(vue.isCheckedFriction());
			break;
		case "sigma" :
			vue.setFlagSigma(vue.isCheckedSigma());
			break;
		case "back" :
			stopSim();
			vue.moveToMenu(user);
			break;
		case "disconnect" :
			stopSim();
			vue.moveToLogin();
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