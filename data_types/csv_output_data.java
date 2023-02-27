package donnees;

public class csv_output_data {

	private String nomPoste;
	private int cas;
	private String Errors;
	private double OffsetYield;
	private double Friction;
	private double Rolling_Torque;
	private double Sigma_Moy;
	private double Sigma_Ini;
	private double Sigma_Out;
	private double Sigma_Max;
	private double Force_Error;
	private double Slip_Error;
	private String Has_Converged;
	
	
	
	public csv_output_data(String nomPoste, int cas, String errors, double offsetYield, double friction,
			double rolling_Torque, double sigma_Moy, double sigma_Ini, double sigma_Out, double sigma_Max,
			double force_Error, double slip_Error, String has_Converged) {
		super();
		this.nomPoste = nomPoste;
		this.cas = cas;
		Errors = errors;
		OffsetYield = offsetYield;
		Friction = friction;
		Rolling_Torque = rolling_Torque;
		Sigma_Moy = sigma_Moy;
		Sigma_Ini = sigma_Ini;
		Sigma_Out = sigma_Out;
		Sigma_Max = sigma_Max;
		Force_Error = force_Error;
		Slip_Error = slip_Error;
		Has_Converged = has_Converged;
	}
	
	
	
}
