package model;

import java.beans.PropertyChangeSupport;
import java.io.File;

import controller.ConnectionBDD;
import data_types.Csv_input_data;
import data_types.DonneesSortieCapteurs;

public class Orowan {
	private String poste;
	private ConnectionBDD bddManager;
	private PropertyChangeSupport pcs;
	
	
	/*
	 * constructor of the Orowan class. It initializes the instance variables of the class 
	 * and creates a PropertyChangeSupport object to manage the listeners.
	 */
	public Orowan(String poste) {
		this.poste = poste;
		bddManager = ConnectionBDD.getInstance();
		pcs = new PropertyChangeSupport(this);
	}
	
	/*
	 * run Orowan.exe
	 */
	public void runOrowanExe() {
		File exeFile = new File("C:/Users/rapha/OneDrive/Ecole/01 - Cours/S8/04 - Ingénierie logicielle/03 - Projet/workspace/Fichiers/Model/Orowan_x64.exe");
		Csv_input_data donneeInOrowan = convertDonneeCapteurs("C:/Users/rapha/OneDrive/Ecole/01 - Cours/S8/04 - Ingénierie logicielle/03 - Projet/workspace/Fichiers/Model/1939351_F2.txt")
		File inputFile = new File("chemin/vers/mon/fichier.csv");
		
		// créer un process builder pour exécuter le fichier .exe avec l'entrée spécifiée
		ProcessBuilder pb = new ProcessBuilder(exeFile.getAbsolutePath(), inputFile.getAbsolutePath());
		
		// démarrer le processus
        Process process = pb.start();
        
        try {
            int exitCode = process.waitFor();
            System.out.println("Le processus s'est terminé avec le code de sortie " + exitCode);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	
	
	
	
	
	
	// Méthode pour sélectionner uniquement les grandeurs du fichier sortie capeteur qui vont
	// nous être utile dans la suite
	public Csv_input_data convertDonneeCapteurs(DonneesSortieCapteurs donneeOutCapteur) {
		Csv_input_data donneeInOrowan = new Csv_input_data(1, donneeOutCapteur.getEnThick(),
				donneeOutCapteur.getExThick(), donneeOutCapteur.getEnTens(), donneeOutCapteur.getExTens(),
				donneeOutCapteur.getDaiameter(), donneeOutCapteur.getYoungModulus(), donneeOutCapteur.getAverageSigma(),
				donneeOutCapteur.getMu(), donneeOutCapteur.getRollForce(), donneeOutCapteur.getfSlip(),
				donneeOutCapteur.getNomPoste());
		return donneeInOrowan;
	}
	
	
	// 
	public boolean insertData(DonneeAffichage donneeAffichage) { // J'ai attendu avant Si ça return true il faut r la
		// moyenne et reset la list (Faudra check pour les
		// doublons)
		if (donneeAffichage != null) {
				linkedListAffichage.addFirst(donneeAffichage);
					if (linkedListAffichage.size() >= 5) {
							// System.out.println(LocalTime.now());
							return true;
					}
		}
		return false;
	}

	public String getNomPoste() {
		// TODO Auto-generated method stub
		return poste;
	}
	
	
}
