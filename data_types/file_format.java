package donnees;

public class file_format {
	
	private String nomPoste;
	private int lp ;
	private int matID;
	private double xTime;
	private double xLoc;
	private double enThick;
	private double exThick;
	private double enTens;
	private double exTens;
	private double rollForce;
	private double fSlip;
	private double daiameter;
	private double rolledLengthWorkRolls;
	private double youngModulus;
	private double backuprolldia;
	private double rolledlengthforBackuprolls;
	private double mu;
	private double torque;	
	private double averageSigma;	
	private double inputError;	
	private double lubWFlUp;	
	private double lubWFlLo;	
	private double lubOilFlUp;	
	private double lubOilFlLo;	 
	private double work_roll_speed;
	
	
	
	
	public file_format(String nomPoste, int lp, int matID, double xTime, double xLoc, double enThick, double exThick,
			double enTens, double exTens, double rollForce, double fSlip, double daiameter,
			double rolledLengthWorkRolls, double youngModulus, double backuprolldia, double rolledlengthforBackuprolls,
			double mu, double torque, double averageSigma, double inputError, double lubWFlUp, double lubWFlLo,
			double lubOilFlUp, double lubOilFlLo, double work_roll_speed) {
		super();
		this.nomPoste = nomPoste;
		this.lp = lp;
		this.matID = matID;
		this.xTime = xTime;
		this.xLoc = xLoc;
		this.enThick = enThick;
		this.exThick = exThick;
		this.enTens = enTens;
		this.exTens = exTens;
		this.rollForce = rollForce;
		this.fSlip = fSlip;
		this.daiameter = daiameter;
		this.rolledLengthWorkRolls = rolledLengthWorkRolls;
		this.youngModulus = youngModulus;
		this.backuprolldia = backuprolldia;
		this.rolledlengthforBackuprolls = rolledlengthforBackuprolls;
		this.mu = mu;
		this.torque = torque;
		this.averageSigma = averageSigma;
		this.inputError = inputError;
		this.lubWFlUp = lubWFlUp;
		this.lubWFlLo = lubWFlLo;
		this.lubOilFlUp = lubOilFlUp;
		this.lubOilFlLo = lubOilFlLo;
		this.work_roll_speed = work_roll_speed;
	}
	
	

}
