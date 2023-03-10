package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import controller.DaoManagerH2;
import data_types.DonneesSortieCapteurs;

public class SensorDataHandler {
	
	private String pathSensorFile;
	private String SensorFileName;
	
	public SensorDataHandler(String fileName) {
		pathSensorFile = "C:/Users/rapha/OneDrive/Ecole/01 - Cours/S8/04 - Ingénierie logicielle/03 - Projet/workspace/Fichiers/Krakov/";
		this.SensorFileName = fileName;
	}
	
	// Méthode pour stocker les données du fichier capteur dans la bdd
	public void store() {
		File file = new File(pathSensorFile);
		
		// Scanner utiliser pour lire les données du fichier avec les données capteurs
		Scanner scan;
		
		try {
			scan = new Scanner(file);
			
			while (scan.hasNextLine()) {
			
			// lire une ligne du fichier csv et stocker les données dans un 
			// tableau 'csv_line'
            String line = scan.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(line, ";");
            String[] csv_line = new String[24];
            
            int i = 0;
            while (tokenizer.hasMoreTokens()) {
                csv_line[i++] = tokenizer.nextToken();
            }
            
            // toutes les données stockées sont des String, on les convertit en double
            double[] data = new double[csv_line.length];
            
            // On crée un objet data_types donneesCapteur pour y stocker les paramètres lus
            DonneesSortieCapteurs donneesCapteur = new DonneesSortieCapteurs(
            		(int) data[0], (int) data[1], data[2], data[3], data[4], data[5], 
            		data[6], data[7], data[8], data[9], data[10], data[11], data[12], 
            		data[13], data[14], data[15], data[16], data[17], data[18], data[19],
					data[20], data[21], data[22], data[23], SensorFileName);
            
            // On envoie les données dans la bdd H2 avec la méthode 'insertDonneOutCapteur
            // de la classe DaoManagerH2 du package controller (cf partie Anais)
            DaoManagerH2.insererDonneeSortieCapteur(donneesCapteur);
			
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
