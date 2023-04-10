package donnees;

public class Csv_input_data {
	
	private String nomPoste;
	private int cas;
	private double he;
	private double hs;
	private double te;
	private double ts;
	private double diamWR;
	private double wRYoung;
	private double offset;
	private double mu_ini;
	private double force;
	private double g;

	public Csv_input_data(
			int cas, double he, double hs, 
			double te, double ts, double diamWR,
			double wRYoung, double offset,
			double mu_ini, double force, double g,
			String nomPoste) {
		
		this.cas = cas;
		this.he = he;
		this.hs = hs;
		this.te = te;
		this.ts = ts;
		this.diamWR = diamWR;
		this.wRYoung = wRYoung;
		this.offset = offset;
		this.mu_ini = mu_ini;
		this.force = force;
		this.g = g;
		this.nomPoste = nomPoste;
	}
	

}
