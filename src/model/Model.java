package model;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ModuleLayer.Controller;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.FileWriter;
import java.io.IOException;

//import org.h2.store.Data;

import java.sql.ResultSet;

import controller.ConnectionBDD;
import controller.ControllerWorker;
import donnees.Csv_input_data;
import donnees.Csv_output_data;
import donnees.DonneesSortieCapteurs;
import donnees.Table_donnees_affichage;

public class Model {

	private String cheminFichierSortieCapteurs;
	private String nomFichierSortieCapteurs;
	private Csv_input_data data;
	private static Model monModele = null;
	private String nomPoste;
	private PropertyChangeSupport pcs;
	private ConnectionBDD maBdd;
	private LinkedList<Table_donnees_affichage> linkedListAffichage;
	
	/*
	 * Constructeur
	 */
	private Model(String nomFichier, String nomPoste) {
		cheminFichierSortieCapteurs = "C:/Users/rapha/OneDrive/Ecole/01 - Cours/S8/04 - Ingénierie logicielle/03 - Projet/workspace/Fichiers/Krakov/";
		this.nomFichierSortieCapteurs = nomFichier;
		pcs = new PropertyChangeSupport(this);
		this.nomPoste = nomPoste;
	}
	
	
	

	public static Model getModel(String nomFichier, String nomPoste) {
		if (monModele == null){
			monModele = new Model(nomFichier, nomPoste);
		}
		return monModele;
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
			            maBdd.insererDonneeSortieCapteur(donneesCapteur);
					}
	
				} catch(FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
	}
	
	public Csv_input_data convertir_les_donnees_capteurs_en_donnees_pour_Orowan(DonneesSortieCapteurs donneesCapteur){
		
		// Sélection des données utiles à Orowan
	    double he = donneesCapteur.getEnThick();
	    double hs = donneesCapteur.getExThick();
	    double te = donneesCapteur.getEnTens();
	    double ts = donneesCapteur.getExTens();
	    double diamWR = donneesCapteur.getDaiameter();
	    double wRYoung = donneesCapteur.getYoungModulus();
	    double mu_ini = donneesCapteur.getMu();
	    double force = donneesCapteur.getRollForce();
	    double g = donneesCapteur.getAverageSigma();
	    String nomPoste = donneesCapteur.getNomPoste();
	    
	    // Création d'un objet "Csv_input_data" avec les données sélectionnées
	    Csv_input_data donneesEntreeOrowan = new Csv_input_data(1, he, hs, te, ts, diamWR, wRYoung, 0, mu_ini, force, g, nomPoste);
	    
	    return donneesEntreeOrowan;
	}
	
	
	public boolean transformerCsv_input_data_en_fichierCSVEntreeOrowan(Csv_input_data data) {
		try {
			FileWriter myWriter = new FileWriter("C:/Users/rapha/OneDrive/Ecole/01 - Cours/S8/04 - Ingénierie logicielle/03 - Projet/workspace/Fichiers/sortieOro/" + nomPoste);
			myWriter.write("Cas	He	Hs	Te	Ts	Diam_WR	WRyoung	offset ini	mu_ini	Force	G\n");
			myWriter.write(data.toStringnoNomPoste().replace(',', '\t'));
			myWriter.close();
		} catch (Exception e) {
			return true;
		}
		return false; // success
	}
	
	
	public void runOrowan (Csv_input_data inOrowan) throws IOException, InterruptedException {
		long start = System.currentTimeMillis();
		String cmd = "C:\\Users\\rapha\\OneDrive\\Ecole\\01 - Cours\\S8\\04 - Ingénierie logicielle\\03 - Projet\\workspace\\Fichiers\\Model\\Orowan_x64.exe"; // la commande à exec

		ProcessBuilder processBuilder = new ProcessBuilder(cmd); // Prépare le lancement de CMD avec la ma commande

		Process process = processBuilder.start(); // lance le process

		DataOutputStream writer = new DataOutputStream(process.getOutputStream()); // on setup l'ecriture et la lecture
																					// de l'invite de commande
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
		BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

		// Démarche a suivre d'après le PDF
		try {
			// System.out.println("i\n");
			writer.writeBytes("i\n");
			writer.flush();

			// System.out.println("c\n");
			writer.writeBytes("c\n");
			writer.flush();

			// System.out.println("./Rugo/Ressources/orowan/inv_cst.txt\n");
			writer.writeBytes("C:/Users/rapha/OneDrive/Ecole/01 - Cours/S8/04 - Ingénierie logicielle/03 - Projet/workspace/Fichiers/sortieOro/" + nomPoste + "\n");
			writer.flush();

			// System.out.println("./Rugo/Ressources/orowan/output.txt\n");
			writer.writeBytes("./Rugo/Ressources/orowan/output/" + nomPoste + "\n");
			writer.flush();

			process.waitFor();
			pcs.firePropertyChange("OrowanComputeTime", nomPoste, System.currentTimeMillis() - start);

			process.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	// Stocker les données de sortie d'Orowan dans la bdd
	public Csv_output_data readCsvDonneeSortieOrowan(){
		BufferedReader br = null;
		Csv_output_data donneeOutOrowan = null;

		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader("./Rugo/Ressources/orowan/output/" + nomPoste));// file name with
																									// path
			int flagFirstLine = 1;
			while ((sCurrentLine = br.readLine()) != null) {
				if (flagFirstLine == 1) {
					flagFirstLine = 0;
				} else {
					String[] lineOfData = sCurrentLine.split("\t");

					NumberFormat format = NumberFormat.getInstance();

					try {
						donneeOutOrowan = new Csv_output_data(nomPoste, format.parse(lineOfData[0]).intValue(), lineOfData[1],
								format.parse(lineOfData[2]).doubleValue(), format.parse(lineOfData[3]).doubleValue(),
								format.parse(lineOfData[4]).doubleValue(), format.parse(lineOfData[5]).doubleValue(),
								format.parse(lineOfData[6]).doubleValue(), format.parse(lineOfData[7]).doubleValue(),
								format.parse(lineOfData[8]).doubleValue(), format.parse(lineOfData[9]).doubleValue(),
								format.parse(lineOfData[10]).doubleValue(), lineOfData[11]);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		// System.out.println(donneeOutOrowan.toString());
		return donneeOutOrowan;
	}
	
	public Table_donnees_affichage selectionnerDonneesAffichages() {
		ArrayList<Table_donnees_affichage> uniqueList = new ArrayList<>();
		while (linkedListAffichage.size() != 0) {
			Table_donnees_affichage tester = linkedListAffichage.pop();
			if (!linkedListAffichage.contains(tester) && !uniqueList.contains(tester)) {
				uniqueList.add(tester);
			}
		}
		// System.out.println(uniqueList.size());
		double sigma = 0;
		double rollSpeed = 0;
		double friction = 0;
		int nbrUnique = 0;
		String error = "VOID";
		Table_donnees_affichage donneeAffichage;
		// System.out.println(uniqueList.toString());
		for (Table_donnees_affichage mAffichage : uniqueList) {
			if (mAffichage.getErreur().equals("VOID")) {
				sigma += mAffichage.getSigma();
				friction += mAffichage.getFriction();
				nbrUnique++;
			} else
				error = mAffichage.getErreur();
			rollSpeed += mAffichage.getRolling_speed();
		}
		if (nbrUnique == 0)
			nbrUnique = 1;
		sigma /= nbrUnique;
		friction /= nbrUnique;
		donneeAffichage = new Table_donnees_affichage(rollSpeed / uniqueList.size(), sigma, friction,
				uniqueList.get(0).getNomPoste(), error);
		// System.out.println(donneeAffichage.toString());
		pcs.firePropertyChange("newData", null, null);
		return (donneeAffichage);
	}
	
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
		
	}



	
	

	public String getNomPoste() {
		return nomPoste;
	}

	
	
}
	
	

	
	
	
	
	
	
	
	
	
	
	
/**	
	
	
	
	public static void donneesCapteurs_to_inputOrowan() {
		BufferedReader reader = new BufferedReader(new FileReader(cheminFichierSortieCapteurs));
        FileWriter writer = new FileWriter(outputFile);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] columns = line.split(",");
            if (columns.length >= 4) {
                writer.write(columns[1] + "," + columns[3] + "\n");
            }
        }
        reader.close();
        writer.close();
    }
    
**/	
	
	/*
	 * Cette méthode extrait les données utiles à Orowan depuis les données de sorties de capteurs que l'on a inséré
	 * précédemment dans la bdd. Elle effectue cette extraction toutes les 200 ms en utilisant un thread et la commande 
	 * wait. 
	 * 
	 */
	/**
	public Csv_input_data extract_data() {
		Thread thread = new Thread(new Runnable() {
		String nomPoste;
			public void run() {
				while(true) {
					try {
                        data = maBdd.recupererDerniereDonneeEntreeOrowan(nomPoste); // on extrait uniquement les valeurs utiles à Orowan
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
				}
			}
		});
		thread.start();
		
		return data;
	}
	
	
	
	/*
	 * 
	 * cette méthode exécute "Orowan" avec les données que l'on a récupéré avec la méthode extract_data,
	 * et stocke les valeurs de sortie d'Orowan dans une liste.
	 * 
	 * Cette méthode retourne une liste où chaque élément correspond au fichier de sortie d'Orowan pour une ligne correspondante
	 * 
	 */
	/**
	public Csv_output_data execute_orowan(Csv_input_data data) {
		File exeFile = new File("C:/Users/rapha/OneDrive/Ecole/01 - Cours/S8/04 - Ingénierie logicielle/03 - Projet/workspace/Fichiers/Model/Orowan_x64.exe");
		
	    // List<String> outputOrowanList = new ArrayList<>(); // Nouvelle liste pour stocker les sorties de Orowan
	    
	
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
	           
	                
	                // stockage dans la base de données
	                //maBdd.storeOrowanOutput(data.getId(), output);
	            } else {
	                System.err.println("Erreur lors de l'exécution de Orowan pour la donnée " + data.getId());
	            }
	        } catch (IOException | InterruptedException | SQLException e) {
	            e.printStackTrace();
	        }
	        insererDonneeSortieOrowan(output);
	   }
	
	

	    **/
	   
	
	
	/*
	 * prend 5 valeurs et calcule la moyenne des coefficient de frictions et les stocke dans une liste 
	 * 
	 */
	/**
	public void calculeMoyennesParametres(){
		
		Double averageCoeffFriction = new ArrayList<>(); // liste pour stocker les moyennes de coefficients de friction
		Double averageSigma = new ArrayList<>(); // liste pour stocker les moyennes de coefficients de friction
		Double averageRollingSpeed = new ArrayList<>(); // liste pour stocker les moyennes de coefficients de friction
	    List<Double> currentCoeffFriction = new ArrayList<>(); // liste pour stocker les coefficients de friction courants
	    List<Double> currentSigma = new ArrayList<>(); // liste pour stocker les coefficients de friction courants
	    List<Double> currentRollingSpeed = new ArrayList<>(); // liste pour stocker les coefficients de friction courants
	    
	    for (int i=0;i<5;i++) {
			currentCoeffFriction.append(ConnectionBDD.recupererDerniereDonnesSortieOrowan().get(0));
	     	currentSigma.append(ConnectionBDD.recupererDerniereDonnesSortieOrowan().get(1));
			currentRollingSpeed.append(ConnectionBDD.recupererDerniereDonnesSortieOrowan().get(2));
	    }
	    
	    
	    int sumCoeffFriction = 0;
        for (int i=0; i < currentCoeffFriction.size(); i++) {
            sumCoeffFriction += currentCoeffFriction.get(i);
        }
        
        averageCoeffFriction = (double) (sumCoeffFriction / currentCoeffFriction.size());
        
        int sumSigma = 0;
        for (int i=0; i < currentSigma.size(); i++) {
            sumSigma += currentSigma.get(i);
        }
        
        averageSigma = (double) (sumSigma / currentSigma.size());
        
        int sumRollingSpeed = 0;
        for (int i=0; i < currentRollingSpeed.size(); i++) {
            sumRollingSpeed += currentRollingSpeed.get(i);
        }
        
        averageRollingSpeed = (double) (sumRollingSpeed / currentRollingSpeed.size());
        

		Table_donnees_affichage donneeAffichage = new Table_donnees_affichage(averageRollingSpeed, averageSigma, averageCoeffFriction, nomPoste);
	  
		maBdd.insertionDonneeAffichage(donneeAffichage);
	    
	  
	    
	        
	}
**/


