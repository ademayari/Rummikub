package gui;

import java.util.List;
import java.util.Optional;

import domein.DomeinController;
import domein.Steen;
import exceptions.BevatJokerException;
import exceptions.BuitenBereikException;
import exceptions.JokerException;
import exceptions.LeegVeldException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
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

public class vervangJokerPaneel extends GridPane {

	private DomeinController dc;
	private Stage stage2;
	private SpelPaneel spelpaneel;

	private int posInVeld;
	private int posJokerInLijstOpVeld;
	private int posSteenInBezit;
	private TextField txKeuzeTeVervangenSteen;
	private TextField txPosJoker;
	private TextField txPosLijstJoker;
	private String keuzeTijdelijk2;
	private Button btnConfirmatie;
	private Button btnTerug;
	private Label lblPosJoker;
	private Label lblPosLijstInGemVeld;
	private Label lblPosSteenInLijst;
	private Label choiceBox3VervangJoker;
	private Alert alert;
	private List<Steen> steenLijst;

	private ChoiceBox<String> choiceBox3;

	public vervangJokerPaneel(DomeinController dc, Stage stage2, SpelPaneel spelpaneel) {
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

		String vervangJokerMogelijkheden[] = { "steen van werkveld", "steen van persoonlijk bezit" };
		choiceBox3 = new ChoiceBox(FXCollections.observableArrayList(vervangJokerMogelijkheden));
		this.add(choiceBox3, 0, 1, 2, 1);

		txKeuzeTeVervangenSteen = new TextField();
		this.add(txKeuzeTeVervangenSteen, 0, 3, 2, 1);

		txPosLijstJoker = new TextField();
		this.add(txPosLijstJoker, 0, 5, 2, 1);

		txPosJoker = new TextField();
		this.add(txPosJoker, 0, 7, 2, 1);

		choiceBox3VervangJoker = new Label("Wil je een steen gebruiken van je \n persoonlijk bezit of werkveld?");
		this.add(choiceBox3VervangJoker, 0, 0, 2, 1);

		lblPosJoker = new Label("Geef de positie in van de steen \n die je wilt gebruiken");
		this.add(lblPosJoker, 0, 2, 2, 1);

		lblPosLijstInGemVeld = new Label("Waar op het spelbord wil je \n een joker vervangen?");
		this.add(lblPosLijstInGemVeld, 0, 4, 2, 1);

		lblPosSteenInLijst = new Label("Waar in deze rij of serie \n wil je de joker vervangen?");
		this.add(lblPosSteenInLijst, 0, 6, 2, 1);

		btnConfirmatie = new Button("Bevestig je \n input");
		this.add(btnConfirmatie, 0, 10);

		btnTerug = new Button("Ga terug naar \n het hoofdscherm");
		this.add(btnTerug, 1, 10);

		alert = new Alert(AlertType.WARNING);

		lblPosJoker.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 12));
		lblPosLijstInGemVeld.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 12));
		lblPosSteenInLijst.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 12));
		choiceBox3VervangJoker.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 12));

		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setGridLinesVisible(false);
		choiceBox3.setMaxWidth(Double.MAX_VALUE);

		lblPosJoker.setVisible(false);
		lblPosLijstInGemVeld.setVisible(false);
		lblPosSteenInLijst.setVisible(false);
		btnConfirmatie.setVisible(false);
		btnTerug.setVisible(false);

		txKeuzeTeVervangenSteen.setVisible(false);
		txPosLijstJoker.setVisible(false);
		txPosJoker.setVisible(false);

		btnConfirmatie.setOnAction(this::registreerInput);
		btnTerug.setOnAction(this::gaTerugNaarVorigScherm);

		stage2.setOnCloseRequest(evt -> {
			Alert alert = new Alert(Alert.AlertType.WARNING,
					"Weet je zeker dat je geen stenen \n joker meer wilt vervangen?");
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

		choiceBox3.getSelectionModel().selectedItemProperty().addListener((v2, oldValue2, keuze2) -> {

			try {

				if (dc.getRummikubspel().getBeurt().getCopyList().stream()
						.anyMatch(list -> list.contains(new Steen()))) {
					keuzeTijdelijk2 = keuze2;
					lblPosJoker.setVisible(true);
					lblPosLijstInGemVeld.setVisible(true);
					lblPosSteenInLijst.setVisible(true);
					btnConfirmatie.setVisible(true);
					btnTerug.setVisible(true);
					txKeuzeTeVervangenSteen.setVisible(true);
					txPosLijstJoker.setVisible(true);
					txPosJoker.setVisible(true);
				} else {
					btnTerug.setVisible(true);
					throw new BevatJokerException();
				}

			} catch (BevatJokerException e4) {

				alert.setContentText("Het spelbord bevat geen joker! \n selecteer een andere actie!");
				alert.show();
				txKeuzeTeVervangenSteen.setText("");
				txPosLijstJoker.setText("");
				txPosJoker.setText("");

			}

		});

		txKeuzeTeVervangenSteen.textProperty().addListener((observable2, oldVal2, posSteenInBezitOfWerkveld) -> {

			if (!posSteenInBezitOfWerkveld.equals("")) {
				List<Steen> stenenGebruiker = dc.getRummikubspel().getBeurt().getGebruikerBackup();
				List<Steen> stenenWerkveld = dc.getRummikubspel().getBeurt().getWerkveld();
				try {
					switch (keuzeTijdelijk2) {

					case "steen van persoonlijk bezit":

						steenLijst = stenenGebruiker;

						break;
					case "steen van werkveld":
						steenLijst = stenenWerkveld;
					}
					posSteenInBezit = Integer.parseInt(posSteenInBezitOfWerkveld);
					if (posSteenInBezit > steenLijst.size())

						throw new BuitenBereikException();
				} catch (NumberFormatException e) {

					alert.setContentText("Gelieve geen text in te voeren!");
					alert.show();
					txKeuzeTeVervangenSteen.setText("");
				} catch (BuitenBereikException e) {

					alert.setContentText("De gekozen steen ligt buiten het bereik!");
					alert.show();
					txKeuzeTeVervangenSteen.setText("");

				}

			}

		});

		txPosLijstJoker.textProperty().addListener((observable2, oldVal3, posLijstInGemVeld) -> {

			if (!posLijstInGemVeld.equals("")) {
				try {
					posInVeld = Integer.parseInt(posLijstInGemVeld);
					if (posInVeld > dc.getRummikubspel().getBeurt().getCopyList().size())
						throw new BuitenBereikException();
				} catch (NumberFormatException e) {

					alert.setContentText("Gelieve geen text in te voeren!");
					alert.show();
					txPosLijstJoker.setText("");

				}

				catch (BuitenBereikException e) {
					alert.setContentText("De gekozen steen ligt buiten het bereik!");
					alert.show();
					txPosLijstJoker.setText("");
				}

			}

		});

		txPosJoker.textProperty().addListener((observable2, oldVal2, posJokerInLijst) -> {

			if (!posJokerInLijst.equals("")) {

				try {
					posJokerInLijstOpVeld = Integer.parseInt(posJokerInLijst);
					if (posJokerInLijstOpVeld <= dc.getRummikubspel().getBeurt().getCopyList().get(posInVeld).size()) {

					}

				} catch (NumberFormatException e) {

					alert.setContentText("Gelieve geen text in te voeren!");
					alert.show();
					txPosJoker.setText("");

				}

				catch (IndexOutOfBoundsException e) {

					alert.setContentText("De gekozen steen ligt buiten het bereik!");
					alert.show();
					txPosJoker.setText("");

				}

			}

		});

	}

	private void registreerInput(ActionEvent e) {

		try {

			if (txKeuzeTeVervangenSteen.getText().isEmpty() || txPosLijstJoker.getText().isEmpty()
					|| txPosJoker.getText().isEmpty())
				throw new LeegVeldException();
			txKeuzeTeVervangenSteen.setText("");
			txPosLijstJoker.setText("");
			txPosJoker.setText("");

			switch (keuzeTijdelijk2) {

			case "steen van persoonlijk bezit":

				spelpaneel.getBox().getChildren().remove(posSteenInBezit);

				break;
			case "steen van werkveld":

				spelpaneel.getBoxv2().getChildren().remove(posSteenInBezit);
				break;
			}
			spelpaneel.bouwNodeOp(posInVeld, posJokerInLijstOpVeld);
			dc.vervangJoker(posInVeld, posSteenInBezit, posJokerInLijstOpVeld, keuzeTijdelijk2);
			spelpaneel.getBoxv3().getChildren().clear();
			spelpaneel.bouwVeld(dc.getRummikubspel().getBeurt().getCopyList());

		} catch (LeegVeldException e2) {
			alert.setContentText("De textvelden mogen niet leegzijn!");
			alert.show();
			txKeuzeTeVervangenSteen.setText("");
			txPosLijstJoker.setText("");
			txPosJoker.setText("");
		}

		catch (JokerException e3) {
			alert.setContentText("Je kan de joker niet vervangen met deze steen!");
			alert.show();
			txKeuzeTeVervangenSteen.setText("");
			txPosLijstJoker.setText("");
			txPosJoker.setText("");

		}

	}

	private void gaTerugNaarVorigScherm(ActionEvent e) {
		Alert alert = new Alert(Alert.AlertType.WARNING, "Weet je zeker dat je geen jokers \n meer wilt vervangen?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			stage2.close();
			spelpaneel.getC().getSelectionModel().clearSelection();
		} else {
			e.consume();
		}

	}

}
