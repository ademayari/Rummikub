package main;

import domein.DomeinController;
import gui.TaalSelectie;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartUpGUI extends Application {
	@Override
	public void start(Stage primarystage) {
		DomeinController controller = new DomeinController();
		TaalSelectie root = new TaalSelectie(controller);
		Scene scene = new Scene(root, 600, 400);

		primarystage.getIcons().add(new Image(getClass().getResource("/gui/image/testicon.png").toExternalForm()));
		primarystage.setScene(scene);
		Text t = new Text("RummikubGroep29");
		primarystage.setTitle(t.getText());
		primarystage.show();
	}

	public static void main(String... args) {
		Application.launch(StartUpGUI.class, args);
	}
}