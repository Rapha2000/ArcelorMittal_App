package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;

import controller.ConnectionBDD;
import donnees.DonneesSortieCapteurs;

public class Simulation extends Thread {
	private String cheminVersFichierCapteur;
	private boolean interrompu;
	private ConnectionBDD connectionBDD;
	private String fileName;
	

	
	/**
	 * Constructeur de la classe Simulation qui permet de créer une nouvelle instance de Simulation.
	 * 
	 * 
	 * @param nomFichier le nom du fichier qui contient les données des capteurs à simuler
	 */
	public Simulation(String nomFichier) {
		connectionBDD = ConnectionBDD.getInstance();
		connectionBDD.viderTables();
		cheminVersFichierCapteur = "C:/Users/andes/OneDrive/Documents/IMT Mines Alès/2A/cours 2IA/PROJET GL/Fichiers/Krakov/" + nomFichier;
		this.fileName = nomFichier;
	}

	
	/**
	 * Cette méthode "run()" permet de lire les données d'un capteur depuis un fichier txt/csv,
	 * puis de stocker ces données dans un tableau et de les insérer dans une base de données à travers un objet "DonneesSortieCapteurs".
	 * 
	 * @throws FileNotFoundException si le fichier de données de capteurs n'est pas trouvé.
     * @throws InterruptedException si le thread est interrompu.
     * @throws ParseException si une erreur de parsing se produit lors de la conversion des données de capteurs en nombres.
	 */
	
	
	
	public void run() {
	// Initialisation de la variable d'instance interrompu à false
	interrompu = false;

	// Création d'un objet File correspondant au fichier contenant les données du capteur
	File file = new File(cheminVersFichierCapteur);

	// Création d'un objet Scanner pour lire le fichier txt/csv
	Scanner scan;
	try {
		scan = new Scanner(file);

		// Création d'une chaîne de caractères pour stocker le contenu lu dans le fichier
		String contenuDuFichier = "";

		// Création d'une chaîne de caractères pour stocker le contenu lu dans le fichier avec un délimiteur spécifique
		String delimit = "";

		// Lecture du fichier ligne par ligne
		while (scan.hasNextLine()) {
			contenuDuFichier = contenuDuFichier.concat(scan.nextLine() + "\n");
		}

		/*
		 * La classe StringTokenizer permet de découper une chaîne de caractères en un tableau de chaînes de caractères en utilisant un délimiteur spécifique
		 * Dans ce cas, le délimiteur est ";"
		 */
		StringTokenizer tokenizer = new StringTokenizer(contenuDuFichier, ";");

		// Création d'un tableau pour stocker les paramètres de notre capteur
		double[] stockageParametres = new double[25];

		// Initialisation de l'index k à 0
		int k = 0;

		// Initialisation de la variable debut à la valeur du temps système actuel
		long debut = System.currentTimeMillis();

		// Initialisation de la variable duree à 0
		long duree = 0;

		// Lecture des données dans le fichier ligne par ligne tant qu'il reste des données et que la variable interrompu est à false
		while (tokenizer.hasMoreTokens() && !interrompu) {

			// Récupération de la ligne courante et découpage de la chaîne de caractères en utilisant le délimiteur ";"
			delimit = tokenizer.nextToken(); 

			// Si on est à la première ligne du fichier, on force la première valeur à 100 pour avoir une structure identique à chaque fois
			if (k == 0) {
				delimit = "100";
			} 
			// Si on est à la dernière valeur de la ligne, on force la valeur à 100 et on ré-initialise l'index k pour la lecture de la ligne suivante
			else if (k == 24) {
				delimit = "100"; 
				k = 0;
			} 
			// Sinon, on supprime le premier caractère de la chaîne de caractères
			else {
				delimit = delimit.substring(1); 
			}
			
			// Ajout du découpage à notre chaîne de caractères2
			contenuDuFichier = contenuDuFichier.concat(delimit);

			// Conversion de la chaîne de caractères en nombre à virgule en utilisant le format français ("," au lieu de ".")
			NumberFormat format = NumberFormat.getInstance(Locale.FRANCE); 
			Number nb = format.parse(delimit); 
			stockageParametres[k++] = nb.doubleValue(); 

			// Si on est à la dernière valeur de la ligne, on calcule la durée entre cette valeur et la valeur précédente pour créer un objet Donnees

			if (k == 24) { 
			    long l1 = (long) (stockageParametres[2] * 1000 - duree); // calcule le temps à attendre avant d'insérer les données captées dans la base de données
			    duree = (long) (stockageParametres[2] * 1000); // met à jour la durée entre deux enregistrements
			    long l2 = debut + l1; // calcule l'heure à laquelle les données captées doivent être insérées dans la base de données
			    
			    // crée un objet DonneesSortieCapteurs pour stocker les données captées
			    DonneesSortieCapteurs donneesCapteur = new DonneesSortieCapteurs((int) stockageParametres[0], (int) stockageParametres[1],
			        stockageParametres[2], stockageParametres[3], stockageParametres[4], stockageParametres[5], stockageParametres[6], stockageParametres[7],
			        stockageParametres[8], stockageParametres[9], stockageParametres[10], stockageParametres[11], stockageParametres[12], stockageParametres[13],
			        stockageParametres[14], stockageParametres[15], stockageParametres[16], stockageParametres[17], stockageParametres[18], stockageParametres[19],
			        stockageParametres[20], stockageParametres[21], stockageParametres[22], stockageParametres[23], fileName);
			        
			    // attend jusqu'à ce que l'heure à laquelle les données doivent être insérées soit atteinte
			    while (System.currentTimeMillis() < l2) {
			        sleep(1); // suspend le thread pendant 1 milliseconde
			    }
			    
			    // insère les données captées dans la base de données
			    connectionBDD.insererDonneeSortieCapteur(donneesCapteur); 
			    
			    debut = System.currentTimeMillis(); // met à jour le temps de début
			    if (isInterrupted()) // vérifie si le thread a été interrompu
			        break; // sort de la boucle while si le thread a été interrompu
			}
		}}

	catch (FileNotFoundException | InterruptedException | ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();}
	}

	

	/**
	 *  Assigne la valeur true à la variable interrompu, qui est utilisée pour signaler que le 
	 *  processus en cours doit être interrompu.	
	 */
	public void setInterrompu() {
		// TODO Auto-generated method stub
		interrompu = true;
	}

}