package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class StartPaneel extends GridPane {

	DomeinController dc;
    
	public StartPaneel(DomeinController dc) {
		this.dc = dc;
		configureerGrid();
		voegComponentenToe();
		}

	

	private void configureerGrid() {
		setPadding(new Insets(10));
		setHgap(10);
		setVgap(10);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setHalignment(HPos.RIGHT);

		ColumnConstraints col2 = new ColumnConstraints();
		col2.setHgrow(Priority.ALWAYS);

		getColumnConstraints().addAll(col1, col2);
	}

	private void voegComponentenToe() {

		TextArea txaAangelogdeGebruikers = new TextArea();
		this.add(txaAangelogdeGebruikers, 0, 0, 4, 20);
		txaAangelogdeGebruikers.setText(dc.geefGebruikersNamen());

		Button startSpel = new Button("start spel");
		this.add(startSpel, 0, 24);
		startSpel.setOnAction(this::spelSpelen);

		Button geefOverzicht = new Button("geef overzicht");
		this.add(geefOverzicht, 3, 24);
		geefOverzicht.setOnAction(this::overzichtKrijgen);

	}
    private void overzichtKrijgen(ActionEvent e) {
    	dc.geefGebruikersNamen();
    	OverzichtPaneel overzichtpaneel = new OverzichtPaneel(dc);
    	getScene().setRoot(overzichtpaneel); 
    	}
	
	
	private void spelSpelen(ActionEvent e) {

		dc.startSpel();
		SpelPaneel spelpaneel = new SpelPaneel(dc);

		getScene().setRoot(spelpaneel);
		Stage spelPaneelStage = (Stage) spelpaneel.getScene().getWindow();
		spelPaneelStage.setMaximized(true);
	}

//	private void geefOverzicht(ActionEvent e) {
//
//		GUIusecase4 usecase4Paneel = new GUIusecase4(dc);
//		getScene().setRoot(usecase4Paneel);
//
//	}

}
