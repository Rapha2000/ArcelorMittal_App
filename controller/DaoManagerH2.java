package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class DaoManagerH2 {
	
	private static DaoManagerH2 instance = null;
	private Connection conn = null;
	
	private DaoManagerH2() {
		try {
			// Informations de connexion à la base de données H2
		    String jdbcUrl = "jdbc:h2:&~/test";
		    String username = "sa";
		    String password = "sa";
		    conn = DriverManager.getConnection(jdbcUrl, username, password);
		} catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Connection to H2 db succeeded");
	
	}

	
    public static DaoManagerH2 getInstance() {
    	if (instance == null)
			instance = new DaoManagerH2();
		return instance;
    }
    
    /**
	 * Méthode qui permet de savoir si un utilisateur existe dans la bdd
	 */
	public boolean userExists(String username) {
		
		boolean exists = false;
		
		try {
			String sql_query = "SELECT * FROM users WHERE username = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql_query); 
			preparedStatement.setString(1, username); // remplace le premier ? par la valeur username dans la requête
			ResultSet resultSet = preparedStatement.executeQuery();
			//si le résultat de la requete n'est pas vide, l'utilisateur existe déjà
			while (resultSet.next()) { 
				exists = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return exists;
	}
	
	/**
	 * Méthode qui permet d'ajouter un user à la bdd s'il n'existe pas déjà
	 */
    public void ajouterUser(String username, String password, boolean isAdmin) {
    	try {
    		if (!userExists(username)) {
    			String sql_query = "INSERT INTO users (username, password, admin) VALUES (? , ? , ?)";
				PreparedStatement preparedStatement = conn.prepareStatement(sql_query);
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, password);
				preparedStatement.setBoolean(3, isAdmin);
				preparedStatement.execute();
				preparedStatement.close();
    		}
    	} catch (Exception e) {
    			e.printStackTrace();
    	}
    
    }
    
    
    
    
}

