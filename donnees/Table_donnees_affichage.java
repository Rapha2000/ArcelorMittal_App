package donnees;

public class Table_donnees_affichage {

	
	
	private double rolling_speed;
	private double sigma;
	private double friction;
	private String nomPoste;
	private String erreur;
	
	
	public Table_donnees_affichage(double rolling_speed, double friction, double sigma,
			String nomPoste, String erreur) {
		super();
		this.rolling_speed = rolling_speed;
		this.sigma = sigma;
		this.friction = friction;
		this.nomPoste = nomPoste;
		this.erreur = erreur;
	}



	public double getRolling_speed() {
		return rolling_speed;
	}


	public void setRolling_speed(double rolling_speed) {
		this.rolling_speed = rolling_speed;
	}


	public double getSigma() {
		return sigma;
	}


	public void setSigma(double sigma) {
		this.sigma = sigma;
	}


	public double getFriction() {
		return friction;
	}


	public void setFriction(double friction) {
		this.friction = friction;
	}


	public String getNomPoste() {
		return nomPoste;
	}


	public void setNomPoste(String nomPoste) {
		this.nomPoste = nomPoste;
	}


	public String getErreur() {
		return erreur;
	}


	public void setErreur(String erreur) {
		this.erreur = erreur;
	}
	
	
	
	
}
