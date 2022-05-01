package main;

import cui.UC1;
import cui.UC2;
import domein.DomeinController;

public class Startup {

	public static void main(String[] args) {
		DomeinController dc = new DomeinController();
		new UC1(dc).start();
		new UC2(dc).start();
		// start de applicatie
	}
}
