package gui;

import java.util.Optional;

import domein.DomeinController;
import exceptions.BuitenBereikException;
import exceptions.IncorrecteSelectie;
import exceptions.LeegVeldException;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class VerplaatsenWerkveldPaneel extends GridPane {

	private DomeinController dc;
	private Stage stage;
	private SpelPaneel spelpaneel;
	private TextField txPlaatsOpBord;
	private TextField txPlaatsInSerieOfRij;
	private Alert alert = new Alert(AlertType.WARNING);

	private int posBord;
	private int posInSerieOfRij;

	public VerplaatsenWerkveldPaneel(DomeinController dc, Stage stage, SpelPaneel spelpaneel) {
		this.spelpaneel = spelpaneel;
		this.dc = dc;
		this.stage = stage;
		bouwSchermOp();
		invoerInput();

	}

	private void bouwSchermOp() {

		Image imgBord = new Image(getClass().getResource("/gui/image/backdrop.png").toExternalForm());

		BackgroundImage backSpelBord = new BackgroundImage(imgBord, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background background = new Background(backSpelBord);
		this.setBackground(background);

		txPlaatsOpBord = new TextField();
		this.add(txPlaatsOpBord, 1, 0);

		Label lblPlaatsOpBord = new Label(
				"Welke Serie of Rij op het spelbord \n bevat de steen die je wilt gebruiken?");
		this.add(lblPlaatsOpBord, 0, 0);

		txPlaatsInSerieOfRij = new TextField();
		this.add(txPlaatsInSerieOfRij, 1, 1);

		Label lblPlaatsInSerieOfRij = new Label("waar in de serie of rij \n wil je een steen gebruiken?");
		this.add(lblPlaatsInSerieOfRij, 0, 1);

		Button btnTerug = new Button("Ga terug naar het hoofdscherm");
		this.add(btnTerug, 0, 4);
		btnTerug.setOnAction(this::gaTerugNaarVorigScherm);

		Button btnSelectie = new Button("Bevestig je input");
		this.add(btnSelectie, 1, 4);
		btnSelectie.setOnAction(this::registratieInput);

		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setGridLinesVisible(false);
		txPlaatsInSerieOfRij.setMaxWidth(Double.MAX_VALUE);
		txPlaatsOpBord.setMaxWidth(Double.MAX_VALUE);
		setHalignment(btnSelectie, HPos.RIGHT);

		lblPlaatsOpBord.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 12));
		lblPlaatsInSerieOfRij.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 12));

		stage.setOnCloseRequest(evt -> {
			Alert alert = new Alert(Alert.AlertType.WARNING,
					"Weet je zeker dat je geen stenen \n meer wilt vervangen?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				stage.close();
				spelpaneel.getC().getSelectionModel().clearSelection();

			} else {
				evt.consume();
			}
		});

	}

	private void gaTerugNaarVorigScherm(ActionEvent e) {
		Alert alert = new Alert(Alert.AlertType.WARNING, "Weet je zeker dat je geen stenen \n meer wilt vervangen?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			stage.close();
			spelpaneel.getC().getSelectionModel().clearSelection();
		} else {
			e.consume();
		}

	}

	private void invoerInput() {

		txPlaatsOpBord.textProperty().addListener((v, oldVal, indexSpelBord) -> {

			if (!indexSpelBord.isEmpty()) {

				try {
					posBord = Integer.parseInt(indexSpelBord);
					if (posBord > dc.getRummikubspel().getBeurt().getCopyList().size())
						throw new BuitenBereikException();

				} catch (BuitenBereikException e) {
					alert.setContentText("Gelieve een geldige plaats te kiezen!");
					alert.show();
					txPlaatsOpBord.setText("");

				}

				catch (NumberFormatException e) {
					alert.setContentText("Gelieve geen text in te voeren!");
					alert.show();
					txPlaatsOpBord.setText("");

				}

			}

		});

		txPlaatsInSerieOfRij.textProperty().addListener((v, oldVal, indexRijOfSerie) -> {

			if (!indexRijOfSerie.isEmpty()) {

				try {
					posInSerieOfRij = Integer.parseInt(indexRijOfSerie);
					if (posInSerieOfRij > dc.getRummikubspel().getBeurt().getCopyList().get(posBord).size()) {

					}

				} catch (IndexOutOfBoundsException e) {
					alert.setContentText("Gelieve een geldige plaats te kiezen!");
					alert.show();
					txPlaatsInSerieOfRij.setText("");

				} catch (NumberFormatException e) {
					alert.setContentText("Gelieve geen text in te voeren!");
					alert.show();
					txPlaatsInSerieOfRij.setText("");
				}

			}

		});
	}

	private void registratieInput(ActionEvent e) {

		try {
			if (txPlaatsOpBord.getText().isEmpty() || txPlaatsInSerieOfRij.getText().isEmpty())
				throw new LeegVeldException();
			spelpaneel.bouwNodeOp(posBord, posInSerieOfRij);
			dc.verplaatsNaarWerkveld(posBord, posInSerieOfRij);
			spelpaneel.getBoxv3().getChildren().clear();
			spelpaneel.bouwVeld(dc.getRummikubspel().getBeurt().getCopyList());
		} catch (LeegVeldException e1) {
			alert.setContentText("Textvelden mogen niet leegzijn!");
			alert.show();
			txPlaatsOpBord.setText("");
			txPlaatsInSerieOfRij.setText("");

		}

		catch (IncorrecteSelectie e2) {
			alert.setContentText("Gelieve de juiste steen te selecteren!");
			alert.show();
			txPlaatsInSerieOfRij.setText("");

		}

	}

}
