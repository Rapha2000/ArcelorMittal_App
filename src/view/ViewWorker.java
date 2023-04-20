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
import controller.ControllerProcessEng;



/**
 * La classe ViewWorker contient les éléments d'interface graphique pour la vue du travailleur.
 * Elle permet d'afficher les données du poste de travail, les graphiques de courbes, les options de
 * visualisation et le bouton de déconnexion. Elle est utilisée par les classes ControllerWorker, ControllerMenu
 * et ControllerProcessEng pour la gestion de l'interface graphique.
 */
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
	private Text tempsOrowanText;
	private Text frictionCoefValue;
	private long tempsExOrowan;
	private Label labelFrictionCoef;
	private Label labelValFrictionCoef;
	private HBox hBox;
	private CheckBox checkBoxFriction;
	private CheckBox checkBoxRollSpeed;
	private CheckBox checkBoxSigma;
	private boolean booleanCoefFriction;
	private boolean booleanRollSpeed;
	private boolean booleanSigma;
	
	
	
	//Partie centre (affichage des courbes)	
	private NumberAxis timeAxis;
	private NumberAxis yAxis;
	private XYChart.Series<Number, Number> coefFriction;
	private XYChart.Series<Number, Number> rollSpeed;
	private XYChart.Series<Number, Number> sigma;
	private ArrayList<Table_donnees_affichage> donneeAffichage;
	
	private Button buttonLogout;
	
	
	private ViewWorker() {
		
	}
	
    /**
     * Renvoie l'instance unique de ViewWorker.
     * @return instanceViewWorker l'instance de la classe ViewWorker
     */
	public static ViewWorker getInstanceViewWorker() { //récupère l'instance courante car on est dans un pattern singleton
		if (instanceViewWorker == null) {
			instanceViewWorker = new ViewWorker();
		}
		return instanceViewWorker;
	}
	   
    /**
     * Configure l'interface graphique de la vue de l'ouvrier
     */
	public void setViewWorker() {
	
		stageWorker = new Stage();
		paneWorker = new BorderPane();
	
		vBox = new VBox();
				
		labelStandID = new Label("Voir le poste : ");
		comboBoxStandID = new ComboBox<>();
		comboBoxStandID.setOnMouseClicked(new ControllerWorker("nomPoste"));
		
		hBoxStandID = new HBox();
		
		
		labelComputeTime = new Label("Compute time of Orowan");
		tempsOrowanText = new Text();
		
		labelFrictionCoef = new Label("Friction coefficient factor");
		frictionCoefValue = new Text();
		
		hBox = new HBox();
		
		checkBoxFriction = new CheckBox("Friction");
		checkBoxFriction.setOnMouseClicked(new ControllerWorker("Friction"));
		checkBoxRollSpeed = new CheckBox("Roll Speed");
		checkBoxRollSpeed.setOnMouseClicked(new ControllerWorker("Roll Speed"));
		checkBoxSigma = new CheckBox("Sigma");
		checkBoxSigma.setOnMouseClicked(new ControllerWorker("Sigma"));
		
		hBox.getChildren().addAll(checkBoxFriction, checkBoxRollSpeed, checkBoxSigma);
		
		vBox.getChildren().addAll(labelStandID, comboBoxStandID, labelComputeTime, tempsOrowanText, labelFrictionCoef, frictionCoefValue, hBox);
	
		paneWorker.setLeft(vBox);
		//paneWorker.setCenter();
		
		
		//Initialisation du graphique
		NumberAxis timeAxis = new NumberAxis();
		timeAxis.setLabel("temps (ms)");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("valeur");
				
		//Definition des series de donnees
		coefFriction = new XYChart.Series<>();
		coefFriction.setName(" Coef Friction");
		rollSpeed = new XYChart.Series<>();
		rollSpeed.setName("Roll Speed");
		sigma = new XYChart.Series<>();
		sigma.setName("Sigma");
		
		//Initialisation des courbes
		final LineChart<Number, Number> lc = new LineChart<Number, Number>(timeAxis, yAxis);
		lc.setTitle("Curves");
		lc.getData().add(coefFriction);
		lc.getData().add(rollSpeed);
		lc.getData().add(sigma);
		
		paneWorker.setCenter(lc);
		
		//Bouton deconnexion
		buttonLogout = new Button("Logout");
		//buttonLogout.setOnAction(new ControllerWorker("Logout"));
		buttonLogout.setOnMouseClicked(new ControllerWorker("Logout"));
		paneWorker.setRight(buttonLogout);
		
		//Affichage de la fenetre
		sceneWorker = new Scene(paneWorker);
		stageWorker.setTitle("Worker");
		stageWorker.setScene(sceneWorker);
		stageWorker.show();	
	
	}
	

	 /**
    Ajoute des éléments à la liste des postes affichée dans la combobox.
    @param listeDesPostes La liste des postes à ajouter.
    */
	public void ajouterPoste(ArrayList<String> listeDesPostes) { 
		comboBoxStandID.getItems().setAll(listeDesPostes);
	}
	
	
	
	 /**
    Cette méthode permet de passer à l'écran de menu principal de l'application.
    Elle crée une instance du contrôleur de menu, ferme la fenêtre de travail actuelle et affiche la fenêtre du menu principal.
    */
	public void allerMenu() { 
		controllerMenu = new ControllerMenu();
		stageWorker.close();
		controllerMenu.montrerVue();
	}
	
	

	/**
	 * Met à jour les données affichées sur les courbes pour les coefficients de friction,
	 * les valeurs de sigma et la vitesse de rotation en fonction des données fournies.
	 * 
	 * @param donneesAffichage les données à afficher sur les courbes
	 */
	public void obtenirPointsCourbes(ArrayList<Table_donnees_affichage> donneesAffichage) {
		// Stocke les données fournies dans la variable membre donneeAffichage
		donneeAffichage = donneesAffichage;

		// Met à jour les données affichées sur les courbes en utilisant JavaFX Platform.runLater pour exécuter
		// le code dans le thread JavaFX Application Thread
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// Efface toutes les données des courbes avant de les mettre à jour
				coefFriction.getData().clear();
				sigma.getData().clear();
				rollSpeed.getData().clear();
				// Parcourt les données fournies et ajoute les points correspondants aux courbes
				for (int i = 0; i < donneeAffichage.size(); i++) {
					if (booleanRollSpeed) {
						// Si la vitesse de rotation doit être affichée, ajoute les données de la vitesse de rotation à la courbe
						rollSpeed.getData().add(new XYChart.Data<>(i, donneeAffichage.get(i).getRolling_speed()));
					} else {
						// Sinon, ajoute des données nulles à la courbe
						rollSpeed.getData().add(new XYChart.Data<>(0, 0));
					}
					if (booleanCoefFriction) {
						// Si le coefficient de friction doit être affiché et que la valeur n'est pas nulle, ajoute les données du coefficient de friction à la courbe
						if (donneeAffichage.get(i).getFriction() != 0)
							coefFriction.getData().add(new XYChart.Data<>(i, donneeAffichage.get(i).getFriction()));
					} else {
						// Sinon, ajoute des données nulles à la courbe
						coefFriction.getData().add(new XYChart.Data<>(0, 0));
					}
					if (booleanSigma) {
						// Si la valeur de sigma doit être affichée et que la valeur n'est pas nulle, ajoute les données de sigma à la courbe
						if (donneeAffichage.get(i).getSigma() != 0)
							sigma.getData().add(new XYChart.Data<>(i, donneeAffichage.get(i).getSigma()));
					} else {
						// Sinon, ajoute des données nulles à la courbe
						sigma.getData().add(new XYChart.Data<>(0, 0));
					}
				}
			}
		});
	}

	
	
	/**
    Cette méthode renvoie la liste déroulante des identifiants de poste.
    @return comboBoxStandID la liste déroulante des identifiants de poste.
    */
	public ComboBox<String> getComboBoxStandID() {
		return comboBoxStandID;
	}
	/**
	 Cette méthode permet de définir la liste déroulante des identifiants de poste.
	 @param comboBoxStandID la liste déroulante des identifiants de poste.
	 */
	public void setComboBoxStandID(ComboBox<String> comboBoxStandID) {
		this.comboBoxStandID = comboBoxStandID;
	}
	

	public boolean isSelectededFriction() { //permet de savoir si l'ouvrier souhaite voir la courbe du coef de friction
		return checkBoxFriction.isSelected();
	}

	public boolean isSelectedRollSpeed() { //permet de savoir si l'ouvrier souhaite voir la courbe de la vitesse
		return checkBoxRollSpeed.isSelected();
	}
	
	public boolean isSelectedSigma() { //permet de savoir si l'ouvrier souhaite voir la courbe de sigma
		return checkBoxSigma.isSelected();
	}
	
	public void setBooleanCoefFriction(boolean boolFriction) { //boolean permettant de controller le comportement de nos boucles dans obtenirPointsCourbes()
		this.booleanCoefFriction = boolFriction;
	}
	
	public void setBooleanRollSpeed(boolean boolRollSpeed) {
		this.booleanRollSpeed = boolRollSpeed;
	}
	
	public void setBooleanSigma(boolean boolSigma) {
		this.booleanSigma = boolSigma;
	}
	
	
	 /**
    Met à jour la valeur de temps de calcul affichée dans l'interface graphique avec la valeur passée en paramètre.
    @param tempsExOrowan La valeur de temps de calcul à afficher.
    */
	public void setTempsExOrowan(long tempsExOrowan) {
		this.tempsExOrowan = tempsExOrowan;
		tempsOrowanText.setText(tempsExOrowan + "ms");
	}
}