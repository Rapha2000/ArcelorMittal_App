package controler;

import java.util.List;

import java.beans.PropertyChangeEvent;
import javafx.scene.input.MouseEvent;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.Accumulateur;
import view2.IView;


/**
 *Controleur est la classe permettant de faire le lien entre le modèle et l'interface graphique.
 *
 *Un membre de cette classe est caractérisé par les attributs suivants :
 * 
 * un accumulateur lui permettant de réaliser les opérations demandées et d'empiler les opérandes
 * une valeurActuelle qui contient les chiffres entrés par l'utilisateur avant d'être push
 * une view qui gère l'interface graphique 
 * 
 * 
 * De plus, le controleur doit pouvoir définir la view avec laquelle il travaille.
 * Il doit aussi pouvoir gérer les évènements qui surviennent sur cette view.
 * Enfin, il doit être capable de changer certains élements de cette view quand il est averti d'un changement par le modèle
 * 
 * @author anais
 * @version 2.0
 */


public class Controleur implements EventHandler<Event>, java.beans.PropertyChangeListener { 

	
	
	 /**
     * L'accumulateur du controleur. Cet accumulateur n'est pas modifiable.
     */
	private final Accumulateur accumulateur;
	
	 /**
     * La valeur actuellement contenue dans le controleur.
     */
	private String valeurActuelle;
	
	/**
     * La view sur laquelle le controleur va répercuter les changements du modèle.
     */
	private IView view;
	
	
	
	
	
    /**
     * Constructeur du controleur.
     * 
     * A la construction d'un objet controleur, la "valeur actuelle" est une chaîne de caractère vide, puisque l'utilisateur n'a encore rien saisi. 
     * De plus l'accumulateur est crée au même moment.
     */
	public Controleur() {
		super();
		this.valeurActuelle="";
		this.accumulateur = new Accumulateur(this);
	}

    /**
     * Affecte une vue au controleur.
     * 
     * @param vue
     */
	public void definirVue(IView vue) {
		this.view=vue;
		//this.view.change(this.valeurActuelle); //permet d'initialiser les label
	}


    /**
     * Permet la gestion des évènements qui surviennent dès que l'utilisateur appuie sur un bouton de la vue.
     * S'il s'agit d'un bouton chiffre (de 0 à 9), on l'ajoute à la valeur actuelle du controleur (après vérification du respect de la syntaxe).
     * S'il s'agit d'un bouton virgule, on l'ajoute à la valeur actuelle en vérifiant qu'il n'y a pas déjà une virgule dans valeur actuelle.
     * S'il s'agit d'un bouton opérateur (+,*,/,..) on dit à l'accumulateur de réaliser l'opération
     * S'il s'agit du bouton RESET, on appelle la méthode clear de l'accumulateur
     * S'il s'agit du bouton push, on va empiler valeur actuelle sur la pile de l'accumulateur (après avoir vérifié que valeur actuelle n'est pas vide et qu'elle ne finit pas par une virgule)
     * 
     * @param event
     *            un évènement de la vue
     */
	@Override
	public void handle (Event event) {
		
		if (event instanceof MouseEvent) {
			
		final Button butt = (Button) event.getSource();
		final String typeBouton = butt.getText();
		switch (typeBouton) {
		case ("0"): {
			if(! this.valeurActuelle.equals("0")) { //on vérifie que valeurActuelle n'est pas égale à "0" ( car 00 n'a aucun sens )
			this.valeurActuelle=this.valeurActuelle + "0";
			this.view.change2("0"); // on modifie l'affichage de la vue pour voir ce qu'on est en train d'écrire
			break;}
			else { //sinon on avertit l'utilisateur d'une erreur de syntaxe
				 this.view.changerTexteL5("attention à la syntaxe !");
			}
			break;}
		case ("1"):{
			if(! this.valeurActuelle.equals("0")) {  //on vérifie que valeurActuelle n'est pas égale à "0" ( car 01 n'a aucun sens )
			this.valeurActuelle=this.valeurActuelle + "1";
			this.view.change2("1");// on modifie l'affichage de la vue pour voir ce qu'on est en train d'écrire
			break;}
			else {
				 this.view.changerTexteL5("attention à la syntaxe !");
			}
			break;}
		case ("2"):{
			if(! this.valeurActuelle.equals("0")) {  //on vérifie que valeurActuelle n'est pas égale à "0" ( car 02 n'a aucun sens )
			this.valeurActuelle=this.valeurActuelle + "2";
			this.view.change2("2");// on modifie l'affichage de la vue pour voir ce qu'on est en train d'écrire
			break;}
			else {
				 this.view.changerTexteL5("attention à la syntaxe !");
			}
			break;}
		case ("3"):{  //on vérifie que valeurActuelle n'est pas égale à "0" ( car 03 n'a aucun sens )
			if(! this.valeurActuelle.equals("0")) {
			this.valeurActuelle=this.valeurActuelle + "3";
			this.view.change2("3");// on modifie l'affichage de la vue pour voir ce qu'on est en train d'écrire
			break;}
			else {
				 this.view.changerTexteL5("attention à la syntaxe !");
			}
			break;}
		case ("4"):{
			if(! this.valeurActuelle.equals("0")) {  //on vérifie que valeurActuelle n'est pas égale à "0" ( car 04 n'a aucun sens )
			this.valeurActuelle=this.valeurActuelle + "4";
			this.view.change2("4");// on modifie l'affichage de la vue pour voir ce qu'on est en train d'écrire
			break;}
			else {
				 this.view.changerTexteL5("attention à la syntaxe !");
			}
			break;}
		case ("5"):{
			if(! this.valeurActuelle.equals("0")) {  //on vérifie que valeurActuelle n'est pas égale à "0" ( car 05 n'a aucun sens )
			this.valeurActuelle=this.valeurActuelle + "5";
			this.view.change2("5");// on modifie l'affichage de la vue pour voir ce qu'on est en train d'écrire
			break;}
			else {
				 this.view.changerTexteL5("attention à la syntaxe !");
			}
			break;}
		case ("6"):{
			if(! this.valeurActuelle.equals("0")) {  //on vérifie que valeurActuelle n'est pas égale à "0" ( car 06 n'a aucun sens )
			this.valeurActuelle=this.valeurActuelle + "6";
			this.view.change2("6");// on modifie l'affichage de la vue pour voir ce qu'on est en train d'écrire
			break;}
			else {
				 this.view.changerTexteL5("attention à la syntaxe !");
			}
			break;}
		case ("7"):{
			if(! this.valeurActuelle.equals("0")) {  //on vérifie que valeurActuelle n'est pas égale à "0" ( car 07 n'a aucun sens )
			this.valeurActuelle=this.valeurActuelle + "7";
			this.view.change2("7");// on modifie l'affichage de la vue pour voir ce qu'on est en train d'écrire
			break;}
			else {
				 this.view.changerTexteL5("attention à la syntaxe !");
			}
			break;}
		case ("8"):{
			if(! this.valeurActuelle.equals("0")) {  //on vérifie que valeurActuelle n'est pas égale à "0" ( car 08 n'a aucun sens )
			this.valeurActuelle=this.valeurActuelle + "8";
			this.view.change2("8");// on modifie l'affichage de la vue pour voir ce qu'on est en train d'écrire
			break;}
			else {
				 this.view.changerTexteL5("attention à la syntaxe !");
			}
			break;}
		case ("9"):{
			if(! this.valeurActuelle.equals("0")) {  //on vérifie que valeurActuelle n'est pas égale à "0" ( car 09 n'a aucun sens )
			this.valeurActuelle=this.valeurActuelle + "9";
			this.view.change2("9");// on modifie l'affichage de la vue pour voir ce qu'on est en train d'écrire
			break;}
			else {
				 this.view.changerTexteL5("attention à la syntaxe !");
			}
			break;}
		case ("+"):{
			if ( accumulateur.getP().size() > 1  ) { //on vérifie qu'il y a au moins deux élements dans la pile
				accumulateur.add(); // on réalise l'addition
				this.view.change2("+"); // on modifie l'affichage de la vue pour voir ce qu'on est en train d'écrire
			break;}
			break;}
			
		case ("-"):{
			if ( accumulateur.getP().size() > 1  ) {
				accumulateur.sub();
				this.view.change2("-");
				break;}
			break;
		}
			
		case ("*"):{
			if ( accumulateur.getP().size() > 1 ) {
				accumulateur.mult();
				this.view.change2("*");
			break;}
			break;}
			
				
		case ("/"):{
			if (accumulateur.getP().size() > 1  ) {
				accumulateur.div();
				this.view.change2("/");}
			break;}
				
		case ("+/-"):{
			if ( accumulateur.getP().size() > 0 ) {
				accumulateur.neg();
				this.view.change2("+/-");
			break;}
			break;}

			
		case (","):{
			if ( this.valeurActuelle.length() > 0 && this.valeurActuelle.contains(".")==false) { // on vérifie que la virgule n'est pas le premier caractère et qu'il n'y a pas déjà une virgule 
				this.valeurActuelle=this.valeurActuelle + ".";
				this.view.change2(",");
				break;
			}
			else {
				 this.view.changerTexteL5("attention à la syntaxe !");
			}
			break;}
		case ("RESET"):{
			if ( accumulateur.getP().size() > 0  ) { //on vérifie que la pile n'est pas vide
				accumulateur.clear();
				this.valeurActuelle=""; // on remet aussi valeurActuelle à zéro
				break;}
			this.valeurActuelle="";
			break;}
		case ("AC"):{
				this.view.changerTexteL5(""); //on remet l'affichage à zéro
				break;}
		case ("<>"):{
			char c='.';
			if (this.valeurActuelle.charAt(this.valeurActuelle.length()-1) != c ) { // on vérifie qu'on ne termine pas par une virgule ( car 0,01, n'a aucun sens)
				if (this.valeurActuelle.length() > 0 ) {// on vérifie que valeurActuelle contient des caractères
				
				double value;
				value = Double.parseDouble(this.valeurActuelle);
				accumulateur.getP().push(value); //on empile valeurActuelle sur la pile
				this.valeurActuelle=""; //on remet à zéro valeurActuelle
				this.view.changerTexteL5(""); //on remet à zéro l'affichage de ce que l'on vient de taper
				break;}
		break;}
			else {
				 this.view.changerTexteL5("attention à la syntaxe !");
			}
		break;}
		}
		}
		
	
	}




	/**
     * Change ce qui est affiché dans l'écran de la calculatrice
     * Selon le type d'event reçu, on ne fait pas les mêmes changements sur la vue
     * @param evt
     */
    @Override
    public void propertyChange(final PropertyChangeEvent evt) { 
        if (evt.getPropertyName().equals("chgmtContenu")) { // si le contenu a changé
            this.view.change1(String.valueOf(evt.getNewValue())); // on change l'affichage du contenu
        }
        if (evt.getPropertyName().equals("majHistorique")) { // si l'historique a changé
            this.view.change((List<String>)evt.getNewValue()); //on change l'affichage de l'historique
        }
        if (evt.getPropertyName().equals("clearHistory")) { // si l'historique est remis à zéro
        	this.view.change((List<String>)evt.getNewValue());; //on affiche un historique vide
        }
      
    }
    
	
}

