package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Model {
	
	private ArrayList<Orowan> orowan;
	private ArrayList<SensorDataHandler> sensorDataHandler;
	private static Model model;
	private PropertyChangeSupport pcs;

	/*
	 * private constructor that initializes the sensorDataHandler and orowan objects.
	 */
	private Model() {
		pcs = new PropertyChangeSupport(this);
		
		sensorDataHandler = new ArrayList<>();
		sensorDataHandler.add(new SensorDataHandler("1939351_F2.txt"));
		sensorDataHandler.add(new SensorDataHandler("1939351_F3.txt"));
		
		orowan = new ArrayList<>();
	}
	
	/*
	 * static method that returns the model instance. 
	 */
	public static Model getModel() {
		return model;
	}
	
	/*
	 * method that starts the simulation by calling the start() method 
	 * of the sensorDataHandler and orowan objects.
	 */
	public void launchSimulation() {
		sensorDataHandler.get(0).store();
		sensorDataHandler.get(1).store();
	}

	/*
	 * method that returns an ArrayList of strings containing the names of the orowan objects.
	 */
	public ArrayList<String> getPostesNames(){
		ArrayList<String> postes = new ArrayList<>();
		
		for(int i = 0; i < orowan.size(); i++) {
			postes.add(orowan.get(i).getNomPoste());
		}
		
		return postes;
	}
	
	/*
	 * method that receives property change events from other components and fires property 
	 * change events to its listeners using the pcs object.
	 */
	public void propertyChange(PropertyChangeEvent e) {
		String propName = e.getPropertyName();

		//System.out.println(propName);
		switch (propName) {
		case "newData":
			pcs.firePropertyChange("newData", null, null);
			break;
		case "OrowanComputeTime" :
			pcs.firePropertyChange("OrowanComputeTime", e.getOldValue(), e.getNewValue());
			break;
		default:
			break;
		}
	}
	
	/*
	 * method that adds a listener to the pcs object.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	/*
	 * method that removes a listener from the pcs object.
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
			
}
