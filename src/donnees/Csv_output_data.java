package donnees;


/** Cette classe représente les données qui ressortent de Orowan **/

public class Csv_output_data {

	private String nomPoste;
	private int cas;
	private String errors;
	private double offsetYield;
	private double friction;
	private double rolling_Torque;
	private double sigma_Moy;
	private double sigma_Ini;
	private double sigma_Out;
	private double sigma_Max;
	private double force_Error;
	private double slip_Error;
	private String has_Converged;
	
	
	
	public Csv_output_data(String nomPoste, int cas, String errors, double offsetYield, double friction,
			double rolling_Torque, double sigma_Moy, double sigma_Ini, double sigma_Out, double sigma_Max,
			double force_Error, double slip_Error, String has_Converged) {
		super();
		this.nomPoste = nomPoste;
		this.cas = cas;
		this.errors = errors;
		this.offsetYield = offsetYield;
		this.friction = friction;
		this.rolling_Torque = rolling_Torque;
		this.sigma_Moy = sigma_Moy;
		this.sigma_Ini = sigma_Ini;
		this.sigma_Out = sigma_Out;
		this.sigma_Max = sigma_Max;
		this.force_Error = force_Error;
		this.slip_Error = slip_Error;
		this.has_Converged = has_Converged;
	}



	public String getNomPoste() {
		return nomPoste;
	}



	public void setNomPoste(String nomPoste) {
		this.nomPoste = nomPoste;
	}



	public int getCas() {
		return cas;
	}



	public void setCas(int cas) {
		this.cas = cas;
	}



	public String getErrors() {
		return errors;
	}



	public void setErrors(String errors) {
		this.errors = errors;
	}



	public double getOffsetYield() {
		return offsetYield;
	}



	public void setOffsetYield(double offsetYield) {
		this.offsetYield = offsetYield;
	}



	public double getFriction() {
		return friction;
	}



	public void setFriction(double friction) {
		this.friction = friction;
	}



	public double getRolling_Torque() {
		return rolling_Torque;
	}



	public void setRolling_Torque(double rolling_Torque) {
		this.rolling_Torque = rolling_Torque;
	}



	public double getSigma_Moy() {
		return sigma_Moy;
	}



	public void setSigma_Moy(double sigma_Moy) {
		this.sigma_Moy = sigma_Moy;
	}



	public double getSigma_Ini() {
		return sigma_Ini;
	}



	public void setSigma_Ini(double sigma_Ini) {
		this.sigma_Ini = sigma_Ini;
	}



	public double getSigma_Out() {
		return sigma_Out;
	}



	public void setSigma_Out(double sigma_Out) {
		this.sigma_Out = sigma_Out;
	}



	public double getSigma_Max() {
		return sigma_Max;
	}



	public void setSigma_Max(double sigma_Max) {
		this.sigma_Max = sigma_Max;
	}



	public double getForce_Error() {
		return force_Error;
	}



	public void setForce_Error(double force_Error) {
		this.force_Error = force_Error;
	}



	public double getSlip_Error() {
		return slip_Error;
	}



	public void setSlip_Error(double slip_Error) {
		this.slip_Error = slip_Error;
	}



	public String getHas_Converged() {
		return has_Converged;
	}



	public void setHas_Converged(String has_Converged) {
		this.has_Converged = has_Converged;
	}
	
	/** cette méthode remplace la méthode toString définie par défaut dans la classe Object **/
	public String toString() {
		return  cas + ", '" + errors + "', " + offsetYield + ", "
				+ friction + ", " + rolling_Torque + ", " + sigma_Moy + ", "
				+ sigma_Ini + ", " + sigma_Out + ", " + sigma_Max + ", " + force_Error
				+ ", " + slip_Error + ", '" + has_Converged +"'" + ", '" + nomPoste + "'";
	}
	
	
	
	
}
