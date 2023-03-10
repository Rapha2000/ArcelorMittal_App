package model;

import java.util.ArrayList;

public class Model {
	
	private ArrayList<SensorDataHandler> sensorDataHandler;
	private static Model model;

	// Constructeur
	private Model() {
		sensorDataHandler = new ArrayList<>();
		sensorDataHandler.add(new SensorDataHandler("1939351_F2.txt"));
		sensorDataHandler.add(new SensorDataHandler("1939351_F3.txt"));
	}
	
	// Méthode qui lance l'exécutable Orowan.exe
	public void runOrowan() {
		
	}

	public static Model getModel() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}

	
	// Pour lancer la simulation
	public void runSimulation() {
		System.out.println("starting simulation");
		sensorDataHandler.get(0).store(); // Remplissage de la bdd avec les données du 1er fichier
		sensorDataHandler.get(1).store(); // Remplissage de la bdd avec les données du 2e fichier
	}
	
	
}
