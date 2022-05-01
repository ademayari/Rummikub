package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import domein.DomeinController;
import domein.Gebruiker;
import exceptions.IncorrecteGebruikerException;
import exceptions.ZelfdeGebruikerException;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

public class AanmeldPaneel extends GridPane {
	private DomeinController dc;
	private TextField txfGebruikersnaam;
	private PasswordField pwdPassword;
	private Label lblFoutbericht;
	private ResourceBundle r;
	private Alert alert;
	private List<Gebruiker> gebruikersAangemeld;

	public AanmeldPaneel(DomeinController dc) {
		this.dc = dc;
		r = dc.getTaal().getR();
		gebruikersAangemeld = new ArrayList<>();

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

		Label lblUsername = new Label(r.getString("user"));
		this.add(lblUsername, 0, 1);
		txfGebruikersnaam = new TextField();
		this.add(txfGebruikersnaam, 1, 1);

		Label lblPassword = new Label(r.getString("key"));
		this.add(lblPassword, 0, 2);
		pwdPassword = new PasswordField();
		this.add(pwdPassword, 1, 2);

		Button btnLogin = new Button("Login");
		this.add(btnLogin, 0, 3);

		alert = new Alert(AlertType.WARNING);

		lblFoutbericht = new Label();
		lblFoutbericht.setTextFill(Color.RED);
		this.add(lblFoutbericht, 1, 3);

		btnLogin.setOnAction(this::aanmelden);

	}

	private void aanmelden(ActionEvent event) {

		try {

			gebruikersAangemeld.add(new Gebruiker(txfGebruikersnaam.getText(), pwdPassword.getText(), 0));

			gebruikersAangemeld.stream().reduce((a, b) -> {
				if (a.equals(b)) {
					gebruikersAangemeld.remove(gebruikersAangemeld.size() - 1);
					throw new ZelfdeGebruikerException();

				}
				return b;

			});

			dc.meldAan(txfGebruikersnaam.getText(), pwdPassword.getText(), 0);
			txfGebruikersnaam.setText("");
			pwdPassword.setText("");

		} catch (IncorrecteGebruikerException e) {

			txfGebruikersnaam.setText("");
			pwdPassword.setText("");
			alert.setContentText("Het wachtwoord of de gebruikersnaam is incorrect ingegeven!");
			alert.show();

		}

		catch (ZelfdeGebruikerException e) {

			txfGebruikersnaam.setText("");
			pwdPassword.setText("");
			alert.setContentText("Je kan niet met 2 dezelfde gebruikers aanmelden!");
			alert.show();

		}

		if (dc.getGebruikers().size() == dc.getAantalGebruikers()) {
			StartPaneel spelKeuzePaneel = new StartPaneel(dc);
			getScene().setRoot(spelKeuzePaneel);

		}

	}

}
