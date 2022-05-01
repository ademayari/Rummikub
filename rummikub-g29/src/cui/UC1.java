package cui;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import domein.Gebruiker;
import exceptions.AantalException;
import exceptions.IncorrecteGebruikerException;
import exceptions.ZelfdeGebruikerException;

public class UC1 {

	private DomeinController dc;
	private String gebruikersnaam = "";
	private String wachtwoord = "";
	private int aantal = 0;
	private boolean nietGelukt = true;
	private boolean nietGelukt2 = true;
	private boolean taalTest = true;
	private ResourceBundle r;
	private List<Gebruiker> gebruikersAangemeld;

	private String taal = "";

	Scanner sc = new Scanner(System.in);

	public UC1(DomeinController dc) {
		this.dc = dc;
		gebruikersAangemeld = new ArrayList<>();

	}

	public void start() {

		selecteerTaal();
		r = dc.getTaal().getR();
		inputControleerAantal();
		loginVanGebruikers();
		geefOverzicht();

	}

	public void selecteerTaal() {

		do {
			try {
				System.out.print("English or/of Nederlands: ");
				taal = sc.next();
				String capitol = Character.toString(taal.charAt(0)).toUpperCase();
				taal = capitol + taal.substring(1);
				dc.setTaal(taal);
				taalTest = false;
			} catch (IllegalArgumentException e) {
				System.out.print(e.getMessage());
				sc.nextLine();
			}
		} while (taalTest);

	}

	public void loginVanGebruikers() {
		String gebru = r.getString("user");
		String pass = r.getString("key");
		String incorgebruiker = r.getString("incorg");
		String zelfdegebruiker = r.getString("zelfg");
		int test = 0;

		do {
			do {

				try {

					System.out.print(gebru);
					gebruikersnaam = sc.next();
					System.out.printf(pass);
					wachtwoord = sc.next();

					gebruikersAangemeld.add(new Gebruiker(gebruikersnaam, wachtwoord, 0));

					gebruikersAangemeld.stream().reduce((a, b) -> {
						if (a.equals(b)) {
							gebruikersAangemeld.remove(gebruikersAangemeld.size() - 1);
							throw new ZelfdeGebruikerException();

						}
						return b;

					});

					dc.meldAan(gebruikersnaam, wachtwoord, 0);
					test++;
					nietGelukt2 = false;

				} catch (IncorrecteGebruikerException e) {
					System.out.print(incorgebruiker + "\n");
					sc.nextLine();
				}

				catch (ZelfdeGebruikerException e) {

					System.out.print(zelfdegebruiker + "\n");
					sc.nextLine();

				}

			} while (nietGelukt2);
		} while (test < aantal);
		for (int j = 0; j < aantal; j++) {

		}

	}

	public void inputControleerAantal() {

		String am = r.getString("amount");
		String err = r.getString("error");
		String excep = r.getString("aantalexception");

		do {
			try {

				System.out.print(am);
				aantal = sc.nextInt();

				dc.registreerAantalGebruikers(aantal);
				nietGelukt = false;

			} catch (AantalException e) {
				System.out.println(excep);
				sc.nextLine();
			}

			catch (InputMismatchException e) {
				System.out.println(err);
				sc.nextLine();

			}
		} while (nietGelukt);
	}

	public void geefOverzicht() {
		System.out.print(dc.geefGebruikersNamen());

	}

// applicatie voor een testrun
	//

}
