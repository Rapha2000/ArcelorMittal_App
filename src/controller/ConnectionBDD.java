package controller;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import donnees.Csv_input_data;
import donnees.Csv_output_data;
import donnees.DonneesSortieCapteurs;
import donnees.Table_donnees_affichage;
import donnees.Users;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**

*Cette classe représente la DAO de notre programme.  Elle fait le lien entre notre BDD H2 et le code source java et permet de séparer la gestion des données avec les autres packages.
*Nous réalisons ici les différentes actions CRUD.
 */
public class ConnectionBDD {

	private  Connection conn;
	private static ConnectionBDD obj = null;
	
	// Cette méthode établit une connexion à une base de données H2 locale et crée une table "users" si elle n'existe pas déjà.
	private ConnectionBDD() {

	    // Informations de connexion à la base de données H2
	    String jdbcUrl = "jdbc:h2:tcp://localhost/~/test"; // URL de connexion à la base de données H2
	    String username = "sa"; // Nom d'utilisateur pour se connecter à la base de données
	    String password = "anaisdespres"; // Mot de passe pour se connecter à la base de données
	    
	    // Connexion à la base de données H2
	    try {
	        // Etablissement de la connexion avec les informations d'identification
	        conn = DriverManager.getConnection(jdbcUrl, username, password);

	        // Création d'une table "users" si elle n'existe pas déjà
	        String createTableQuery = "CREATE TABLE IF NOT EXISTS USERS (name VARCHAR PRIMARY KEY, password VARCHAR, id INTEGER)";
	        Statement stmt = conn.createStatement();
	        stmt.execute(createTableQuery);

	    } catch (SQLException e) {
	        // affichage de l'erreur
	    	e.printStackTrace();
	      
	    }
	}

	/**
	 * récupérer l'instance du singleton ConnectionBDD 
	 */
	public static ConnectionBDD getInstance() {
	    // Vérifie si l'objet n'a pas encore été créé
	    if (obj == null)
	        // Création de l'objet
	        obj = new ConnectionBDD();
	    // Retourne l'objet
	    return obj;
	}
	
	
	/**
	 * Permet de vider les tables de la base de données sans les supprimer
	 */
	public void viderTables() {
		// Prépare la requête SQL pour vider chaque table
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("TRUNCATE TABLE FILE_FORMAT");
			// Exécute la requête SQL pour vider la table FILE_FORMAT
			ps.execute();
			
			ps = conn.prepareStatement("TRUNCATE TABLE CSV_INPUT_DATA");
			// Exécute la requête SQL pour vider la table CSV_INPUT_DATA
			ps.execute();
			
			ps = conn.prepareStatement("TRUNCATE TABLE CSV_OUTPUT_DATA");
			// Exécute la requête SQL pour vider la table CSV_OUTPUT_DATA
			ps.execute();
			
			ps = conn.prepareStatement("TRUNCATE TABLE TABLE_DONNEE_AFFICHAGE");
			// Exécute la requête SQL pour vider la table TABLE_DONNEE_AFFICHAGE
			ps.execute();
			
			// Ferme le preparedStatment
			ps.close();
		} catch (SQLException e) {
			// En cas d'erreur, affiche le message d'erreur
			e.printStackTrace();
		}
	}
	

	/**
	 * Récupère la dernière donnée sortie par les capteurs pour un poste donné.
	 * @param nomPoste le nom du poste pour lequel on veut récupérer la dernière donnée de sortie.
	 * @return un objet DonneesSortieCapteurs contenant les données de la dernière sortie dans les capteurs pour le poste donné.
	**/
	public DonneesSortieCapteurs recupererDerniereDonneeSortieCapteurs(String nomPoste) {
		DonneesSortieCapteurs DerniereDonneeSortieCapteurs = null;
		try {
			Statement st = conn.createStatement();
			String requeteBDD = "SELECT * FROM FILE_FORMAT WHERE NOM_POSTE = '" + nomPoste	+ "' ORDER BY id DESC LIMIT 1";
			
			if (st.execute(requeteBDD)) {
				ResultSet rs = st.getResultSet();

				while (rs.next()) {
					// Création d'un objet DonneesSortieCapteurs avec les valeurs récupérées de la base de données
					DerniereDonneeSortieCapteurs = new DonneesSortieCapteurs(rs.getInt(1), rs.getInt(2),
							rs.getDouble(3), rs.getDouble(4),
							rs.getDouble(5), rs.getDouble(6), rs.getDouble(7),
							rs.getDouble(8), rs.getDouble(9), rs.getDouble(10),
							rs.getDouble(11), rs.getDouble(12), rs.getDouble(13),
							rs.getDouble(14), rs.getDouble(15), rs.getDouble(16),
							rs.getDouble(17), rs.getDouble(18), rs.getDouble(19),
							rs.getDouble(20), rs.getDouble(21), rs.getDouble(22),
							rs.getDouble(23), rs.getDouble(24), rs.getString(25) );
				}
			}
		} catch (Exception e) {
			e.printStackTrace();;
		}
		return DerniereDonneeSortieCapteurs;
	}
	 

		/**
		 * Cette méthode insère une donnée de sortie de capteur dans la base de données.
		 * @param outputDonneesCapteur contenant les informations de la donnée à insérer.
		 */
		public void insererDonneeSortieCapteur(DonneesSortieCapteurs outputDonneesCapteur) {
		    
		    // Définition des champs de la table "FILE_FORMAT" dans laquelle les données doivent être insérées
		    String requeteBDD = "LP,MATID,XTIME,XLOC,ENTHICK,EXTHICK,ENTENS,EXTENS,ROLLFORCE,FSLIP,DAIAMETER,"
		                    + "ROLLED_LENGTH_FOR_WORK_ROLLS,YOUNGMODULUS,BOCKUP_ROLL_DIA,ROLLED_LENGTH_FOR_BACKUP_ROLLS,"
		                    + "MU,TORQUE,AVERAGESIGMA,INPUTERROR,LUBWFLUP,LUBWFLLO,LUBOILFLUP,LUBOILFLLO,WORK_ROLL_SPEED,NOM_POSTE";
		    
		    try {
		        // Préparation de la requête SQL pour insérer les données dans la table "FILE_FORMAT"
		        PreparedStatement ps = conn.prepareStatement("INSERT INTO FILE_FORMAT("
		                + requeteBDD + ") VALUES (" + outputDonneesCapteur.toString() + ")");
		        
		        // Exécution de la requête SQL pour insérer les données dans la table "FILE_FORMAT"
		        ps.execute();
		        
		        // Fermeture du PreparedStatement
		        ps.close();
		        
		    } catch (SQLException e) {
		        // En cas d'erreur lors de l'insertion des données, l'exception est affichée à l'écran
		        System.err.println("INSERT INTO FILE_FORMAT("
		                + requeteBDD + ") VALUES (" + outputDonneesCapteur.toString() + ")");
		        System.err.println(outputDonneesCapteur.toString());
		        e.printStackTrace();
		    }
		}
		
	 

		/**
		 * Insère les données d'entrée pour Orowan dans la base de données.
		 * @param inputDonneesOrowan Les données d'entrée à insérer.
		 */
		public void insererDonneeEntreeOrowan(Csv_input_data inputDonneesOrowan) {
		    // Chaine de caractères représentant les noms des colonnes dans la table CSV_INPUT_DATA
		    String requeteBDD = "CAS,HE,HS,TE,TS,DIAM_WR,WRYOUNG,OFSET,MU_INI,FORCE_DATA,G";
		    
		    try {
		        // Prépare la requête d'insertion en remplaçant les valeurs de la chaîne request et les valeurs de la classe Csv_input_data.
		        PreparedStatement ps = conn.prepareStatement("INSERT INTO CSV_INPUT_DATA(" + requeteBDD + ") VALUES (" + inputDonneesOrowan.toString() + ")");
		        // Exécute la requête d'insertion.
		        ps.execute();
		        // Ferme la connexion à la base de données.
		        ps.close();
		    } catch (SQLException e) {
		        // affichage de l'erreur
		        e.printStackTrace();
		    }
		}
		
	/**
	 * Récupère la dernière donnée d'entrée pour Orowan que l'on a mise dans la BDD pour un poste spécifié.
	 * @param nomPoste Le nom du poste à récupérer.
	 * @return Les données d'entrée d'Orowan pour le poste spécifié.
	 */
	public Csv_input_data recupererDerniereDonneeEntreeOrowan(String nomPoste) {
	    Csv_input_data DerniereDonneeEntreeOrowan = null;
	    try {
	        // Crée un objet statement pour exécuter des requêtes SQL.
	        Statement st = conn.createStatement();
	        // Chaîne de caractères représentant la requête SQL à exécuter.
	        String requeteBDD = "SELECT * FROM CSV_INPUT_DATA WHERE nom_poste = '" + nomPoste + "' ORDER BY id DESC LIMIT 1";
	        // Exécute la requête SQL et vérifie si elle renvoie un résultat.
	        if (st.execute(requeteBDD)) {
	            // Si la requête a renvoyé un résultat, récupère le ResultSet.
	            ResultSet rs = st.getResultSet();
	            // Parcourt le ResultSet et crée un objet Csv_input_data à partir des données récupérées.
	            while (rs.next()) {
	                DerniereDonneeEntreeOrowan = new Csv_input_data(rs.getInt(2), rs.getDouble(3),
	                        rs.getDouble(4), rs.getDouble(5), rs.getDouble(6),
	                        rs.getDouble(7), rs.getDouble(8), rs.getDouble(9),
	                        rs.getDouble(10), rs.getDouble(11), rs.getDouble(12));
	            }
	        }
	        // Ferme l'objet statement.
	        st.close();
	    } catch (Exception e) {
	        // affichage de l'erreur.
	    	e.printStackTrace();;
	    }
	    // Retourne les données d'entrée d'Orowan pour le poste spécifié.
	    return DerniereDonneeEntreeOrowan;
	}




	
	/**
	 * Insère les données de sortie d'Orowan dans la base de données.
	 * @param outputDonneesOrowan Les données de sortie à insérer.
	 */
	public void insererDonneeSortieOrowan(Csv_output_data outputDonneesOrowan) {
	    // Chaine de caractères représentant les noms des colonnes dans la table CSV_OUTPUT_DATA
	    String requeteBDD = "CASE_,ERROR,OFFSETYIELD,FRICTION,ROLLING_TORQUE,SIGMA_MOY,SIGMA_INI,SIGMA_OUT,SIGMA_MAX,FORCE_ERROR,SLIP_ERROR,HAS_CONVERGED,NOM_POSTE";
	    
	    try {
	        // Prépare la requête d'insertion en remplaçant les valeurs de la chaîne request et les valeurs de la classe Csv_output_data.
	        PreparedStatement ps = conn.prepareStatement("INSERT INTO CSV_OUTPUT_DATA(" + requeteBDD + ") VALUES (" + outputDonneesOrowan.toString() + ")");
	        // Exécute la requête d'insertion.
	        ps.execute();
	        // Ferme la connexion à la base de données.
	        ps.close();
	    } catch (SQLException e) {
	        // Si une exception SQL se produit, imprimez la pile d'appels et les messages d'erreur.
	        e.printStackTrace();
	    }
	}



	/**
	 * recupere la derniere donnée sortie par Orowan
	 * @param nomPoste le nom du poste
	 * @return la liste des dernières données sorties par Orowan
	 */
	public ArrayList<Double> recupererDerniereDonneeSortieOrowan(String nomPoste) {
		ArrayList<Double> derniereSortieOrowan = new ArrayList<Double>();
		try {
			Statement st = conn.createStatement();
			String requeteBDD = "SELECT * FROM CSV_OUTPUT_DATA WHERE nom_poste = '" + nomPoste
					+ "' ORDER BY ID3 DESC LIMIT 1";
			if (st.execute(requeteBDD)) {
				ResultSet rs = st.getResultSet();
				while (rs.next()) {
					// On ajoute chaque donnée à la liste
					derniereSortieOrowan.add(rs.getDouble(5));
					derniereSortieOrowan.add(rs.getDouble(6));
					derniereSortieOrowan.add(rs.getDouble(7));
				}
			}
		} catch (Exception e) {
			// affichage de l'erreur
			e.printStackTrace();
		}
		return derniereSortieOrowan;
	}

	/**
	 * insère les données servant à l'affichage des courbes dans la BDD
	 * @param donneeAffichage la donnée à inserer
	 * @return si cela a été fait
	 */
	public boolean insertionDonneeAffichage(Table_donnees_affichage donneeAffichage) {
		try {
			String requeteBDD = "INSERT INTO table_donnee_affichage (friction, roll_speed, sigma, nom_poste, erreur) VALUES (? , ? , ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(requeteBDD); // protection injection SQL
			ps.setDouble(1, donneeAffichage.getFriction());
			ps.setDouble(2, donneeAffichage.getRolling_speed());
			ps.setDouble(3, donneeAffichage.getSigma());
			ps.setString(4, donneeAffichage.getNomPoste());
			ps.setString(5, donneeAffichage.getErreur());
			ps.execute();
			ps.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * recupération des données d'affichage stockées dans la BDD
	 * @param nomPoste le nom du poste des données à récupérer
	 * @return la liste des données récupérées
	 */
	public ArrayList<Table_donnees_affichage> recupererDonneesAffichage(String nomPoste) {
		ArrayList<Table_donnees_affichage> listeDonneesAffichage = new ArrayList<>();
		try {
			String requeteBDD = "SELECT * FROM table_donnee_affichage WHERE nom_poste = '" + nomPoste
					+ "' ORDER BY id ASC";
			Statement st = conn.createStatement();
			if (st.execute(requeteBDD)) { // Execute la requête et récupère le résultat sous forme de ResultSet
				ResultSet rs = st.getResultSet();
				while (rs.next()) { // Boucle sur les résultats de la requête
					// Création d'un objet Table_donnees_affichage pour chaque ligne récupérée
					Table_donnees_affichage donneeAffichage = new Table_donnees_affichage(rs.getDouble(2),
							rs.getDouble(3), rs.getDouble(4), rs.getString(5),
							rs.getString(6));
					listeDonneesAffichage.add(donneeAffichage); // Ajout de l'objet à la liste de résultats
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); //Affichage des erreurs
		}
		return listeDonneesAffichage; // Renvoie la liste des données d'affichage
	}


	/**
	 * récupère la dernière données d'affichage entrée dans la BDD
	 * @param nomPoste le nom du poste de la donnée à récupérer
	 * @return la donnée récupérée
	 */
	public Table_donnees_affichage recupererDerniereDonneeAffichage(String nomPoste) {
	    // Initialisation de la variable qui sera retournée
	    Table_donnees_affichage derniereDonneeAffichage = null;
	    try {
	        // Création d'une nouvelle déclaration pour exécuter la requête SQL
	        Statement st = conn.createStatement();
	        // Requête SQL pour récupérer la dernière donnée d'affichage en fonction du nom de poste spécifié
	        String requeteBDD = "SELECT file_format.xtime,file_format.work_roll_speed, "
	            + "csv_output_data.sigma_ini, csv_output_data.friction, csv_output_data.error "
	            + "FROM file_format " + "INNER JOIN csv_output_data "
	            + "ON file_format.id = csv_output_data.ID3 "
	            + "WHERE file_format.nom_poste = '" + nomPoste + "' "
	            + "ORDER BY id DESC LIMIT 1";
	        // Vérification si la requête SQL peut être exécutée
	        if (st.execute(requeteBDD)) {
	            // Récupération des résultats de la requête SQL
	            ResultSet rs = st.getResultSet();
	            // Parcours des résultats de la requête SQL
	            while (rs.next()) {
	                // Création d'un objet Table_donnees_affichage avec les résultats de la requête SQL
	                derniereDonneeAffichage = new Table_donnees_affichage(rs.getDouble(2), rs.getDouble(3),
	                        rs.getDouble(4), nomPoste, rs.getString(5));
	            }
	        }
	    } catch (Exception e) {
	        // Affichage de l'erreur
	    	e.printStackTrace();
	    }
	    // Retourne la dernière donnée d'affichage récupérée
	    return derniereDonneeAffichage;
	}


	
	
	/**
	 * permet de vérifier si un utilisateur existe déjà dans la BDD
	 * @param username le nom d'utilisateur
	 * @return le booléen qui dit si oui ou non l'utilisateur existe
	 */
	public boolean utilisateurExiste(String username) {
		// Initialise existence à false
		boolean existence = false;
		
		try {
			// Prépare la requête SQL avec un paramètre pour éviter les injections SQL
			String requeteBDD = "SELECT * FROM USERS WHERE NAME = ?";
			PreparedStatement ps = conn.prepareStatement(requeteBDD);
			
			// Associe la valeur du paramètre
			ps.setString(1, username);
			
			// Exécute la requête SQL et stocke les résultats dans un objet ResultSet
			ResultSet rs = ps.executeQuery();
			
			// Parcourt les résultats un par un
			while (rs.next()) {
				// Si un utilisateur est trouvé, met existence à true
				existence = true;
			}

		} catch (Exception e) {
			// En cas d'erreur, affiche la pile d'erreurs
			e.printStackTrace();
		}
		
		// Retourne la valeur de existence, true si l'utilisateur existe, false sinon
		return existence;
	}

	
	/**
	 * crée un nouvel utilisateur dans la BDD en fonction des paramètres suivants : 
	 * @param username le nom d'utilisateur
	 * @param password son mot de passe
	 * @param int son droit
	 * @return s'il a été crée ou non
	 */
	public boolean creerNouvelUtilisateur(String username, String password, int admin) {
		try {
			// Vérifie si l'utilisateur n'existe pas déjà dans la base de données.
			if (! utilisateurExiste(username)) {
				// Prépare la requête SQL avec les paramètres fournis.
				String requeteBDD = "INSERT INTO USERS (name, password, id) VALUES (?, ?, ?)";
				PreparedStatement ps = conn.prepareStatement(requeteBDD);
				ps.setString(1, username);
				ps.setString(2, password);
				ps.setInt(3, admin);
				
				// Exécute la requête SQL pour créer l'utilisateur.
				ps.execute();
				
				// Ferme la connexion.
				ps.close();
				
				// Renvoie true pour indiquer que l'utilisateur a été créé avec succès.
				return true;
			}
		} catch (Exception e) {
			// En cas d'erreur, affiche la pile d'erreurs.
			e.printStackTrace();
		}
		
		// Renvoie false si l'utilisateur n'a pas été créé avec succès.
		return false;
	}

	/**
	 * Récupération de tous les utilisateurs présents dans la BDD
	 * @return la liste des utilisateurs présents dans la BDD
	 */
	public ArrayList<Users> recupererUtilisateurs() {
	    ArrayList<Users> listeUtilisateurs = new ArrayList<>();
	    try {
	        String requeteBDD = "SELECT NAME, ID FROM USERS";
	        Statement st = conn.createStatement();
	        if (st.execute(requeteBDD)) {
	            ResultSet rs = st.getResultSet();
	            while (rs.next()) {
	                // Création d'un objet Users pour chaque ligne de résultat retournée par la requête SQL
	                Users utilisateur = new Users(rs.getInt(2),rs.getString(1));
	                listeUtilisateurs.add(utilisateur);
	            }
	        }
	    } catch (Exception e) {
	        // Affichage de l'erreur s'il y a eu un problème lors de l'exécution de la requête SQL
	        System.err.println(e.getClass().getName() + ": " + e.getMessage());
	    }
	    return listeUtilisateurs;
	}

	/**
	 * crée un nouvel utilisateur à partir d'un nom d'utilisateur et d'un mot de passe
	 * @param username le nom d'utilisateur
	 * @param password le mot de passe 
	 * @return l'utilisateur crée
	 */
	public Users existenceEtRole(String username, String password) { 
		
		// Initialise id à -1 pour indiquer qu'aucun utilisateur n'a été trouvé
		int id = -1;
		
		try {
			// Prépare la requête SQL avec des paramètres pour éviter les injections SQL
			String requeteBDD = "SELECT * FROM USERS WHERE NAME = ?  AND password = ?";
			PreparedStatement ps = conn.prepareStatement(requeteBDD);
			
			// Associe les valeurs des paramètres
			ps.setString(1, username);
			ps.setString(2, password);
			
			// Exécute la requête SQL et stocke les résultats dans un objet ResultSet
			ResultSet rs = ps.executeQuery();
			
			// Parcourt les résultats un par un
			while (rs.next()) {
				// Si l'utilisateur a un rôle admin, met id à 1, sinon à 0
				if (rs.getBoolean("ID"))
					id = 1;
				else
					id = 0;
			}

		} catch (Exception e) {
			// En cas d'erreur, affiche la pile d'erreurs
			e.printStackTrace();
		}
		
		// Retourne un objet Users avec l'id trouvé et le nom d'utilisateur fourni
		return new Users(id, username);
	}


	

	/**
	 * supprime l'utilisateur de la BDD
	 * @param username le nom d'utilisateur
	 * @return si il a été supprimé ou non
	 */
	public boolean supprimerUtilisateur(String username) {
		// Initialise utilisateurSupprime à false
		boolean utilisateurSupprime = false;
		
		try {
			// Prépare la requête SQL avec un paramètre pour éviter les injections SQL
			String requeteBDD = "DELETE FROM USERS WHERE NAME = ?";
			PreparedStatement ps = conn.prepareStatement(requeteBDD);
			
			// Associe la valeur du paramètre
			ps.setString(1, username);
			
			// Exécute la requête SQL pour supprimer l'utilisateur
			ps.execute();
			
			// Ferme la connexion
			ps.close();
			
			// Met utilisateurSupprime à true pour indiquer que l'utilisateur a été supprimé
			utilisateurSupprime = true;
			
		} catch (Exception e) {
			// En cas d'erreur, affiche la pile d'erreurs
			e.printStackTrace();
		}
		
		// Retourne la valeur de utilisateurSupprime, true si l'utilisateur a été supprimé, false sinon
		return utilisateurSupprime;
	}




	

	/**
	 * change le droit d'un utilisateur dans la BDD
	 * @param username le nom d'utilisateur
	 * @param droit son droit
	 * @return si la mise à jour est faite ou non
	 */
	public boolean miseAJourDroits(String username, int droit) {
		// Prépare la requête SQL pour mettre à jour le droit de l'utilisateur.
		String requeteBDD = "UPDATE USERS SET id = ? WHERE name = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(requeteBDD);
			ps.setInt(1, droit);
			ps.setString(2, username);
			
			// Exécute la requête SQL pour mettre à jour le droit de l'utilisateur.
			ps.execute();
			
			// Ferme la connexion.
			ps.close();
			
			// Renvoie true pour indiquer que la mise à jour a été effectuée avec succès.
			return true;
		} catch (Exception e) {
			// En cas d'erreur, affiche la pile d'erreurs.
			e.printStackTrace();
		}
		
		// Renvoie false si la mise à jour n'a pas été effectuée avec succès.
		return false;
	}

	
	
	

}

