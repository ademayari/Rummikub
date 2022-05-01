package gui;

import java.util.ResourceBundle;

import domein.DomeinController;
import exceptions.AantalException;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HoofdPaneel extends GridPane {

	private DomeinController dc;
	private TextField txfAantalGebruikers;
	private ResourceBundle r;
	private Alert alert;

	public HoofdPaneel(DomeinController dc) {
		this.dc = dc;
		r = dc.getTaal().getR();
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

		Label lblWelcome = new Label(r.getString("welcome"));
		lblWelcome.setFont(Font.font("Garamond", FontWeight.EXTRA_BOLD, 20));
		this.add(lblWelcome, 0, 0, 2, 1);

		Label lblAantalGebruikers = new Label(r.getString("amount"));
		this.add(lblAantalGebruikers, 0, 1);

		txfAantalGebruikers = new TextField();
		this.add(txfAantalGebruikers, 0, 2);

		Button btnStart = new Button(r.getString("login"));
		this.add(btnStart, 0, 3);
		setHalignment(btnStart, HPos.LEFT);

		alert = new Alert(AlertType.WARNING);

		btnStart.setOnAction(this::btnStartClicked);

	}

	private void btnStartClicked(ActionEvent action) {
		String excep = r.getString("aantalexception");

		try {
			int aantal = Integer.parseInt(txfAantalGebruikers.getText());

			dc.registreerAantalGebruikers(aantal);
			AanmeldPaneel aanmeldpnl = new AanmeldPaneel(dc);
			getScene().setRoot(aanmeldpnl);

		}

		catch (AantalException e) {
			handleException(excep);
		}

		catch (NumberFormatException e) {
			alert.setContentText("Gelieve geen text in te geven");
			alert.show();
			txfAantalGebruikers.setText("");

		}

	}

	private void handleException(String s) {
		txfAantalGebruikers.setText(s);
		txfAantalGebruikers.selectAll();
		txfAantalGebruikers.requestFocus();

	}
}
