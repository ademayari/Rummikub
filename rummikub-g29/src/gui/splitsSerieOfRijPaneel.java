package gui;

import java.util.Optional;

import domein.DomeinController;
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

public class splitsSerieOfRijPaneel extends GridPane {

	private DomeinController dc;
	private Stage stage2;
	private SpelPaneel spelpaneel;

	private int posRijVeld;
	private int posSteen1InLijst;
	private int posSteen2InLijst;
	private TextField txPosOpVeld;
	private TextField txSteenPos1;
	private TextField txSteenPos2;
	private Button btnConfirmatie;
	private Button btnTerug;
	private Label lblPosOpVeld;
	private Label lblPosStenenSplitsen;
	private Alert alert;

	public splitsSerieOfRijPaneel(DomeinController dc, Stage stage2, SpelPaneel spelpaneel) {
		this.spelpaneel = spelpaneel;
		this.dc = dc;
		this.stage2 = stage2;
		bouwSchermOp();
		invoerInput();

	}

	private void bouwSchermOp() {

		Image imgBord = new Image(getClass().getResource("/gui/image/backdrop.png").toExternalForm());

		BackgroundImage backSpelBord = new BackgroundImage(imgBord, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background background = new Background(backSpelBord);
		this.setBackground(background);

		txPosOpVeld = new TextField();
		this.add(txPosOpVeld, 0, 2, 2, 1);

		txSteenPos1 = new TextField();
		this.add(txSteenPos1, 0, 4);

		txSteenPos2 = new TextField();
		this.add(txSteenPos2, 1, 4);

		lblPosOpVeld = new Label("Waar op het veld wil je splitsen?");
		this.add(lblPosOpVeld, 0, 1, 2, 1);

		lblPosStenenSplitsen = new Label("Geef de posities in van de stenen, \nwaar tussen je wilt splitsen");
		this.add(lblPosStenenSplitsen, 0, 3, 2, 1);

		btnConfirmatie = new Button("Bevestig je input");
		this.add(btnConfirmatie, 0, 10);

		btnTerug = new Button("Ga terug naar het hoofdscherm");
		this.add(btnTerug, 1, 10);

		alert = new Alert(AlertType.WARNING);

		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setGridLinesVisible(false);

		setHalignment(lblPosOpVeld, HPos.CENTER);
		setHalignment(lblPosStenenSplitsen, HPos.CENTER);

		lblPosOpVeld.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 12));
		lblPosStenenSplitsen.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 12));

		btnConfirmatie.setOnAction(this::registreerInput);
		btnTerug.setOnAction(this::gaTerugNaarVorigScherm);

		stage2.setOnCloseRequest(evt -> {
			Alert alert = new Alert(Alert.AlertType.WARNING,
					"Weet je zeker dat je geen stenen \n meer wilt aanleggen?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				stage2.close();
				spelpaneel.getC().getSelectionModel().clearSelection();

			} else {
				evt.consume();
			}
		});
	}

	private void invoerInput() {

		txPosOpVeld.textProperty().addListener((observable2, oldVal2, posRijOpVeld) -> {

			if (!posRijOpVeld.equals("")) {

				try {
					posRijVeld = Integer.parseInt(posRijOpVeld);
					if (posRijVeld > dc.getRummikubspel().getBeurt().getCopyList().get(posRijVeld).size()) {

					}

				} catch (NumberFormatException e) {

					alert.setContentText("Gelieve geen text in te voeren!");
					alert.show();
					txPosOpVeld.setText("");
				} catch (IndexOutOfBoundsException e) {

					alert.setContentText("De gekozen steen ligt buiten het bereik!");
					alert.show();
					txPosOpVeld.setText("");

				}

			}

		});

		txSteenPos1.textProperty().addListener((observable2, oldVal3, posSteen1) -> {

			if (!posSteen1.equals("")) {
				try {
					posSteen1InLijst = Integer.parseInt(posSteen1);
					if (posSteen1InLijst > dc.getRummikubspel().getBeurt().getCopyList().get(posRijVeld)
							.get(posSteen1InLijst).getGetal()) {

					}
				} catch (NumberFormatException e) {

					alert.setContentText("Gelieve geen text in te voeren!");
					alert.show();
					txSteenPos1.setText("");

				}

				catch (IndexOutOfBoundsException e) {
					alert.setContentText("De gekozen steen ligt buiten het bereik!");
					alert.show();
					txSteenPos1.setText("");
				}

			}

		});

		txSteenPos2.textProperty().addListener((observable2, oldVal3, posSteen2) -> {

			if (!posSteen2.equals("")) {
				try {
					posSteen2InLijst = Integer.parseInt(posSteen2);
					if (posSteen2InLijst > dc.getRummikubspel().getBeurt().getCopyList().get(posRijVeld)
							.get(posSteen2InLijst).getGetal()) {

					}
				} catch (NumberFormatException e) {

					alert.setContentText("Gelieve geen text in te voeren!");
					alert.show();
					txSteenPos2.setText("");

				}

				catch (IndexOutOfBoundsException e) {
					alert.setContentText("De gekozen steen ligt buiten het bereik!");
					alert.show();
					txSteenPos2.setText("");
				}

			}

		});

	}

	private void registreerInput(ActionEvent e) {

		try {
			if (txPosOpVeld.getText().isEmpty() || txSteenPos1.getText().isEmpty() || txSteenPos2.getText().isEmpty())
				throw new LeegVeldException();

			dc.splitsSerieOfRij(posRijVeld, posSteen1InLijst, posSteen2InLijst);
			spelpaneel.bouwVeld(dc.getRummikubspel().getBeurt().getCopyList());

		} catch (LeegVeldException e2) {
			alert.setContentText("Textvelden mogen niet leegzijn!");
			alert.show();
			txPosOpVeld.setText("");
			txSteenPos1.setText("");
			txSteenPos2.setText("");
		}

	}

	private void gaTerugNaarVorigScherm(ActionEvent e) {
		Alert alert = new Alert(Alert.AlertType.WARNING, "Weet je zeker dat je geen stenen \n meer wilt aanleggen?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			stage2.close();
			spelpaneel.getC().getSelectionModel().clearSelection();
		} else {
			e.consume();
		}

	}

}
