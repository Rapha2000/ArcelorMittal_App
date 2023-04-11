/**
 * 
 */
/**
 * @author guill
 *
 */
module Orowan {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	requires java.sql;
	
	opens view to javafx.graphics, javafx.fxml;
	
}