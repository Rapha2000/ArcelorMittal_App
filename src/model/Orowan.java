package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;

import java.io.DataOutputStream;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;

import controller.ConnectionBDD;
import donnees.Table_donnees_affichage;
import donnees.Csv_input_data;
import donnees.DonneesSortieCapteurs;
import donnees.Csv_output_data;


public class Orowan extends Thread{
	
	private boolean interrompu;
	private ConnectionBDD connectionBDD;
	private PropertyChangeSupport pcs;
	private String stand;
	private LinkedList<Table_donnees_affichage> listeDonneesAffichage;

	


	/**
	 * Constructeur de la classe Orowan
	 *
	 * @param nomPoste le nom du poste de travail associé à la simulation
	 */
	public Orowan(String nomPoste) {
		listeDonneesAffichage = new LinkedList<>();
		this.stand = nomPoste;
		connectionBDD = ConnectionBDD.getInstance();
		pcs = new PropertyChangeSupport(this);
	}
	
	
	
	
	/**
	 * Cette méthode écrit les commandes dans l'invite de commande (CMD) pour lancer Orowan_x64.exe avec 
	 * les fichiers appropriés.
	 * Elle utilise ProcessBuilder pour préparer et lancer CMD et envoie les données nécessaires via 
	 * DataOutputStream.
	 * Les résultats de la commande sont stockés dans les fichiers de sortie correspondants pour chaque 
	 * poste.
	 * 
	 * @throws Exception
	 */
	
	public void runOrowanExe() throws Exception {
	    // Récupération de l'heure actuelle pour mesurer le temps d'exécution
	    long tempsDebut = System.currentTimeMillis();
			
	    // Chemin vers l'exécutable Orowan_x64
	    String exe = "C:/Users/andes/OneDrive/Documents/IMT Mines Alès/2A/cours 2IA/PROJET GL/Fichiers/Model/Orowan_x64.exe";
			
	    // Création d'un nouveau processus pour exécuter l'exécutable
	    ProcessBuilder pb = new ProcessBuilder(exe); 
	    Process process = pb.start(); 

	    // Flux de sortie pour écrire dans le processus
	    DataOutputStream outputStream = new DataOutputStream(process.getOutputStream()); 

	    // Envoi des commandes au processus pour exécuter Orowan
	    try {
	        // Commande pour initialiser Orowan
	        outputStream.writeBytes("i");
	        outputStream.flush();

	        // Commande pour exécuter Orowan
	        outputStream.writeBytes("c");
	        outputStream.flush();

	        // Chemin vers le fichier de configuration pour Orowan
	        outputStream.writeBytes("C:/Users/andes/OneDrive/Documents/IMT Mines Alès/2A/cours 2IA/PROJET GL/Fichiers/Orowan/" + "final" + stand );
	        outputStream.flush();

	        // Chemin vers le fichier de sortie pour Orowan
	        outputStream.writeBytes("C:/Users/andes/OneDrive/Documents/IMT Mines Alès/2A/cours 2IA/PROJET GL/Fichiers/Orowan/output_" + stand );
	        outputStream.flush();

	        // Envoi d'un événement pour signaler la fin de l'exécution d'Orowan
	        pcs.firePropertyChange("tempsOrowan", stand, System.currentTimeMillis() - tempsDebut);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	
	
	


	
	/**
	 * Cette méthode permet d'exécuter le processus d'acquisition des données de capteurs, de conversion, 
	 * d'insertion dans la base de données et d'écriture dans un fichier CSV. 
	 * Elle lit également les données de sortie Orowan à partir d'un fichier CSV, les insère dans 
	 * la base de données, et calcule la moyenne de ces données pour l'affichage. Cette méthode s'exécute 
	 * en boucle pour récupérer les données de capteurs en continu jusqu'à ce qu'elle soit interrompue.
	 * 
	 * @throws Exception si une erreur survient lors de l'exécution du processus d'acquisition de données 
	 * ou de l'écriture de données dans le fichier CSV.
	 * 
	 */
	public void run() {
		try {
			interrompu = false;
			sleep(1000); 
			long time = 1;

			// Création du FileWriter pour écrire dans le fichier "final_stand"
			FileWriter fileWriter = new FileWriter("C:/Users/andes/OneDrive/Documents/IMT Mines Alès/2A/cours 2IA/PROJET GL/Fichiers/Orowan/" + "final"+ stand);
			
			// Écriture de l'en-tête du fichier
			fileWriter.write("Cas\tHe\tHs\tTe\tTs\tDiam_WR\tWRyoung\toffset ini\tmu_ini\tForce\tG\n");

			for (int i = 0; i<237; i++) { // 237 correspond au nombre de lignes de données capteurs
				// Si le temps est positif, on attend le temps spécifié
				if (time > 0)
					sleep(time);
					long tempsDebut = System.currentTimeMillis();
				// Récupération de la dernière donnée de sortie des capteurs depuis la base de données
				DonneesSortieCapteurs donneeSortieCapteur = connectionBDD.recupererDerniereDonneeSortieCapteurs("1939351_F2.txt");
				
				// Affichage de la donnée de sortie capteur et de son identifiant de stand
				System.out.println(stand + " " +donneeSortieCapteur);
				
				// Préparation de la donnée d'entrée pour le programme Orowan
				Csv_input_data donneeEntreeOrowan = prepareDonneeEntreeOrowan(donneeSortieCapteur);
				
				// Insertion de la donnée d'entrée dans la base de données
				connectionBDD.insererDonneeEntreeOrowan(donneeEntreeOrowan);
				
				// Écriture de la donnée d'entrée dans le fichier CSV isrt_cst.txt
				ecritureFichierCsvEntreeOrowan(donneeEntreeOrowan, fileWriter);// ecrit dans file isrt_cst.txt
				
				// Affichage de la donnée de sortie des capteurs
				System.out.println(donneeSortieCapteur.toString());
				
				// Lancement du programme Orowan
				runOrowanExe(); // running orowan
				
				// Lecture de la donnée de sortie d'Orowan depuis le fichier CSV output_stand
				Csv_output_data donneeSortieOrowan = lectureFichierDeSortieOrowan();
				  
				// Insertion de la donnée de sortie dans la base de données
				connectionBDD.insererDonneeSortieOrowan(donneeSortieOrowan);
				
				// Si la dernière donnée d'affichage a été insérée avec succès dans la base de données, on calcule la moyenne et on insère la donnée d'affichage dans la base de données
				if (insererDonneesAffichageData(connectionBDD.recupererDerniereDonneeAffichage(stand))) { 
					
					Table_donnees_affichage donneeAffichage = calculMoyenne();
					connectionBDD.insertionDonneeAffichage(donneeAffichage);
				}
			
			long tempsFin = System.currentTimeMillis();
			time = 200 - (tempsFin - tempsDebut);
			setInterrompu();
		}
		
		fileWriter.close();

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.exit(0);
	}
	
}
	
	/**
	 * 
	 * Cette méthode lit la dernière ligne insérée dans le fichier CSV contenant les résultats de la 
	 * simulation Orowan.
	 * 
	 * @return
	 */
	public Csv_output_data lectureFichierDeSortieOrowan() {

		// On initialise un BufferedReader et Csv_output_data à null
		BufferedReader br = null;
		Csv_output_data donneeSortieOrowan = null;

		try {
			// On lit le fichier CSV avec le nom de fichier déterminé par le chemin donné
			String ligneCourante;
			br = new BufferedReader(new FileReader("C:/Users/andes/OneDrive/Documents/IMT Mines Alès/2A/cours 2IA/PROJET GL/Fichiers/Orowan/output_" + stand));
			
			// On ignore la première ligne et on traite toutes les autres lignes
			int premiereLigne = 1;
			while ((ligneCourante = br.readLine()) != null) {
				if (premiereLigne == 1) {
					premiereLigne = 0;
				} else {
					// On divise la ligne actuelle en chaînes de caractères en utilisant le séparateur tabulation
					String[] ligne = ligneCourante.split("\t");

					// On crée un objet NumberFormat pour pouvoir parser les chaînes de caractères en nombres
					NumberFormat numberFormat = NumberFormat.getInstance();

					// On crée un nouvel objet Csv_output_data à partir des valeurs parsées
					try {
						donneeSortieOrowan = new Csv_output_data(stand, numberFormat.parse(ligne[0]).intValue(), ligne[1],
								numberFormat.parse(ligne[2]).doubleValue(), numberFormat.parse(ligne[3]).doubleValue(),
								numberFormat.parse(ligne[4]).doubleValue(), numberFormat.parse(ligne[5]).doubleValue(),
								numberFormat.parse(ligne[6]).doubleValue(), numberFormat.parse(ligne[7]).doubleValue(),
								numberFormat.parse(ligne[8]).doubleValue(), numberFormat.parse(ligne[9]).doubleValue(),
								numberFormat.parse(ligne[10]).doubleValue(), ligne[11]);
							
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (IOException e) {
			// Si le fichier n'est pas trouvé ou qu'il y a une erreur de lecture, on affiche la trace de la pile d'erreurs
			e.printStackTrace();
		} finally {
			// On ferme le BufferedReader s'il n'est pas null
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		// On renvoie Csv_output_data
		return donneeSortieOrowan;
	}

	
	/**

	Cette méthode permet d'écrire les données d'entrée Orowan dans un fichier CSV.
	@param donneesEntreeOrowan les données d'entrée Orowan à écrire
	@param fileWriter le FileWriter qui va écrire les données dans le fichier CSV
	@return true si une exception est levée lors de l'écriture, false sinon
	*/
	public boolean ecritureFichierCsvEntreeOrowan(Csv_input_data donneesEntreeOrowan, FileWriter fileWriter) {
	try {
	// On écrit les données sans le nom de poste, et on remplace les virgules par des tabulations
	fileWriter.write(donneesEntreeOrowan.toStringnoNomPoste().replace(',', '\t'));
	// On passe à la ligne suivante
	fileWriter.write('\n');
	} catch (Exception e) {
	// Si une exception est levée, on affiche l'erreur et on retourne true
	System.out.println(e);
	return true;
	}
	// Si tout s'est bien passé, on retourne false
	return false;
	}
	
	
	
	
	/**
	 * 
	 * Insère une donnée dans la liste des données d'affichage et retourne true si la taille de la 
	 * liste est supérieure ou égale à 5, ce qui signifie qu'il faut faire la moyenne et réinitialiser la 
	 * liste.
	 * 
	 * @param donneesCourbes
	 * @return booléen indiquant si l'insertion a été effectuée
	 */
	/**

	Cette méthode permet d'insérer des données d'affichage de courbes dans une liste chaînée.
	@param donneeCourbes les données d'affichage de courbes à insérer
	@return true si la liste contient au moins 5 éléments après l'insertion, false sinon
	*/
	public boolean insererDonneesAffichageData(Table_donnees_affichage donneeCourbes) {
	// Si les données d'affichage ne sont pas nulles
	if (donneeCourbes != null) {
	// On ajoute les données d'affichage au début de la liste
	listeDonneesAffichage.addFirst(donneeCourbes);
	// Si la liste contient au moins 5 éléments après l'insertion, on retourne true
	if (listeDonneesAffichage.size() >= 5) {
	return true;
	}
	}
	// Si la liste contient moins de 5 éléments après l'insertion, on retourne false
	return false;
	}
	
	/**
	 * Cette méthode permet de convertir les données de sortie d'un capteur en données d'entrée pour 
	 * Orowan. 
	 * Elle prend en entrée un objet de type DonneesSortieCapteurs et crée un objet Csv_input_data (le 
	 * type de fichier qui est envoyé dans l'exécutable Orowan).
	 * 
	 * @param donneesSortiesCapteur
	 * @return une donnée d'entrée pour Orowan
	 */
	public Csv_input_data prepareDonneeEntreeOrowan(DonneesSortieCapteurs donneesSortiesCapteur) {
		Csv_input_data donneesEntreeOrowan = new Csv_input_data(1, donneesSortiesCapteur.getEnThick(),
				donneesSortiesCapteur.getExThick(), donneesSortiesCapteur.getEnTens(), 
				donneesSortiesCapteur.getExTens(), donneesSortiesCapteur.getDaiameter(), 
				donneesSortiesCapteur.getYoungModulus(), donneesSortiesCapteur.getAverageSigma(),
				donneesSortiesCapteur.getMu(), donneesSortiesCapteur.getRollForce(), 
				donneesSortiesCapteur.getfSlip());
		return donneesEntreeOrowan;
	}

	

	/**
	 * Cette méthode calcule la moyenne des X dernières données reçues, où X est le nombre d'éléments 
	 * dans la liste maListe.
	 * Les données de chaque élément unique sont ajoutées à la moyenne si l'erreur de l'élément 
	 * est "VOID".
	 * Les valeurs moyennes calculées sont utilisées pour créer un nouvel objet 
	 * Table_donnees_affichage qui est retourné.
	 * 
	 * @return  un nouvel objet Table_donnees_affichage qui contient la moyenne des X dernières 
	 * données reçues.
	 * 
	 */
	public Table_donnees_affichage calculMoyenne() {
		// On crée une nouvelle liste d'éléments
		ArrayList<Table_donnees_affichage> maListe = new ArrayList<>();
		// Tant que la liste d'éléments actuelle n'est pas vide
		while (listeDonneesAffichage.size() != 0) {
		// On retire le premier élément de la liste actuelle
		Table_donnees_affichage testListe = listeDonneesAffichage.pop();
		// Si l'élément n'est pas déjà présent dans la nouvelle liste ou dans la liste actuelle
		if (!listeDonneesAffichage.contains(testListe) && !maListe.contains(testListe)) {
		// On l'ajoute à la nouvelle liste
		maListe.add(testListe);
		}
		}
		// On initialise des variables pour les valeurs moyennes à calculer
		double valeurSigma = 0;
		double valeurSpeed = 0;
		double valeurFriction = 0;
		int nb = 0;
		String erreur = "VOID";
		Table_donnees_affichage aAfficher;

		// On parcourt la nouvelle liste d'éléments
		for (Table_donnees_affichage tableAffichage : maListe) {
		// Si l'erreur de l'élément est VOID
		if (tableAffichage.getErreur().equals("VOID")) {
		// On ajoute les valeurs de cet élément aux valeurs moyennes
		valeurSigma += tableAffichage.getSigma();
		valeurFriction += tableAffichage.getFriction();
		nb++;
		} else
		// Sinon, on affecte l'erreur de l'élément à la variable erreur
		erreur = tableAffichage.getErreur();
		valeurSpeed += tableAffichage.getRolling_speed();
		}
		// Si le nombre d'éléments pour lesquels on calcule les moyennes est 0, on l'initialise à 1 pour éviter une division par 0
		if (nb == 0)
		nb = 1;
		// On calcule les moyennes des valeurs des éléments
		valeurSigma /= nb;
		valeurFriction /= nb;
		// On crée un nouvel élément avec les valeurs moyennes calculées, ainsi que le nom du poste du premier élément de la liste actuelle et l'erreur de l'élément ayant une erreur, si elle existe
		aAfficher = new Table_donnees_affichage(valeurSpeed / maListe.size(), valeurSigma, valeurFriction,
		maListe.get(0).getNomPoste(), erreur);

		// On notifie les écouteurs d'un changement de données
		pcs.firePropertyChange("changementDonnee", null, null);
		// On retourne l'élément avec les valeurs moyennes calculées
		return (aAfficher);
		}
	
	
	
	/**
	 * Cette méthode permet de définir la variable interrompu à true, ce qui indique que le 
	 * thread a été interrompu.
	 */
	public void setInterrompu() {
		// TODO Auto-generated method stub
		interrompu = true;
	}


	/**
	 * 
	 * Ajoute un écouteur pour les changements de propriété.
	 *  
	 * @param listener l'écouteur à ajouter
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	/**
	 * Renvoie le nom du poste.
	 * 
	 * @return stand le nom du poste
	 */
	public String getStand() {
		return stand;
	}
	
	/**
	 * méthode permettant de supprimer un PropertyChangeListener de l'objet courant. 
	 * 
	 * @param listener  l'objet PropertyChangeListener à retirer de la liste des listeners 
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	
}