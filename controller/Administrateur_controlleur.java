package Controller;

import java.util.ArrayList;
import javafx.scene.control.Button;
import donnees.Users;

public class Administrateur_controlleur implements EventHandler<Event> {

	
	private String nomUtilisateur;
	private int id;
	private static Users user;
	private ConnectionBDD dao;
	private VueAdminConfig vue;
	
	
	public Administrateur_controlleur() {
		this.vue = VueAdminConfig.getInstance();
		dao = ConnectionBDD.getInstance();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructeur pour vue
	 */
	public Administrateur_controlleur(String nomUtilisateur, int id) {
		// TODO Auto-generated constructor stub
		this.vue = VueAdminConfig.getInstance();
		this.nomUtilisateur = nomUtilisateur;
		this.id = id;
		dao = ConnectionBDD.getInstance();
	}
	
	/**
	 * Affichage des utilisateurs sur la vue 
	 */
	public void show(Users user) {
		// TODO Auto-generated method stub
		this.user = user;
		ArrayList<Users> listUsers = dao.recupererUtilisateurs();
		vue.setVue(user, listUsers);
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
		public void handle(Event event) {
			// TODO Auto-generated method stub
			final Button butt = (Button) event.getSource();
			final String typeBouton = butt.getText();
			switch (typeBouton) {
			case "back":
				vue.moveToMenu(user);
				break;
			case "disconnect":
				vue.moveToLogin();
				break;
			case "Add":
				if (!(verifyPassword(vue.getPswData()) && verifyPassword(vue.getUsrData()))) {
					vue.setInfoText("le mot de passe que vous avez saisi ne respecte pas les contraintes");
				} else {
					if (vue.getPswData().equals(vue.getPswConf())) {
						if (!dao.UtilisateurExiste(vue.getUsrData())) {
							dao.creerNouvelUtilisateur(vue.getUsrData(), vue.getPswData(), vue.getAdmin())
							vue.close();
							show(user);
						} else
							vue.setInfoText("L'utilisateur existe déjà ");
					} else
						vue.setInfoText("Les mots de passe fournis ne sont pas identiques");
				}
				break;
			case "update":
				if (dao.UtilisateurExiste(nomUtilisateur) ){
					int newId;
					if (id == 0)
						newId = 1;
					else
						newId= 0;
					dao.miseAJourDroits(nomUtilisateur, newId);
					vue.close();
					show(user);
				}
				break;
			case "delete":
				if (dao.UtilisateurExiste(nomUtilisateur)) {
					dao.supprimerUtilisateur(nomUtilisateur);
					vue.close();
					show(user);
				}
				break;
			default:
				break;
			}
		}

	
}
