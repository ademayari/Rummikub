package cui;

import java.util.Scanner;

import domein.DomeinController;

public class UC2 {
	private DomeinController dc;

	public UC2(DomeinController dc) {
		this.dc = dc;

	}

	public void start() {
		startSpel();
		Scanner sc = new Scanner(System.in);
		String input = "";

		do {
			System.out.println("----------------------------");
			System.out.print(dc.geefNaamGebruikerAanBeurt());
			System.out.print("Beurt stoppen?");
			input = sc.next();

		} while (dc.isEindeSpel() == false);

		geefScoreBord();

	}

	public void startSpel() {
		dc.startSpel();

	}

	public void geefScoreBord() {
		dc.geefScores();
	}

}
