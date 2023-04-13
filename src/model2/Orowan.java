package model2;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;

import controller.ConnectionBDD;
import donnees.Table_donnees_affichage;
import donnees.Csv_input_data;
import donnees.DonneesSortieCapteurs;
import donnees.Csv_output_data;
import donnees.Table_donnees_affichage;

public class Orowan extends Thread{
	
	private LinkedList<Table_donnees_affichage> linkedListAffichage;
	private ConnectionBDD daoManagerH2;
	private String nomPoste;
	private PropertyChangeSupport pcs;
	private boolean flagInterupt;

	public Orowan(String nomPoste) {
		linkedListAffichage = new LinkedList<>();
		this.nomPoste = nomPoste;
		daoManagerH2 = ConnectionBDD.getInstance();
		pcs = new PropertyChangeSupport(this);
		//System.out.println(nomPoste);
	}
	
	public Table_donnees_affichage returnMeanOfDataOut() {
		ArrayList<Table_donnees_affichage> uniqueList = new ArrayList<>();
		while (linkedListAffichage.size() != 0) {
			Table_donnees_affichage tester = linkedListAffichage.pop();
			if (!linkedListAffichage.contains(tester) && !uniqueList.contains(tester)) {
				uniqueList.add(tester);
			}
		}
		// System.out.println(uniqueList.size());
		double sigma = 0;
		double rollSpeed = 0;
		double friction = 0;
		int nbrUnique = 0;
		String error = "VOID";
		Table_donnees_affichage donneeAffichage;
		// System.out.println(uniqueList.toString());
		for (Table_donnees_affichage mAffichage : uniqueList) {
			if (mAffichage.getErreur().equals("VOID")) {
				sigma += mAffichage.getSigma();
				friction += mAffichage.getFriction();
				nbrUnique++;
			} else
				error = mAffichage.getErreur();
			rollSpeed += mAffichage.getRolling_speed();
		}
		if (nbrUnique == 0)
			nbrUnique = 1;
		sigma /= nbrUnique;
		friction /= nbrUnique;
		donneeAffichage = new Table_donnees_affichage(rollSpeed / uniqueList.size(), sigma, friction,
				uniqueList.get(0).getNomPoste(), error);
		// System.out.println(donneeAffichage.toString());
		pcs.firePropertyChange("newData", null, null);
		return (donneeAffichage);
	}
	
	

	
	public void runCmd() throws Exception {
		long start = System.currentTimeMillis();
		String cmd = "\"C:\\Users\\andes\\OneDrive\\Documents\\IMT Mines Alès\\2A\\cours 2IA\\PROJET GL\\Fichiers\\Model\\Orowan_x64.exe.exe\""; // la commande à exec

		ProcessBuilder processBuilder = new ProcessBuilder(cmd); // Prépare le lancement de CMD avec la ma commande

		Process process = processBuilder.start(); // lance le process

		DataOutputStream writer = new DataOutputStream(process.getOutputStream()); // on setup l'ecriture et la lecture
																					// de l'invite de commande
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
		BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

		// Démarche a suivre d'après le PDF
		try {
			// System.out.println("i\n");
			writer.writeBytes("i\n");
			writer.flush();

			// System.out.println("c\n");
			writer.writeBytes("c\n");
			writer.flush();

			// System.out.println("./Rugo/Ressources/orowan/inv_cst.txt\n");
			writer.writeBytes("C:/Users/andes/OneDrive/Documents/IMT Mines Alès/2A/cours 2IA/PROJET GL/Fichiers/Orowan/" + nomPoste + "\n");
			writer.flush();

			// System.out.println("./Rugo/Ressources/orowan/output.txt\n");
			writer.writeBytes("C:/Users/andes/OneDrive/Documents/IMT Mines Alès/2A/cours 2IA/PROJET GL/Fichiers/Orowan/output_" + nomPoste + "\n");
			writer.flush();

			process.waitFor();
			pcs.firePropertyChange("OrowanComputeTime", nomPoste, System.currentTimeMillis() - start);

			process.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Csv_input_data convertDonneeCapteurs(DonneesSortieCapteurs donneeOutCapteur) {
		Csv_input_data donneeInOrowan = new Csv_input_data(1, donneeOutCapteur.getEnThick(),
				donneeOutCapteur.getExThick(), donneeOutCapteur.getEnTens(), donneeOutCapteur.getExTens(),
				donneeOutCapteur.getDaiameter(), donneeOutCapteur.getYoungModulus(), donneeOutCapteur.getAverageSigma(),
				donneeOutCapteur.getMu(), donneeOutCapteur.getRollForce(), donneeOutCapteur.getfSlip(),
				donneeOutCapteur.getNomPoste());
		return donneeInOrowan;
	}
	
	public Csv_output_data readOneDataOutFromFile() {

		BufferedReader br = null;
		Csv_output_data donneeOutOrowan = null;

		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader("C:/Users/andes/OneDrive/Documents/IMT Mines Alès/2A/cours 2IA/PROJET GL/Fichiers/Orowan/output_" + nomPoste));// file name with
																									// path
			int flagFirstLine = 1;
			while ((sCurrentLine = br.readLine()) != null) {
				if (flagFirstLine == 1) {
					flagFirstLine = 0;
				} else {
					String[] lineOfData = sCurrentLine.split("\t");

					NumberFormat format = NumberFormat.getInstance();

					try {
						donneeOutOrowan = new Csv_output_data(nomPoste, format.parse(lineOfData[0]).intValue(), lineOfData[1],
								format.parse(lineOfData[2]).doubleValue(), format.parse(lineOfData[3]).doubleValue(),
								format.parse(lineOfData[4]).doubleValue(), format.parse(lineOfData[5]).doubleValue(),
								format.parse(lineOfData[6]).doubleValue(), format.parse(lineOfData[7]).doubleValue(),
								format.parse(lineOfData[8]).doubleValue(), format.parse(lineOfData[9]).doubleValue(),
								format.parse(lineOfData[10]).doubleValue(), lineOfData[11]);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		// System.out.println(donneeOutOrowan.toString());
		return donneeOutOrowan;
	}
	
	
	
	public boolean writeOneDataInToFile(Csv_input_data data) {
		try {
			FileWriter myWriter = new FileWriter("C:/Users/andes/OneDrive/Documents/IMT Mines Alès/2A/cours 2IA/PROJET GL/Fichiers/Orowan/" + nomPoste);
			myWriter.write("Cas	He	Hs	Te	Ts	Diam_WR	WRyoung	offset ini	mu_ini	Force	G\n");
			myWriter.write(data.toStringnoNomPoste().replace(',', '\t'));
			myWriter.close();
		} catch (Exception e) {
			return true;
		}
		return false; // success
	}
	
	public void run() {
		try {
			flagInterupt = false;
			sleep(1000); // Simulation On a deja reçu des données
			long sleepTime = 1;
			while (!flagInterupt) {
				if (sleepTime > 0)
					sleep(sleepTime);
				else
					System.out.println("ON EST EN RETARD : " + sleepTime);
				long startTime = System.currentTimeMillis();
				DonneesSortieCapteurs donneeOutCapteur = daoManagerH2.recupererDerniereDonneeSortieCapteurs(nomPoste);// recup
				Csv_input_data donneeInOrowan = convertDonneeCapteurs(donneeOutCapteur);// convrt
				daoManagerH2.insererDonneeEntreeOrowan(donneeInOrowan);// Insert
				writeOneDataInToFile(donneeInOrowan);// ecrit dans file isrt_cst.txt
				runCmd(); // run orowan
				Csv_output_data donneeOutOrowan = readOneDataOutFromFile();
				daoManagerH2.insererDonneeSortieOrowan(donneeOutOrowan);
				if (insertData(daoManagerH2.recupererDerniereDonneeAffichage(nomPoste))) { // on ajoute virtuellement une
																					// donnée, quand on arrive a 5, il
																					// faut lancer la moyenne et reset
																					// l'arrayList
					Table_donnees_affichage donneeAffichage = returnMeanOfDataOut();
					daoManagerH2.insertionDonneeAffichage(donneeAffichage);
				}
				long endTime = System.currentTimeMillis();
				sleepTime = 200 - (endTime - startTime);
			}
			System.out.println("orowan thread just ended");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean insertData(Table_donnees_affichage donneeAffichage) { // J'ai attendu avant Si ça return true il faut r la
		// moyenne et reset la list (Faudra check pour les
		// doublons)
		if (donneeAffichage != null) {
			linkedListAffichage.addFirst(donneeAffichage);
			if (linkedListAffichage.size() >= 5) {
				// System.out.println(LocalTime.now());
				return true;
			}
		}
		return false;
	}


	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public String getNomPoste() {
		return nomPoste;
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	public void setIsTerminated() {
		// TODO Auto-generated method stub
		flagInterupt = true;
	}

}
