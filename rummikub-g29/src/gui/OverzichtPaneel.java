package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import persistentie.ScoreDatabase;

public class OverzichtPaneel extends GridPane {

	private DomeinController dc;
	private TableColumn tbColumn;
	private ScoreDatabase scoreDatabase = new ScoreDatabase();
	private TextArea txaOverzicht;

	public OverzichtPaneel(DomeinController dc) {
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
		Button resetScore = new Button("reset scorebord");
		this.add(resetScore, 3, 24);
		resetScore.setOnAction(this::scoreReset);

		Button returnstart = new Button("return");
		this.add(returnstart, 0, 24);
		returnstart.setOnAction(this::returnstart);

		txaOverzicht = new TextArea();
		this.add(txaOverzicht, 0, 0, 4, 20);
//		TableView tb = new TableView();
//		for (Gebruiker gebr : dc.getGebruikers()) {
//			tbColumn = new TableColumn(gebr.getGebruikersnaam());
//			tb.getColumns().add(tbColumn);
//			tb.getItems().add(scoreDatabase.getScores());
//		}
//		this.add(tb, 0, 0);
//
		for (String score : dc.getScores()) {

			txaOverzicht.appendText(score + "\n");
		}

	}

	private void scoreReset(ActionEvent e) {
		dc.scoreReset();
		txaOverzicht.clear();
		for (String score : dc.getScores()) {

			txaOverzicht.appendText(score + "\n");
		}

	}

	private void returnstart(ActionEvent e) {

		StartPaneel startpaneel = new StartPaneel(dc);
		getScene().setRoot(startpaneel);
	}

}
