package donnees;

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
	
	
	
}
