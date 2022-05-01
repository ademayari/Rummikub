package gui;

import java.util.List;
import java.util.Optional;

import domein.DomeinController;
import domein.Steen;
import exceptions.BuitenBereikException;
import exceptions.GeenSerieOfRijException;
import exceptions.LeegVeldException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
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

public class LegAanPaneel extends GridPane {

	private DomeinController dc;
	private Stage stage;
	private SpelPaneel spelpaneel;

	private int posLijstInVeld;
	private int posSteenInBezit;
	private TextField txKeuzeSteen;
	private TextField txKeuzeVeld;
	private String keuzeTijdelijk;
	private String keuzeTijdelijk2;
	private ChoiceBox<String> choiceBox2;
	private ChoiceBox<String> choiceBox3;
	private Label lblKeuze;
	private Label lblKeuzeVeld;
	private Label lblSteenInBezit;
	private Button btnTerug;
	private Button btnConfirmatie;
	private Alert alert;
	private List<Steen> steenLijst;

	public LegAanPaneel(DomeinController dc, Stage stage, SpelPaneel spelpaneel) {
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

		String legAanMogelijkheden[] = { "van werkveld naar het spelbord", "van persoonlijk bezit naar het spelbord" };
		String nieuweRijOfSerieOfBestaandAanleggen[] = { "nieuwe rij of serie starten",
				"bestaande rij of serie aanvullen" };

		Label lblMogelijkheden = new Label("Uit welke lijst wil je \n een steen aanleggen?");
		choiceBox2 = new ChoiceBox(FXCollections.observableArrayList(legAanMogelijkheden));
		choiceBox3 = new ChoiceBox(FXCollections.observableArrayList(nieuweRijOfSerieOfBestaandAanleggen));
		lblKeuze = new Label("Wat wil je doen \n met deze steen?");
		lblKeuzeVeld = new Label("Aan welke lijst of serie \n wil je een steen toevoegen?");
		btnTerug = new Button("Keer terug naar \n het spelbord");
		btnConfirmatie = new Button("Bevestig je input");
		lblSteenInBezit = new Label("Geef de steen uit \n de gekozen lijst in");
		alert = new Alert(AlertType.WARNING);
		txKeuzeSteen = new TextField();
		txKeuzeVeld = new TextField();

		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setGridLinesVisible(false);
		choiceBox3.setMaxWidth(Double.MAX_VALUE);
		btnConfirmatie.setMaxHeight(Double.MAX_VALUE);
		setHalignment(btnConfirmatie, HPos.RIGHT);

		lblMogelijkheden.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 12));
		lblKeuze.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 12));
		lblKeuzeVeld.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 12));
		lblSteenInBezit.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 12));

		this.add(lblKeuze, 0, 1);
		this.add(lblKeuzeVeld, 0, 3);
		this.add(txKeuzeVeld, 1, 3);
		this.add(btnTerug, 0, 7);
		this.add(btnConfirmatie, 1, 7);
		this.add(lblSteenInBezit, 0, 2);
		this.add(txKeuzeSteen, 1, 2);
		this.add(lblMogelijkheden, 0, 0);
		this.add(choiceBox3, 1, 1);
		this.add(choiceBox2, 1, 0);

		lblKeuze.setVisible(true);
		lblKeuzeVeld.setVisible(false);
		txKeuzeVeld.setVisible(false);
		choiceBox3.setVisible(true);
		lblSteenInBezit.setVisible(false);
		txKeuzeSteen.setVisible(false);
		btnTerug.setVisible(true);
		btnConfirmatie.setVisible(false);

		btnTerug.setOnAction(this::gaTerugNaarVorigScherm);
		btnConfirmatie.setOnAction(this::registreerInput);

		stage.setOnCloseRequest(evt -> {
			Alert alert = new Alert(Alert.AlertType.WARNING,
					"Weet je zeker dat je geen stenen \n meer wilt aanleggen?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				stage.close();
				spelpaneel.getC().getSelectionModel().clearSelection();

			} else {
				evt.consume();
			}
		});

	}

	private void invoerInput() {

		choiceBox2.getSelectionModel().selectedItemProperty().addListener((v, oldValue, keuze) -> {

			keuzeTijdelijk = keuze;

		});

		choiceBox3.getSelectionModel().selectedItemProperty().addListener((v, oldValue, keuze) -> {

			if (keuze != "nieuwe rij of serie starten") {

				lblSteenInBezit.setVisible(true);
				txKeuzeSteen.setVisible(true);
				lblKeuzeVeld.setVisible(true);
				txKeuzeVeld.setVisible(true);
				btnTerug.setVisible(true);
				btnConfirmatie.setVisible(true);

			} else {

				lblSteenInBezit.setVisible(true);
				txKeuzeSteen.setVisible(true);
				lblKeuzeVeld.setVisible(false);
				txKeuzeVeld.setVisible(false);
				btnTerug.setVisible(true);
				btnConfirmatie.setVisible(true);

			}

			keuzeTijdelijk2 = keuze;

		});

		txKeuzeVeld.textProperty().addListener((observable, oldVal, indexLijstInVeld) -> {

			List<List<Steen>> spelbordVoorlopig = dc.getRummikubspel().getBeurt().getCopyList();

			if (!indexLijstInVeld.isEmpty()) {

				try {
					posLijstInVeld = Integer.parseInt(indexLijstInVeld);

					if (posLijstInVeld > spelbordVoorlopig.size())
						throw new BuitenBereikException();

				} catch (BuitenBereikException e) {
					alert.setContentText("Je kan geen steen toevoegen aan een lijst \n die nog niet op het veld ligt!");
					alert.show();
					txKeuzeVeld.setText("");

				} catch (NumberFormatException e) {
					alert.setContentText("Gelieve geen text in te voeren!");
					alert.show();
					txKeuzeVeld.setText("");
				}

			}

		});

		txKeuzeSteen.textProperty().addListener((observable, oldVal, indexSteenBezit) -> {

			if (!indexSteenBezit.isEmpty()) {
				List<Steen> stenenGebruiker = dc.getRummikubspel().getBeurt().getGebruikerBackup();
				List<Steen> stenenWerkveld = dc.getRummikubspel().getBeurt().getWerkveld();

				try {

					switch (keuzeTijdelijk) {

					case "van persoonlijk bezit naar het spelbord":

						steenLijst = stenenGebruiker;

						break;
					case "van werkveld naar het spelbord":
						steenLijst = stenenWerkveld;

						break;
					}
					posSteenInBezit = Integer.parseInt(indexSteenBezit);
					if (posSteenInBezit > steenLijst.size())
						throw new BuitenBereikException();

				} catch (BuitenBereikException e) {
					alert.setContentText("De gekozen steen ligt buiten het bereik!");
					alert.show();
					txKeuzeSteen.setText("");
				}

				catch (NumberFormatException e) {

					alert.setContentText("Gelieve geen text in te voeren!");
					alert.show();
					txKeuzeSteen.setText("");

				}

			}

		});

	}

	private void registreerInput(ActionEvent e) {

		try {
			if (keuzeTijdelijk2 == "nieuwe rij of serie starten") {
				if (txKeuzeSteen.getText().isEmpty())
					throw new LeegVeldException();
			}

			else {
				if (txKeuzeSteen.getText().isEmpty() || txKeuzeVeld.getText().isEmpty())
					throw new LeegVeldException();
			}
			txKeuzeSteen.setText("");
			txKeuzeVeld.setText("");

			List<List<Steen>> spelbordVoorlopig = dc.getRummikubspel().getBeurt().getCopyList();

			if (!spelbordVoorlopig.isEmpty()) {

				switch (keuzeTijdelijk2) {

				case "nieuwe rij of serie starten":
					if (txKeuzeSteen.getText() != "")
						dc.legAan(posSteenInBezit, 0, keuzeTijdelijk, keuzeTijdelijk2);
					grafischeVeranderingenLegAan();
					break;
				case "bestaande rij of serie aanvullen":
					if (txKeuzeSteen.getText() != "" && txKeuzeVeld.getText() != "")

						try {
							dc.legAan(posSteenInBezit, posLijstInVeld, keuzeTijdelijk, keuzeTijdelijk2);
							grafischeVeranderingenLegAan();

						} catch (GeenSerieOfRijException e1) {
							alert.setContentText(
									"De steen die je probeert aan te leggen \n voldoet niet aan de regels voor een serie of rij!");
							alert.show();
						}

					break;

				}

			} else {
				dc.legAan(posSteenInBezit, posLijstInVeld, keuzeTijdelijk, keuzeTijdelijk2);
				grafischeVeranderingenLegAan();

			}
		} catch (LeegVeldException e1) {
			alert.setContentText("De textvelden mogen niet leegzijn!");
			alert.show();
			txKeuzeSteen.setText("");
			txKeuzeVeld.setText("");

		}

	}

	private void gaTerugNaarVorigScherm(ActionEvent e) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
				"Weet je zeker dat je geen stenen \n meer wilt aanleggen?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			stage.close();
			spelpaneel.getC().getSelectionModel().clearSelection();

		} else {
			e.consume();
		}

	}

	private void grafischeVeranderingenLegAan() {

		List<List<Steen>> spelbordVoorlopig = dc.getRummikubspel().getBeurt().getCopyList();

		switch (keuzeTijdelijk) {

		case "van persoonlijk bezit naar het spelbord":

			spelpaneel.getBox().getChildren().remove(posSteenInBezit);
			spelpaneel.getBoxv3().getChildren().clear();
			spelpaneel.bouwVeld(spelbordVoorlopig);

			break;
		case "van werkveld naar het spelbord":
			spelpaneel.getBoxv2().getChildren().remove(posSteenInBezit);
			spelpaneel.getBoxv3().getChildren().clear();
			spelpaneel.bouwVeld(spelbordVoorlopig);

			break;
		}

	}

}
