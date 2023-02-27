package donnees;

public class table_donnees_affichage {

	
	private double timestamp;
	private double rolling_speed;
	private double sigma;
	private double friction;
	private String nomPoste;
	private String erreur;
	
	
	public table_donnees_affichage(double timestamp, double rolling_speed, double sigma, double friction,
			String nomPoste, String erreur) {
		super();
		this.timestamp = timestamp;
		this.rolling_speed = rolling_speed;
		this.sigma = sigma;
		this.friction = friction;
		this.nomPoste = nomPoste;
		this.erreur = erreur;
	}
	
	
	
	
}
