package domein;

import java.util.Locale;
import java.util.ResourceBundle;

public class Taal {
	/**
	 * Nieuwe string van de gekozen taal.
	 */
	private String taal;
	/**
	 * Een nieuwe resourcebundle.
	 */
	private ResourceBundle r;

	/**
	 * <h> Ophalen ResourceBundle </h>
	 * <p>
	 * Gaat aan de hand van de ingegeven taal de juiste resource bundle opvragen en
	 * deze retourneren voor gebruik in de applicatie.
	 * </p>
	 * 
	 * @param taal de gekozen taal
	 */
	public Taal(String taal) {
		setTaal(taal);
		if (taal.equals("Nederlands")) {
			String nllang = "nl";
			String nlcountry = "NL";
			Locale l = new Locale(nllang, nlcountry);
			r = ResourceBundle.getBundle("domein/resource_bundle_nl", l);

		} else if (taal.equals("English")) {
			String enlang = "en";
			String encountry = "EN";

			Locale l2 = new Locale(enlang, encountry);
			r = ResourceBundle.getBundle("domein/resource_bundle_en", l2);

		}
		return;

	}

	/**
	 * <h> ResourceBundle getter </h>
	 * <p>
	 * Gaat de gevraagde ResourceBundle ophalen.
	 * </p>
	 * 
	 * @return de gevraagde ResourceBundle
	 */
	public ResourceBundle getR() {
		return r;
	}

	/**
	 * <h> Getter voor de Taalkeuze </h>
	 * <p>
	 * Getter voor de selectie van de taal, die vooraf het spel begint word gekozen
	 * door de gebruiker. Deze laat toe om de keuze van de taal te gebruiken bij de
	 * andere klassen voor internationalisatie aan de hand van resourcebundles.
	 * </p>
	 * 
	 * @return de gekozen taal, om de juiste resourcebundles te kunnen gebruiken
	 */

	public String getTaal() {
		return taal;
	}

	/**
	 * <h> Setter voor de Taalkeuze </h>
	 * <p>
	 * Setter voor de selectie van de taal. Gaat de gekozen taal instellen als
	 * default taal voor de desbetreffende use cases in deze applicatie.
	 * </p>
	 * 
	 * 
	 * @param taal de keuze van de taal die word geselecteerd
	 */
	public void setTaal(String taal) {
		this.taal = taal;
	}

	/*
	 * <h> Gaat de hashcode returnen </h>
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((taal == null) ? 0 : taal.hashCode());
		return result;
	}

	/**
	 * <h> Equals methode voor taal object </h>
	 * <p>
	 * Gaat heel het object, dus met de hashcode vergelijken.
	 * </p>
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Taal other = (Taal) obj;
		if (taal == null) {
			if (other.taal != null)
				return false;
		} else if (!taal.equals(other.taal))
			return false;
		return true;
	}

}
