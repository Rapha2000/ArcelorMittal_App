package view;

//import donnee.Table_donnee_affichage;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import controller.ControllerWorker;
import donnees.Table_donnees_affichage;
import controller.ControllerMenu;

public class ViewWorker {
	
	//private long computeTime;
	private static ViewWorker instanceViewWorker = null;
	
	private ControllerWorker controllerWorker;
	private ControllerMenu controllerMenu;
	
	
	//Affichage de la fenetre
	private Stage stageWorker;
	private BorderPane paneWorker;
	private Scene sceneWorker;

	
	//Partie gauche
	private VBox vBox;
	private Label labelStandID;
	private ComboBox<String> comboBoxStandID;
	private HBox hBoxStandID;
	private Label labelComputeTime;
	private Text computeTimeValue;
	private Text frictionCoefValue;
	private long computeTimeData;
	private Label labelFrictionCoef;
	private Label labelValFrictionCoef;
	private HBox hBox;
	private CheckBox checkBoxFriction;
	private CheckBox checkBoxRollSpeed;
	private CheckBox checkBoxSigma;
	private boolean flagFriction;
	private boolean flagRollSpeed;
	private boolean flagSigma;
	
	
	
	//Partie centre (affichage des courbes)	
	private NumberAxis timeAxis;
	private NumberAxis yAxis;
	private XYChart.Series<Number, Number> seriesFriction;
	private XYChart.Series<Number, Number> seriesRollSpeed;
	private XYChart.Series<Number, Number> seriesSigma;
	private ArrayList<Table_donnees_affichage> data;
	
	private Button buttonLogout;
	
	
	private ViewWorker() {
		
	}
	
	public static ViewWorker getInstanceViewWorker() {
		if (instanceViewWorker == null) {
			instanceViewWorker = new ViewWorker();
		}
		return instanceViewWorker;
	}
	
	public void setViewWorker() {
	
		stageWorker = new Stage();
		paneWorker = new BorderPane();
	
		vBox = new VBox();
				
		labelStandID = new Label("Voir le poste : ");
		comboBoxStandID = new ComboBox<>();
		comboBoxStandID.setOnMouseClicked(new ControllerWorker());
		
		hBoxStandID = new HBox();
		
		
		labelComputeTime = new Label("Compute time of Orowan");
		computeTimeValue = new Text();
		
		labelFrictionCoef = new Label("Friction coefficient factor");
		frictionCoefValue = new Text();
		
		hBox = new HBox();
		
		checkBoxFriction = new CheckBox("Friction");
		checkBoxRollSpeed = new CheckBox("Roll Speed");
		checkBoxSigma = new CheckBox("Sigma");
		
		hBox.getChildren().addAll(checkBoxFriction, checkBoxRollSpeed, checkBoxSigma);
		
		vBox.getChildren().addAll(labelStandID, labelComputeTime, computeTimeValue, labelFrictionCoef, frictionCoefValue, hBox);
	
		paneWorker.setLeft(vBox);
		//paneWorker.setCenter();
		
		
		//Initialisation du graphique
		NumberAxis timeAxis = new NumberAxis();
		timeAxis.setLabel("temps (ms)");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("valeur");
				
		//Definition des series de donnees
		seriesFriction = new XYChart.Series<>();
		seriesFriction.setName("Friction");
		seriesRollSpeed = new XYChart.Series<>();
		seriesRollSpeed.setName("Roll Speed");
		seriesSigma = new XYChart.Series<>();
		seriesSigma.setName("Sigma");
		
		//Initialisation des courbes
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(timeAxis, yAxis);
		lineChart.setTitle("Curves");
		lineChart.getData().add(seriesFriction);
		lineChart.getData().add(seriesRollSpeed);
		lineChart.getData().add(seriesSigma);
		
		paneWorker.setCenter(lineChart);
		
		//Bouton deconnexion
		buttonLogout = new Button("Logout");
		buttonLogout.setOnAction(new ControllerMenu());
		paneWorker.setRight(buttonLogout);
		
		//Affichage de la fenetre
		sceneWorker = new Scene(paneWorker);
		stageWorker.setTitle("Worker");
		stageWorker.setScene(sceneWorker);
		stageWorker.show();	
	
	}
	
	public void setComputeTimeValue(long computeTimeData) {
		this.computeTimeData = computeTimeData;
		computeTimeValue.setText(computeTimeData + "ms");
	}
	
	public void addAPoste(String listPoste) {
		comboBoxStandID.getItems().setAll(listPoste);
	}
	
	public boolean isCheckedFriction() {
		return checkBoxFriction.isSelected();
	}

	public boolean isCheckedRollSpeed() {
		return checkBoxRollSpeed.isSelected();
	}
	
	public boolean isCheckedSigma() {
		return checkBoxSigma.isSelected();
	}
	
	public void setFlagFriction(boolean flagFriction) {
		this.flagFriction = flagFriction;
	}
	
	public void setFlagRollSpeed(boolean flagRollSpeed) {
		this.flagRollSpeed = flagRollSpeed;
	}
	
	public void setFlagSigma(boolean flagSigma) {
		this.flagSigma = flagSigma;
	}
	
	public void importPointsForData(ArrayList<Table_donnees_affichage> allDonneeAffichage) {
		// TODO Auto-generated method stub
		data = allDonneeAffichage;

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// Update UI here.

				seriesFriction.getData().clear();
				seriesSigma.getData().clear();
				seriesRollSpeed.getData().clear();
				for (int i = 0; i < data.size(); i++) {
					if (flagRollSpeed) {
						seriesRollSpeed.getData().add(new XYChart.Data<>(i, data.get(i).getRolling_speed()));
					} else {
						seriesRollSpeed.getData().add(new XYChart.Data<>(0, 0));
					}
					if (flagFriction) {
						if (data.get(i).getFriction() != 0)
							seriesFriction.getData().add(new XYChart.Data<>(i, data.get(i).getFriction()));
					} else {
						seriesFriction.getData().add(new XYChart.Data<>(0, 0));
					}
					if (flagSigma) {
						if (data.get(i).getSigma() != 0)
							seriesSigma.getData().add(new XYChart.Data<>(i, data.get(i).getSigma()));
					} else {
						seriesSigma.getData().add(new XYChart.Data<>(0, 0));
					}
				}
			}
		});
	}
	
	public void moveToMenu() {
		controllerMenu = new ControllerMenu();
		stageWorker.close();
		controllerMenu.show();
	}
	
	
	
	public ComboBox<String> getStandID() {
		return comboBoxStandID;
	}

	public void setStandID(ComboBox<String> comboBoxStandID) {
		this.comboBoxStandID = comboBoxStandID;
	}
	
	
	
}
