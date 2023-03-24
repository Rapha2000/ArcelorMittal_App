package model;

import java.sql.SQLException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.h2.store.Data;

import java.sql.ResultSet;

import controller.ConnectionBDD;
import data_types.DonneesSortieCapteurs;

public class Model {

	private String cheminFichierSortieCapteurs;
	private String nomFichierSortieCapteurs;
	private Controller controller;
	private ResultSet data;
	
	
	/*
	 * Constructeur
	 */
	private Model(String nomFichier, Controller controller) {
		cheminFichierSortieCapteurs = "C:/Users/rapha/OneDrive/Ecole/01 - Cours/S8/04 - Ingénierie logicielle/03 - Projet/workspace/Fichiers/Krakov/";
		this.nomFichierSortieCapteurs = nomFichier;
		this.controller = controller;
	}
	
	
	
	
	
	/*
	 * Cette méthode a pour but de lire les valeurs contenues dans les fichiers de sortie des capteurs et de les stocker dans 
	 * la BDD en utilisant la méthode insererDonneeSortieCapteur(DonneesSortieCapteurs outputDataCapteur) du controller
	 * 
	 */
	public void read_csv() {
		File file = new File(cheminFichierSortieCapteurs);
		
		// Scanner utiliser pour lire les données du fichier avec les données capteurs
				Scanner scan;
				
				try {
					scan = new Scanner(file);
					
					while (scan.hasNextLine()) {
					
						// lire une ligne du fichier csv et stocker les données dans un tableau 'csv_line'
			            String line = scan.nextLine();
			            StringTokenizer tokenizer = new StringTokenizer(line, ";");
			            String[] csv_line = new String[24];
			            
			            int i = 0;
			            while (tokenizer.hasMoreTokens()) {
			                csv_line[i++] = tokenizer.nextToken();
			            }
			            
			            // toutes les données stockées sont des String, on les convertit en double
			            double[] data = new double[csv_line.length];
			            
			            // la méthode insererDonneeSortieCapteur du controller prend en entrée un argument de type 
			            // 'DonneesSortieCapteurs' (cf data_types)
			            DonneesSortieCapteurs donneesCapteur = new DonneesSortieCapteurs(
			            		(int) data[0], (int) data[1], data[2], data[3], data[4], data[5], 
			            		data[6], data[7], data[8], data[9], data[10], data[11], data[12], 
			            		data[13], data[14], data[15], data[16], data[17], data[18], data[19],
								data[20], data[21], data[22], data[23], nomFichierSortieCapteurs);
			            
			            // Les données sont maintenant au bon format, on peut donc les stocker dans la bdd H2
			            ConnectionBDD.insererDonneeSortieCapteur(donneesCapteur);
					}
	
				} catch(FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
	}
	
	
	/*
	 * Cette méthode extrait les données utiles à Orowan depuis les données de sorties de capteurs que l'on a inséré
	 * précédemment dans la bdd. Elle effectue cette extraction toutes les 200 ms en utilisant un thread et la commande 
	 * wait. 
	 * 
	 */
	public List<Data> extract_data() {
		List<Data> dataList = new ArrayList<>(); // Toutes les 200 ms, on va stocker les données récupérées dans la bdd dans cette liste
		
		Thread thread = new Thread(new Runnable() {
			
			public void run() {
				while(true) {
					try {
                        Data data = controller.extractDataInputOrowan(); // on extrait uniquement les valeurs utiles à Orowan
                        dataList.add(data);
                        Thread.sleep(200);
                    } catch (SQLException | InterruptedException e) {
                        e.printStackTrace();
                    }
				}
			}
		});
		thread.start();
		
		return dataList;
	}
	
	
	
	/*
	 * 
	 * cette méthode exécute "Orowan" avec les données que l'on a récupéré avec la méthode extract_data,
	 * et stocke les valeurs de sortie d'Orowan dans une liste.
	 * 
	 * Cette méthode retourne une liste où chaque élément correspond au fichier de sortie d'Orowan pour une ligne correspondante
	 * 
	 */
	public List<String> execute_orowan(List<Data> dataList) {
		File exeFile = new File("C:/Users/rapha/OneDrive/Ecole/01 - Cours/S8/04 - Ingénierie logicielle/03 - Projet/workspace/Fichiers/Model/Orowan_x64.exe");
		
	    List<String> outputOrowanList = new ArrayList<>(); // Nouvelle liste pour stocker les sorties de Orowan
	    
	    for (Data data : dataList) {
	        // exécution de Orowan
	        try {
	            // création d'un processus pour exécuter Orowan
	            ProcessBuilder processBuilder = new ProcessBuilder("Orowan", data.toString());
	            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT); // redirection de la sortie vers le flux standard
	            Process process = processBuilder.start();
	            int exitCode = process.waitFor(); // attente de la fin de l'exécution de Orowan
	            
	            if (exitCode == 0) {
	                // lecture du fichier de sortie de Orowan
	                String outputFilePath = "output_" + data.getId() + ".csv"; // nom de fichier généré par Orowan
	                List<String> outputLines = Files.readAllLines(Paths.get(outputFilePath));
	                String output = String.join(",", outputLines);
	                outputOrowanList.add(output);
	                
	                // stockage dans la base de données
	                controller.storeOrowanOutput(data.getId(), output);
	            } else {
	                System.err.println("Erreur lors de l'exécution de Orowan pour la donnée " + data.getId());
	            }
	        } catch (IOException | InterruptedException | SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return outputOrowanList;
	}
	
	
	/*
	 * prend 5 valeurs et calcule la moyenne des coefficient de frictions et les stocke dans une liste 
	 * 
	 */
	
	public List<Double> calculeMoyennesCoeffFriction(List<String> outputOrowanList){
		
		List<Double> averages = new ArrayList<>(); // liste pour stocker les moyennes de coefficients de friction
	    List<Double> currentValues = new ArrayList<>(); // liste pour stocker les coefficients de friction courants
	    
	    for (int i = 0; i < outputOrowanList.size(); i++) {
	        String csv_line = outputOrowanList.get(i);
	        
	        // Récupération de la valeur du coefficient de friction dans la troisième colonne
	        String[] valeurs_sortie_orowan = csv_line.split(",");
	        double frictionCoefficient = Double.parseDouble(valeurs_sortie_orowan[2]);
	        currentValues.add(frictionCoefficient);
	        
	        // Calcul de la moyenne tous les 5 éléments
	        if ((i + 1) % 5 == 0) {
	            double sum = 0.0;
	            for (Double value : currentValues) {
	                sum += value;
	            }
	            double average = sum / currentValues.size();
	            averages.add(average);
	            currentValues.clear();
	        }
	    }
	    
	    return averages;  // c'est dans cette liste que sont stocké les moyennes des coefficients de frictions toutes les 5 valeurs
		
	}

	
	
	
}
