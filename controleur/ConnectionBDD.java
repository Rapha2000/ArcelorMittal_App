package Controller;



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



public class ConnectionBDD {

	private  Connection conn;
	private static ConnectionBDD obj = null;
	
	private ConnectionBDD (){

            // Informations de connexion à la base de données H2
            String jdbcUrl = "jdbc:h2:~/test";
            String username = "sa";
            String password = "anaisdespres";

            // Connexion à la base de données H2
           
			try {
				conn = DriverManager.getConnection(jdbcUrl, username, password);
			

            // Création d'une table "users"
            String createTableQuery = "CREATE TABLE IF NOT EXISTS USER (id INT PRIMARY KEY, name VARCHAR(255), password VARCHAR(200)";
            Statement stmt = conn.createStatement();
            stmt.execute(createTableQuery);

            // Fermeture de la connexion à la base de données H2
            conn.close();}
			
            catch (SQLException e) {
				// TODO Auto-generated catch block
            	System.err.println(e.getClass().getName() + ": " + e.getMessage());
    			System.exit(0);
			}
        }
	
	
	/**
	 * Insertion donnée sortie capteur dans la BDD
	 */
	
	public void insererDonneeSortieCapteur(DonneesSortieCapteurs outputDataCapteur) {
		String request = "ID,LP,MATID,XTIME,XLOC,ENTHICK,EXTHICK,ENTENS,EXTENS,ROLLFORCE,FSLIP,DAIAMETER,ROLLED_LENGTH_FOR_WORK_ROLLS,YOUNGMODULUS,BOCKUP_ROLL_DIA,ROLLED_LENGTH_FOR_BACKUP_ROLLS,MU,TORQUE,AVERAGESIGMA,INPUTERROR,LUBWFLUP,LUBWFLLO,LUBOILFLUP,FLUBOILFLLO,WORK_ROLL_SPEED,NOM_POSTE";
		
		try {
			PreparedStatement preparedStatment = conn.prepareStatement("INSERT INTO FILE_FORMAT("
					+ request + ") VALUES (" + outputDataCapteur.toString() + ")");
			preparedStatment.execute();
			preparedStatment.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("INSERT INTO FILE_FORMAT("
					+ request + ") VALUES (" + outputDataCapteur.toString() + ")");
			System.err.println(outputDataCapteur.toString());
			e.printStackTrace();
		}
	}
	

	/**
	 * Insertion donnée de sortie d'orowan dans la BDD 
	 */
	public void insererDonneeSortieOrowan(Csv_output_data outputOrowanData) {
		String request = "ID,CASE_,ERROR,OFFSETYIELD,FRICTION,ROLLING_TORQUE,SIGMA_MOY,SIGMA_INI,SIGMA_OUT,SIGMA_MAX,FORCE_ERROR,SLIP_ERROR,HAS_CONVERGED,NOM_POSTE";
		try {
			PreparedStatement preparedStatment = conn.prepareStatement("INSERT INTO table_data_orowan_out("
					+ request + ") VALUES (" +outputOrowanData.toString() + ")");
			preparedStatment.execute();
			preparedStatment.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Insertion donnée d'entrée d'orowan dans la BDD
	 */
	public void insererDonneeEntreeOrowan(Csv_input_data inputOrowanData) {
		String request = "ID,CAS,HE,HS,TE,TS,DIAM_WR,WRYOUNG,MU_INI,FORCE_DATA,G,NOM_POSTE,OFSET";
		try {
			PreparedStatement preparedStatment = conn.prepareStatement("INSERT INTO table_data_orowan_in("
					+ request + ") VALUES (" + inputOrowanData.toString() + ")");
			preparedStatment.execute();
			preparedStatment.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

	/**
	 * recupere la derniere donnée entrée dans orowan
	 */
	public Csv_input_data recupererDerniereDonneeEntreeOrowan(String nomPoste) {
		Csv_input_data DerniereDonneeEntreeOrowan = null;
		try {
			Statement statement = conn.createStatement();
			String request = "SELECT * FROM CSV_INPUT_DATA WHERE nom_poste = '" + nomPoste
					+ "' ORDER BY id DESC LIMIT 1";
			if (statement.execute(request)) {
				ResultSet resultSet = statement.getResultSet();
				while (resultSet.next()) {
					DerniereDonneeEntreeOrowan = new Csv_input_data(resultSet.getInt(2), resultSet.getDouble(3),
							resultSet.getDouble(4), resultSet.getDouble(5), resultSet.getDouble(6),
							resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9),
							resultSet.getDouble(10), resultSet.getDouble(11), resultSet.getDouble(12),
							resultSet.getString(13));
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return DerniereDonneeEntreeOrowan;
	}
	

	/**
	 * recupere la derniere donnée entrée dans les capteurs
	 */
	public DonneesSortieCapteurs recupererDerniereDonneeEntreeCapteurs(String nomPoste) {
		DonneesSortieCapteurs DerniereDonneeEntreeCapteurs = null;
		try {
			Statement statement = conn.createStatement();
			String request = "SELECT * FROM FILE_FORMAT WHERE nom_poste = '" + nomPoste
					+ "' ORDER BY id DESC LIMIT 1";
			if (statement.execute(request)) {
				ResultSet resultSet = statement.getResultSet();
				while (resultSet.next()) {
					DerniereDonneeEntreeCapteurs = new DonneesSortieCapteurs(resultSet.getInt(2), resultSet.getInt(3),
							resultSet.getDouble(4), resultSet.getDouble(5), resultSet.getDouble(6),
							resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9),
							resultSet.getDouble(10), resultSet.getDouble(11), resultSet.getDouble(12),
							resultSet.getDouble(13), resultSet.getDouble(14), resultSet.getDouble(15),
							resultSet.getDouble(16), resultSet.getDouble(17), resultSet.getDouble(18),
							resultSet.getDouble(19), resultSet.getDouble(20), resultSet.getDouble(21),
							resultSet.getDouble(22), resultSet.getDouble(23), resultSet.getDouble(24),
							resultSet.getDouble(25), resultSet.getString(26));
					// System.out.println(donneeOutCapteur);
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return DerniereDonneeEntreeCapteurs;
	}
	
	/**
	 * recupere la derniere donnée sortie par Orowan
	 */
	public ArrayList recupererDerniereDonneeSortieOrowan(String nomPoste) {
		ArrayList<Double> lastOutputOrowan = new ArrayList<Double>();
		try {
			Statement statement = conn.createStatement();
			String request = "SELECT * FROM CSV_OUTPUT_DATA WHERE nom_poste = '" + nomPoste
					+ "' ORDER BY id DESC LIMIT 1";
			if (statement.execute(request)) {
				ResultSet resultSet = statement.getResultSet();
				while (resultSet.next()) {
				
					lastOutputOrowan.add(resultSet.getDouble(5));
					lastOutputOrowan.add(resultSet.getDouble(6));
					lastOutputOrowan.add(resultSet.getDouble(7));
					
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return lastOutputOrowan;
	}
	
	
	
	
	
	
	

	/**
	 * permet de vider les tables de la BDD sans les supprimer intrasèquement
	 */
	public void viderTables() {
		PreparedStatement preparedStatment;
		try {
			preparedStatment = conn.prepareStatement("TRUNCATE TABLE FILE_FORMAT");
			preparedStatment.execute();
			preparedStatment = conn.prepareStatement("TRUNCATE TABLE CSV_INPUT_DATA");
			preparedStatment.execute();
			preparedStatment = conn.prepareStatement("TRUNCATE TABLE CSV_OUTPUT_DATA");
			preparedStatment.execute();
			preparedStatment = conn.prepareStatement("TRUNCATE TABLE TABLE_DONNEE_AFFICHAGE");
			preparedStatment.execute();
			preparedStatment.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * récupération de tous les utilisateurs de la BDD 
	 */
	public ArrayList<Users> recupererUtilisateurs() {
		ArrayList<Users> listeUtilisateurs = new ArrayList<>();
		try {
			String request = "SELECT username, admin FROM USERS";
			Statement statement = conn.createStatement();
			if (statement.execute(request)) {
				ResultSet resultSet = statement.getResultSet();
				while (resultSet.next()) {
					Users user = new Users(resultSet.getInt(2),resultSet.getString(1));
					listeUtilisateurs.add(user);
				}
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return listeUtilisateurs;
	}
	
	
	
	/**
	 * est-ce que l'utilisateur existe et sont role 1 admin 0 user -1 n'existe pas
	 */
	public Users existenceEtRole(String username, String password) { // 1 admin 0 user -1 n'existe pas
		int id = -1;
		try {
			String request = "SELECT * FROM USER WHERE NAME = ?  AND password = ?" ;
			PreparedStatement preparedStatement = conn.prepareStatement(request); // protection injection SQK
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery(); //résultat de la requête 
			while (resultSet.next()) {
				if (resultSet.getBoolean("admin"))
					id = 1;
				else
					id = 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Users(id, username);}
	


/**
 *  vérifie si l'utilisateur que l'on souhaite créer existe dejà dnas la base ou non 
 */
public boolean utilisateurExiste(String username) {
	boolean existence = false;
	try {
		String request = "SELECT * FROM USER WHERE NAME = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(request); // protection injection SQK
		preparedStatement.setString(1, username);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			existence=true;
		}

	} catch (Exception e) {
		e.printStackTrace();
	}
	return existence;
}

/**
 * permet la suppression d'un utilisateur 
 */
public boolean supprimerUtilisateur(String username) {
	boolean utilisateurSupprime = false;
	try {
		String request = "DELETE FROM USER WHERE username = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(request); // protection injection SQK
		preparedStatement.setString(1, username);
		preparedStatement.execute();
		preparedStatement.close();
		utilisateurSupprime=true;
	} catch (Exception e) {
		// TODO: handle exception
	}
	return utilisateurSupprime;
}


/**
 * permet la création d'un nouvel utilisateur
 */
public boolean creerNouvelUtilisateur(String username, String password, int isAdmin) {
	try {
		if (! utilisateurExiste(username)) { //on vérifie que l'utilisateur n'existe pas déjà
			String request = "INSERT INTO USER (name, password, id) VALUES (? , ? , ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(request); // protection injection SQK
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setInt(3, isAdmin);
			preparedStatement.execute();
			preparedStatement.close();
			return true;
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return false;
}
/**
 * permet la mise à jour d'un droit pour un utilisateur 
 */
public boolean miseAJourDroits(String username, int droit) {
	String request = "UPDATE USER SET id = ? WHERE name = ?";
	try {
		PreparedStatement preparedStatement = conn.prepareStatement(request); // protection injection SQK
		preparedStatement.setInt(1, droit);
		preparedStatement.setString(2, username);
		preparedStatement.execute();
		preparedStatement.close();
		return true;
	} catch (Exception e) {
		// TODO: handle exception
	}
	return false;
}

/**
 * permet d'inserer les donnees pour l'affichage dans la BDD
 */
public boolean insertionDonneeAffichage(Table_donnees_affichage donneeAffichage) {
	try {
		String request = "INSERT INTO table_donnee_affichage (friction, roll_speed, sigma, nom_poste, erreur) VALUES (? , ? , ?, ?, ?)";
		PreparedStatement preparedStatement = conn.prepareStatement(request); // protection injection SQK
		preparedStatement.setDouble(1, donneeAffichage.getFriction());
		preparedStatement.setDouble(2, donneeAffichage.getRolling_speed());
		preparedStatement.setDouble(3, donneeAffichage.getSigma());
		preparedStatement.setString(4, donneeAffichage.getNomPoste());
		preparedStatement.setString(5, donneeAffichage.getErreur());
		preparedStatement.execute();
		preparedStatement.close();
		return true;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return false;
}



/**
 * rend un array list avec toutes les données d'affichage
 */
public ArrayList<Table_donnees_affichage> recupererDonneesAffichage(String nomPoste) {
	ArrayList<Table_donnees_affichage> listeDonneesAffichage = new ArrayList<>();
	try {
		String request = "SELECT * FROM table_donnee_affichage WHERE nom_poste = '" + nomPoste
				+ "' ORDER BY id ASC";
		Statement statement = conn.createStatement();
		if (statement.execute(request)) {
			ResultSet resultSet = statement.getResultSet();
			while (resultSet.next()) {
				Table_donnees_affichage donneeAffichage = new Table_donnees_affichage(resultSet.getDouble(2),
						resultSet.getDouble(3), resultSet.getDouble(4), resultSet.getString(5),
						resultSet.getString(6));
				listeDonneesAffichage.add(donneeAffichage);
			}
		}
	} catch (Exception e) {
		System.err.println(e.getClass().getName() + ": " + e.getMessage());
	}
	return listeDonneesAffichage;
}


/**
 * CREER LA DONNEE AFFICHAGE
 */
public  Table_donnees_affichage recupererDerniereDonneeAffichage(String nomPoste) {
	Table_donnees_affichage derniereDonneeAffichage = null;
	try {
		Statement statement = conn.createStatement();
		String request = "SELECT file_format.xtime,file_format.work_roll_speed, "
				+ "csv_output_data.sigma_ini, csv_output_data.friction, csv_output_data.error "
				+ "FROM file_format " + "INNER JOIN csv_output_data "
				+ "ON file_format.id = csv_output_data.id "
				+ "WHERE file_format.nom_poste = '" + nomPoste + "' "
				+ "ORDER BY c.id DESC LIMIT 1";
		if (statement.execute(request)) {
			ResultSet resultSet = statement.getResultSet();
			while (resultSet.next()) {
				derniereDonneeAffichage = new Table_donnees_affichage(resultSet.getDouble(2), resultSet.getDouble(3),
						resultSet.getDouble(4), nomPoste, resultSet.getString(5));
			}
		}
	} catch (Exception e) {
		System.err.println(e.getClass().getName() + ": " + e.getMessage());
	}
	return derniereDonneeAffichage;
}


public static ConnectionBDD getInstance() {
	if (obj == null)
		obj = new ConnectionBDD();
	return obj;
}
}

