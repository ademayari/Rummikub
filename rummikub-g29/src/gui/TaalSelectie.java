package gui;

import domein.DomeinController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TaalSelectie extends GridPane {

	private DomeinController dc;
	private ChoiceBox<String> choiceBox;
	private String keuzeTijdelijk;
	private Button ok;
	private Alert alert;

	public TaalSelectie(DomeinController dc) {
		this.dc = dc;

		configureerGrid();
		bouwSchermOp();

	}

	private void configureerGrid() {

		this.setGridLinesVisible(false);
		this.setHgap(10);
		this.setVgap(10);
		this.setAlignment(Pos.CENTER);

	}

	private void bouwSchermOp() {

		Label lblTaalSelectie = new Label("kies uw taal");
		lblTaalSelectie.setFont(Font.font("Garamond", FontWeight.EXTRA_BOLD, 20));
		this.add(lblTaalSelectie, 0, 0, 2, 1);

		String keuzeTaal[] = { "English", "Nederlands" };
		choiceBox = new ChoiceBox(FXCollections.observableArrayList(keuzeTaal));
		this.add(choiceBox, 0, 1);
		ok = new Button("Bevestig uw keuze");
		this.add(ok, 0, 8);

		alert = new Alert(AlertType.WARNING);

		ok.setOnAction(this::btnOkClicked);

		choiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, keuze) -> {

			keuzeTijdelijk = keuze;

		});

	}

	private void btnOkClicked(ActionEvent action) {

		if (keuzeTijdelijk == null) {
			alert.setContentText("Gelieve een keuze te maken");
			alert.show();
		} else {
			dc.setTaal(keuzeTijdelijk);
			HoofdPaneel hfdPaneel = new HoofdPaneel(dc);
			getScene().setRoot(hfdPaneel);
		}

	}

}
