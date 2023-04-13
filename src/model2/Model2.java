package model2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Model2 implements java.beans.PropertyChangeListener{
	private ArrayList<Orowan> orowan;
	private ArrayList<Simulation> simulation;
	private static Model2 model2;
	private PropertyChangeSupport pcs;
	
	/**
	 * Instancie la simulation et le module orowan
	 */
	private Model2() {
		pcs = new PropertyChangeSupport(this);
		simulation = new ArrayList<>();
	    simulation.add(new Simulation("1939351_F2.txt"));
		simulation.add(new Simulation("1939351_F3.txt"));
		orowan = new ArrayList<>();
		orowan.add(new Orowan("1939351_F2.txt"));
		orowan.add(new Orowan("1939351_F3.txt"));
		orowan.get(0).addPropertyChangeListener(this);
		orowan.get(1).addPropertyChangeListener(this);
	}
	
	public static Model2 getModel() {
		if (model2 == null) {
			model2 = new Model2();
		}
		return model2;
	}
	
	public void startSimulation() {
		System.out.println("starting Threads");
		simulation.get(0).start(); // Reset les tables et lance le remplissage de la bdd
		simulation.get(1).start(); // Reset les tables et lance le remplissage de la bdd
		orowan.get(0).start();
		orowan.get(1).start();
	}
	
	public ArrayList<String> getPostesName(){
		ArrayList<String> nomPostes = new ArrayList<>();
		for(int i = 0; i < orowan.size(); i++) {
			nomPostes.add(orowan.get(i).getNomPoste());
		}
		return nomPostes;
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();

		//System.out.println(propName);
		switch (propName) {
		case "newData":
			pcs.firePropertyChange("newData", null, null);
			break;
		case "OrowanComputeTime" :
			pcs.firePropertyChange("OrowanComputeTime", evt.getOldValue(), evt.getNewValue());
			break;
		default:
			break;
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }
	
	public void stopSim() {
		// TODO Auto-generated method stub
		simulation.get(0).setIsTerminated(); // Reset les tables et lance le remplissage de la bdd
		simulation.get(1).setIsTerminated(); // Reset les tables et lance le remplissage de la bdd
		orowan.get(0).setIsTerminated();
		orowan.get(1).setIsTerminated();
		
		try {
			simulation.get(0).join();
			simulation.get(1).join();
			orowan.get(0).join();
			orowan.get(1).join();
			recreateThreads();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void recreateThreads() {
		// TODO Auto-generated method stub
				simulation = new ArrayList<>();
				simulation.add(new Simulation("1939351_F2.txt"));
				simulation.add(new Simulation("1939351_F3.txt"));
				orowan = new ArrayList<>();
				orowan.add(new Orowan("1939351_F2.txt"));
				orowan.add(new Orowan("1939351_F3.txt"));
				orowan.get(0).addPropertyChangeListener(this);
				orowan.get(1).addPropertyChangeListener(this);
	}
	

}
