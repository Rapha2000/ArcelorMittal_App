package Controller;

import donnees.Users;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.event.Event;

public class Login_controlleur  implements EventHandler<Event>{

	private ConnectionBDD dao ;
	private VueLogin vue;
	
//constructeur sans action
	
	public Login_controlleur(){
		// TODO Auto-generated constructor stub
		dao = ConnectionBDD.getInstance();
		vue = VueLogin.getInstance();
	}


	
//verification que le password respecte le pattern
	public static boolean verifyPassword(String password) {
	    // Définir le pattern du mot de passe
	    String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	    
	    // Vérifier si le mot de passe respecte le pattern
	    return password.matches(pattern);
	}
	
	
	
//gestion des évenements
	
	public void handle(Event event) {
		// TODO Auto-generated method stub
		final Button butt = (Button) event.getSource();
		final String typeBouton = butt.getText();
		String utilisateur = vue.getUsrData();
		String password = vue.getPswData();
		switch (typeBouton) {
		case "Connexion":
			if (verifyPassword(password) && verifyPassword(utilisateur)) {
				System.out.println("valide");
				Users user = dao.existenceEtRole(utilisateur, password);
				int login = user.getId();
				if (login != -1) {
					System.out.println("l'utilisateur existe dans la BDD");
					switch (login) {
					case 0: //a completer 
					case 1:
						vue.moveToMenu(user);
						break;
					default:
						break;
					}
					// connexion OK
				} else {
					System.out.println("utilisateur introuvable");
					vue.setMessageSiProbleme("login ou mot de passe inconnus");
				}
			} else {
				System.out.println("mot de passe ou nom d'utilisateur invalides");
				vue.setMessageSiProbleme("mot de passe ou nom d'utilisateur invalides");
			}
			break;
		default:
			break;
		}
	}

}
