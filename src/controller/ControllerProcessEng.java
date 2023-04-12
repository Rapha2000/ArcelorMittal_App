package controller;

import java.util.ArrayList;

import view.ViewProcessEng;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import donnees.Users;

public class ControllerProcessEng implements EventHandler<ActionEvent> {

	
	private String nomUtilisateur;
	private int id;
	private static Users user;
	private ConnectionBDD dao;
	private ViewProcessEng vue;
	
	
	public ControllerProcessEng() {
		this.vue = ViewProcessEng.getInstanceViewProcessEng();
		dao = ConnectionBDD.getInstance();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructeur pour vue
	 */
	public ControllerProcessEng(String nomUtilisateur, int id) {
		// TODO Auto-generated constructor stub
		this.vue = ViewProcessEng.getInstanceViewProcessEng();
		this.nomUtilisateur = nomUtilisateur;
		this.id = id;
		dao = ConnectionBDD.getInstance();
	}
	
	/**
	 * Affichage des utilisateurs sur la vue 
	 */
	public void show(Users user) {
		// TODO Auto-generated method stub
		ControllerProcessEng.user = user;
		ArrayList<Users> listUsers = dao.recupererUtilisateurs();
		vue.setViewProcessEng(); //truc à faire
	}
	
	
	//verification que le password respecte le pattern
	public static boolean verifyPassword(String password) {
	    // Définir le pattern du mot de passe
	    String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	    
	    // Vérifier si le mot de passe respecte le pattern
	    return password.matches(pattern);
	}
		
		

		/**
		 * Gestion des boutons
		 */
		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			final Button butt = (Button) event.getSource();
			final String typeBouton = butt.getText();
			switch (typeBouton) {
			case "Logout":
				vue.moveToMenu();
				break;
			case "Add":
				if (!(verifyPassword(vue.getPasswordData()) && verifyPassword(vue.getUserData()))) {
					vue.setInfoText("le mot de passe que vous avez saisi ne respecte pas les contraintes");
				} else {
					if (vue.getPasswordData().equals(vue.getPasswordData())) {
						if (!dao.utilisateurExiste(vue.getUserData())) {
							dao.creerNouvelUtilisateur(vue.getUserData(), vue.getPasswordData(), vue.getAdmin());
							vue.closeStageProcessEng();
							show(user);
						} else
							vue.setInfoText("L'utilisateur existe déjà ");
					} else
						vue.setInfoText("Les mots de passe fournis ne sont pas identiques");
				}
				break;
			case "Update":
				System.out.println("caca"+ id);
				//ici
				if (dao.utilisateurExiste(nomUtilisateur) ){
					int newId;
					System.out.println("caca"+ id);
					if (id == 0)
						newId = 1;
					else
						newId= 0;
					dao.miseAJourDroits(nomUtilisateur, newId);
					vue.closeStageProcessEng();
					show(user);
				}
				break;
			case "Remove":
				if (dao.utilisateurExiste(nomUtilisateur)) {
					dao.supprimerUtilisateur(nomUtilisateur);
					vue.closeStageProcessEng();
					show(user);
				}
				break;
			default:
				break;
			}
		}	
}
