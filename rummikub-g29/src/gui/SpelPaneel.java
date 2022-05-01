package gui;

import java.util.List;

import domein.DomeinController;
import domein.Steen;
import exceptions.BevatJokerException;
import exceptions.GeenSerieOfRijException;
import exceptions.IncorrecteBeurtException;
import exceptions.IncorrecteSpelSituatieException;
import exceptions.SerieOfRijException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SpelPaneel extends AnchorPane {
	private DomeinController dc;
	private Text txAanBeurt;
	private ChoiceBox<String> c;
	private Button startBeurt;
	private Button clearStenen;
	private int beurtIndex;
	private HBox box;
	private VBox boxv2;
	private VBox boxv;
	private VBox boxv3;
	private BorderPane bordertest;
	private StackPane stackv;
	private StackPane stackh;
	private StackPane stackv2;
	private List<Steen> stenen;

	public SpelPaneel(DomeinController dc) {
		this.dc = dc;
		bouwSchermOp();
	}

	private void bouwSchermOp() {

		stackv = new StackPane();
		stackh = new StackPane();
		stackv2 = new StackPane();
		box = new HBox();
		boxv = new VBox();
		boxv2 = new VBox();
		boxv3 = new VBox();
		Image imgBord = new Image(getClass().getResource("/gui/image/grootspelbord.png").toExternalForm());
		BackgroundImage backSpelBord = new BackgroundImage(imgBord, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background background = new Background(backSpelBord);
		this.setBackground(background);

		box.setAlignment(Pos.CENTER);
		boxv.setAlignment(Pos.CENTER);
		box.setSpacing(8);
		boxv3.setSpacing(10);

		boxv3.setAlignment(Pos.CENTER);
		boxv3.setMaxWidth(2500);
		boxv3.setMaxHeight(1800);

		StackPane s = new StackPane();
		s.getChildren().addAll(boxv3);

		StackPane s2 = new StackPane();
		s2.getChildren().addAll(box);

		ScrollPane scroll = new ScrollPane();
		ScrollPane scroll2 = new ScrollPane();

		scroll.setPannable(true);
		scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollBarPolicy.NEVER);
		scroll.setContent(s);
		scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");
		scroll.setMaxHeight(750);
		stackv.getChildren().add(scroll);

		scroll2.setPannable(true);
		scroll2.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroll2.setVbarPolicy(ScrollBarPolicy.NEVER);
		scroll2.setContent(s2);
		scroll2.setStyle("-fx-background: transparent; -fx-background-color: transparent; ");
		stackh.getChildren().add(scroll2);

		s2.setMaxHeight(100);

		scroll2.setMaxWidth(900);
		StackPane.setAlignment(s2, Pos.BOTTOM_CENTER);

		AnchorPane.setTopAnchor(stackv, 175d);
		AnchorPane.setLeftAnchor(stackv, 75d);
		AnchorPane.setRightAnchor(stackv, 75d);

		AnchorPane.setTopAnchor(stackh, 55d);
		AnchorPane.setLeftAnchor(stackh, 75d);
		AnchorPane.setRightAnchor(stackh, 75d);

		AnchorPane.setTopAnchor(stackv2, 50d);
		AnchorPane.setRightAnchor(stackv2, 40d);

		stackv2.getChildren().addAll(boxv2);

		this.getChildren().add(stackv);
		this.getChildren().add(stackh);
		this.getChildren().add(stackv2);

		txAanBeurt = new Text();
		txAanBeurt.setFont(Font.font("Arial", FontWeight.BOLD, 35));
		txAanBeurt.setFill(Color.WHITE);
		txAanBeurt.setStroke(Color.BLACK);
		txAanBeurt.setStrokeWidth(1);

		AnchorPane.setTopAnchor(txAanBeurt, 5d);
		AnchorPane.setLeftAnchor(txAanBeurt, 800d);

		Alert alert = new Alert(AlertType.WARNING);

		TilePane r = new TilePane();
		AnchorPane.setLeftAnchor(r, 20d);
		AnchorPane.setTopAnchor(r, 10d);

		startBeurt = new Button("start beurt");
		AnchorPane.setLeftAnchor(startBeurt, 20d);
		AnchorPane.setTopAnchor(startBeurt, 10d);

		clearStenen = new Button("leeg stenen \n Gebruiker");
		AnchorPane.setTopAnchor(clearStenen, 10d);
		AnchorPane.setRightAnchor(clearStenen, 170d);

		this.getChildren().addAll(txAanBeurt, r, startBeurt, clearStenen);

		startBeurt.setOnAction(this::startBeurt);
		clearStenen.setOnAction(this::leegStenen);

		String acties[] = { "Leg Aan", "Splits serie of rij", "Vervang joker", "Verplaats naar werkveld", "Reset beurt",
				"Beurt stoppen" };

		c = new ChoiceBox(FXCollections.observableArrayList(acties));
		r.getChildren().add(c);

		c.setVisible(false);

		c.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {

			switch (String.format("%s", v.getValue())) {

			case "Beurt stoppen":

				try {

					dc.stopBeurt();
					box.getChildren().clear();
					c.setVisible(false);
					startBeurt.setVisible(true);
					c.getSelectionModel().clearSelection();
					if (dc.isEindeSpel() == true) {
						Alert alertInfo = new Alert(AlertType.INFORMATION);
						alertInfo.setContentText(dc.geefScores());
						alertInfo.show();
						StartPaneel startPaneel = new StartPaneel(dc);
						this.getScene().setRoot(startPaneel);
					}

				} catch (IncorrecteSpelSituatieException e) {

					alert.setContentText("Er moet minstens 30 aan waarde uitgelegd worden in de eerste beurt");
					alert.show();
					c.getSelectionModel().clearSelection();
					resetVeld();

				} catch (BevatJokerException e) {
					alert.setContentText(
							"Er ligt een joker op het spelbord dit is niet toegelaten bij het eerste keer aanleggen");
					alert.show();
					c.getSelectionModel().clearSelection();
					resetVeld();

				} catch (SerieOfRijException e) {
					alert.setContentText("Er moet minstens 3 steentjes per rij of serie uitgelegd worden");
					alert.show();
					c.getSelectionModel().clearSelection();
					resetVeld();
				}

				catch (IncorrecteBeurtException e) {
					alert.setContentText("Het werkveld moet leeg zijn vooraleer de beurt gestopt kan worden");
					alert.show();
					c.getSelectionModel().clearSelection();

				}

				catch (GeenSerieOfRijException e) {
					alert.setContentText("Een serie op het spelbord voldoet niet aan de regels");
					alert.show();
					c.getSelectionModel().clearSelection();
				}

				break;

			case "Verplaats naar werkveld":
				Stage newWindow = new Stage();
				newWindow.setResizable(false);
				newWindow.getIcons().add(new Image(getClass().getResource("/gui/image/testicon.png").toExternalForm()));

				VerplaatsenWerkveldPaneel paneel = new VerplaatsenWerkveldPaneel(dc, newWindow, this);
				Scene scene = new Scene(paneel, 390, 180);
				newWindow.setScene(scene);
				newWindow.show();

				break;
			case "Leg Aan":

				Stage secondNewWindow = new Stage();
				secondNewWindow.setResizable(false);
				secondNewWindow.getIcons()
						.add(new Image(getClass().getResource("/gui/image/testicon.png").toExternalForm()));

				LegAanPaneel paneel2 = new LegAanPaneel(dc, secondNewWindow, this);
				Scene scene2 = new Scene(paneel2, 450, 280);
				secondNewWindow.setScene(scene2);
				secondNewWindow.show();

				break;
			case "Vervang joker":
				Stage thirdNewWindow = new Stage();
				thirdNewWindow.setResizable(false);
				thirdNewWindow.getIcons()
						.add(new Image(getClass().getResource("/gui/image/testicon.png").toExternalForm()));

				vervangJokerPaneel paneel3 = new vervangJokerPaneel(dc, thirdNewWindow, this);
				Scene scene3 = new Scene(paneel3, 240, 400);
				thirdNewWindow.setScene(scene3);
				thirdNewWindow.show();

				break;
			case "Splits serie of rij":
				Stage fourthNewWindow = new Stage();
				fourthNewWindow.setResizable(false);
				fourthNewWindow.getIcons()
						.add(new Image(getClass().getResource("/gui/image/testicon.png").toExternalForm()));

				splitsSerieOfRijPaneel paneel4 = new splitsSerieOfRijPaneel(dc, fourthNewWindow, this);
				Scene scene4 = new Scene(paneel4, 370, 250);
				fourthNewWindow.setScene(scene4);
				fourthNewWindow.show();

				break;
			case "Reset beurt":
				resetVeld();
				break;

			}

		});
	}

	private void startBeurt(ActionEvent e) {

		txAanBeurt.setText(dc.geefNaamGebruikerAanBeurt());
		c.setVisible(true);

		dc.startBeurt();
		stenen = dc.getRummikubspel().getBeurt().getGebruiker().getStenen();
		box.getChildren().clear();
		bouwLijsten(stenen);
		startBeurt.setVisible(false);
		c.getSelectionModel().clearSelection();

	}

	private void leegStenen(ActionEvent e) {

		System.out.print(
				dc.getRummikubspel().getGebruikers().get(dc.getRummikubspel().getGebruikerAanBeurt() - 1).getStenen());
		dc.getRummikubspel().getGebruikers().get(dc.getRummikubspel().getGebruikerAanBeurt() - 1).getStenen().clear();
		System.out.print(
				dc.getRummikubspel().getGebruikers().get(dc.getRummikubspel().getGebruikerAanBeurt() - 1).getStenen());
		box.getChildren().clear();

	}

	public void bouwNodeOp(int lijst, int posInLijst) {

		Steen steen = dc.getRummikubspel().getBeurt().getCopyList().get(lijst).get(posInLijst);

		String kleur = String.format("%s", steen.getKleur());
		String test = String.format("%d", steen.getGetal());
		StackPane stack = kleurSelectie(kleur, test);
		boxv2.getChildren().add(stack);

	}

	// gewoon lijstjes maken en toevoegen aan een bestaande vbox

	public void bouwVeld(List<List<Steen>> spelBord) {

		for (List<Steen> lijst : spelBord) {
			int index = 0;
			HBox htest = new HBox();
			htest.setMaxWidth(1175);
			htest.setAlignment(Pos.TOP_LEFT);
			for (int i = 0; i < lijst.size(); i++) {
				String kleur = String.format("%s", lijst.get(index).getKleur());
				String test = String.format("%d", lijst.get(index).getGetal());
				StackPane stack = kleurSelectie(kleur, test);
				htest.setSpacing(8);
				htest.getChildren().add(stack);

				index++;

			}
			boxv3.getChildren().add(htest);

		}

	}

	public void bouwLijsten(List<Steen> stenen) {

		int index = 0;
		for (int i = 0; i < stenen.size(); i++) {
			String kleur = String.format("%s", stenen.get(index).getKleur());
			String test = String.format("%d", stenen.get(index).getGetal());
			Text t = new Text(test);
			StackPane stack = kleurSelectie(kleur, test);
			box.getChildren().add(stack);

			index++;
		}

	}

	public StackPane kleurSelectie(String kleur, String test) {
		Text t = new Text(test);
		ImageView img = new ImageView(getClass().getResource("/gui/image/joker.png").toExternalForm());
		StackPane stack = new StackPane();
		switch (kleur) {
		case "RED":
			t.setFill(Color.RED);
			break;
		case "BLACK":
			t.setFill(Color.BLACK);
			break;
		case "YELLOW":
			t.setFill(Color.GOLD);
			break;
		case "BLUE":
			t.setFill(Color.BLUE);
			break;
		case "JOKER":
			t.setFill(Color.BLACK);
			break;

		}
		t.setFont(Font.font("Verdana", 32));

		Rectangle rect = new Rectangle(50, 70, 50, 70);
		rect.setStroke(Color.BLACK);
		rect.setFill(Color.WHITE);
		rect.setStrokeWidth(2);

		stack.getChildren().addAll(rect, t);
		if (kleur.equals("JOKER")) {
			stack.getChildren().add(img);

		}

		return stack;
	}

	public void resetVeld() {
		dc.resetBeurt();
		box.getChildren().clear();
		boxv2.getChildren().clear();
		boxv3.getChildren().clear();
		bouwLijsten(dc.getRummikubspel().getBeurt().getGebruikerBackupIntact());
		bouwVeld(dc.getRummikubspel().getBeurt().getCopyList());
	}

	public StackPane getStackH() {
		return stackh;
	}

	public HBox getBox() {
		return box;
	}

	public VBox getBoxv2() {
		return boxv2;
	}

	public VBox getBoxv3() {
		return boxv3;
	}

	public BorderPane getBordertest() {
		return bordertest;
	}

	public ChoiceBox<String> getC() {
		return c;

	}

}
