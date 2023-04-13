package donnees;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DonneesSortieCapteurs {
	
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
	private double LUBWFLUP;	
	private double LUBWFLLO;	
	private double LUBOILFLUP;	
	private double LUBOILFLLO;	 
	private double work_roll_speed;
	
	
	
	

	public DonneesSortieCapteurs(int lp, int matID, double xTime, double xLoc, double enThick, double exThick, double enTens, double exTens,
			double rollForce, double fSlip, double daiameter, double rolledLengthWorkRolls, double youngModulus, double backuprolldia,
			double rolledlengthforBackuprolls, double mu, double torque, double averageSigma, double inputError, double lubWFlUp,
			double lubWFlLo, double lubOilFlUp, double lubOilFlLo, double work_roll_speed, String nomPoste) {
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
		this.LUBWFLUP = lubWFlUp;
		this.LUBWFLLO = lubWFlLo;
		this.LUBOILFLUP = lubOilFlUp;
		this.LUBOILFLLO = lubOilFlLo;
		this.work_roll_speed = work_roll_speed;
		this.nomPoste = nomPoste;
	}





	public String getNomPoste() {
		return nomPoste;
	}





	public void setNomPoste(String nomPoste) {
		this.nomPoste = nomPoste;
	}





	public int getLp() {
		return lp;
	}





	public void setLp(int lp) {
		this.lp = lp;
	}





	public int getMatID() {
		return matID;
	}





	public void setMatID(int matID) {
		this.matID = matID;
	}





	public double getxTime() {
		return xTime;
	}





	public void setxTime(double xTime) {
		this.xTime = xTime;
	}





	public double getxLoc() {
		return xLoc;
	}





	public void setxLoc(double xLoc) {
		this.xLoc = xLoc;
	}





	public double getEnThick() {
		return enThick;
	}





	public void setEnThick(double enThick) {
		this.enThick = enThick;
	}





	public double getExThick() {
		return exThick;
	}





	public void setExThick(double exThick) {
		this.exThick = exThick;
	}





	public double getEnTens() {
		return enTens;
	}





	public void setEnTens(double enTens) {
		this.enTens = enTens;
	}





	public double getExTens() {
		return exTens;
	}





	public void setExTens(double exTens) {
		this.exTens = exTens;
	}





	public double getRollForce() {
		return rollForce;
	}





	public void setRollForce(double rollForce) {
		this.rollForce = rollForce;
	}





	public double getfSlip() {
		return fSlip;
	}





	public void setfSlip(double fSlip) {
		this.fSlip = fSlip;
	}





	public double getDaiameter() {
		return daiameter;
	}





	public void setDaiameter(double daiameter) {
		this.daiameter = daiameter;
	}





	public double getRolledLengthWorkRolls() {
		return rolledLengthWorkRolls;
	}





	public void setRolledLengthWorkRolls(double rolledLengthWorkRolls) {
		this.rolledLengthWorkRolls = rolledLengthWorkRolls;
	}





	public double getYoungModulus() {
		return youngModulus;
	}





	public void setYoungModulus(double youngModulus) {
		this.youngModulus = youngModulus;
	}





	public double getBackuprolldia() {
		return backuprolldia;
	}





	public void setBackuprolldia(double backuprolldia) {
		this.backuprolldia = backuprolldia;
	}





	public double getRolledlengthforBackuprolls() {
		return rolledlengthforBackuprolls;
	}





	public void setRolledlengthforBackuprolls(double rolledlengthforBackuprolls) {
		this.rolledlengthforBackuprolls = rolledlengthforBackuprolls;
	}





	public double getMu() {
		return mu;
	}





	public void setMu(double mu) {
		this.mu = mu;
	}





	public double getTorque() {
		return torque;
	}





	public void setTorque(double torque) {
		this.torque = torque;
	}





	public double getAverageSigma() {
		return averageSigma;
	}





	public void setAverageSigma(double averageSigma) {
		this.averageSigma = averageSigma;
	}





	public double getInputError() {
		return inputError;
	}





	public void setInputError(double inputError) {
		this.inputError = inputError;
	}




















	public double getLUBWFLUP() {
		return LUBWFLUP;
	}





	public void setLUBWFLUP(double lUBWFLUP) {
		LUBWFLUP = lUBWFLUP;
	}





	public double getLUBWFLLO() {
		return LUBWFLLO;
	}





	public void setLUBWFLLO(double lUBWFLLO) {
		LUBWFLLO = lUBWFLLO;
	}





	public double getLUBOILFLUP() {
		return LUBOILFLUP;
	}





	public void setLUBOILFLUP(double lUBOILFLUP) {
		LUBOILFLUP = lUBOILFLUP;
	}





	public double getLUBOILFLLO() {
		return LUBOILFLLO;
	}





	public void setLUBOILFLLO(double lUBOILFLLO) {
		LUBOILFLLO = lUBOILFLLO;
	}





	public double getWork_roll_speed() {
		return work_roll_speed;
	}





	public void setWork_roll_speed(double work_roll_speed) {
		this.work_roll_speed = work_roll_speed;
	}
	
	public String toString() {
		DecimalFormatSymbols mySymbol = new DecimalFormatSymbols();
		mySymbol.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("0.000", mySymbol);
		return  lp + "," + matID + "," + df.format(xTime) + ", " + df.format(xLoc) + ", "
				+ df.format(enThick) + ", " + df.format(exThick) + ", " + df.format(enTens) + ", " + df.format(exTens) + ", "
				+ df.format(rollForce) + ", " + df.format(fSlip) + ", " + df.format(daiameter) + ", "
				+ df.format(rolledLengthWorkRolls) + ", " + df.format(youngModulus) + ", " + df.format(backuprolldia)
				+ ", " + df.format(rolledlengthforBackuprolls) + ", " + df.format(mu) + ", " + df.format(torque)
				+ ", " + df.format(averageSigma) + ", " + df.format(inputError) + ", " + df.format(LUBWFLUP)
				+ ", " + df.format(LUBWFLLO) + ", " + df.format(LUBOILFLUP) + ", " + df.format(LUBOILFLLO)
				+ ", " + df.format(work_roll_speed) + ", '" + nomPoste + "'";
	}

	

}

