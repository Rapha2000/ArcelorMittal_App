package controller;

import java.sql.Connection;
import java.sql.DriverManager;
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
	
}


