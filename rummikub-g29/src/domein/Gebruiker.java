package domein;

import java.util.ArrayList;
import java.util.List;

public class Gebruiker {
	/**
	 * Een nieuwe String met het wachtwoord van de gebruiker.
	 */
	private String wachtwoord;
	/**
	 * Een nieuwe String met de gebruikersnaam van de gebruiker.
	 */
	private String gebruikersnaam;
	/**
	 * Een nieuwe lijst van stenen.
	 */
	private List<Steen> stenen;

	/**
	 * De score van de gebruiker.
	 */
	private int score;

	/**
	 * <h> Aanmaken nieuwe Gebruiker </h>
	 * <p>
	 * Maakt met de aangemelde info van de gebruiker een nieuwe gebruiker met een
	 * lege score en een lege lijst die zijn eigen stenen lijst word bij aanvang van
	 * het spel.
	 * </p>
	 * 
	 * @param gebruikersnaam gebruikersnaam van de aangemelde gebruiker
	 * @param wachtwoord     wachtwoord van de aangemelde gebruiker
	 * @param score          score van de aangemelde gebruiker
	 */

	public Gebruiker(String gebruikersnaam, String wachtwoord, int score) {
		setGebruikersnaam(gebruikersnaam);
		setWachtwoord(wachtwoord);
		this.score = score;
		stenen = new ArrayList<>();

	}

	/**
	 * <h> Methode Geef Score </h>
	 * <p>
	 * Geeft de score van de gebruiker, die momenteel aangemeld is en een spel
	 * speelt, weer.
	 * </p>
	 * 
	 * @return de spelscore van de aangemelde gebruiker in het huidig spel
	 */
	public int geefScore() {
		return score;
	}

	/**
	 * <h> Wachtwoord getter </h>
	 * <p>
	 * Gaat het wachtwoord van een aangemelde gebruiker ophalen.
	 * </p>
	 * 
	 * @return het wachtwoord van een aangemelde gebruiker
	 */
	public String getWachtwoord() {
		return wachtwoord;
	}

	/**
	 * <h> Wachtwoord setter </h>
	 * <p>
	 * Gaat het wachtwoord van een aangemelde gebruiker gebruiken als huidig
	 * wachtwoord voor dezelfde gebruiker die het spel gaat spelen.
	 * </p>
	 * 
	 * @param wachtwoord wachtwoord van de aangemelde gebruiker
	 * 
	 */
	private void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}

	/**
	 * <h> Gebruikersnaam getter </h>
	 * <p>
	 * Gaat de gebruikersnaam van een aangemelde gebruiker ophalen.
	 * </p>
	 * 
	 * @return de gebruikersnaam van een aangemelde gebruiker
	 */
	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	/**
	 * <h> Gebruikersnaam setter </h>
	 * <p>
	 * Gaat de gebruikersnaam van een aangemelde gebruiker gebruiken als huidige
	 * gebruikersnaam voor dezelfde gebruiker die het spel gaat spelen.
	 * </p>
	 * 
	 * @param gebruikersnaam gebruikersnaam van de aangemelde gebruiker
	 * 
	 */
	private void setGebruikersnaam(String gebruikersnaam) {
		this.gebruikersnaam = gebruikersnaam;
	}

	/**
	 * <h> Methode maak Stenen Bord </h>
	 * <p>
	 * Maakt een nieuw stenenbord aan.
	 * </p>
	 * 
	 * @param stenenLijst lijst met stenen
	 */
	public void maakStenenBord(List<Steen> stenenLijst) {
		this.stenen = stenenLijst;

	}

	/**
	 * <h> Stenen getter </h>
	 * <p>
	 * Gaat de lijst met steen objecten gaan ophalen.
	 * </p>
	 * 
	 * @return een lijst van steen objecten
	 */
	public List<Steen> getStenen() {
		return this.stenen;
	}

	/**
	 * <h> toString Methode voor gebruikersnaam </h>
	 * <p>
	 * Gaat een String teruggeven van de naam van de gebruiker met een custom tekst
	 * ervoor.
	 * </p>
	 */
	public String toString() {

		return String.format("%s is aangemeld", gebruikersnaam);

	}

	/**
	 * <h> Methode update stenen </h>
	 * <p>
	 * ??????????
	 * </p>
	 * 
	 * @param lijstje een lijstje van Integers
	 */
	public void updateStenen(List<Integer> lijstje) {

		for (int i : lijstje) {
			stenen.remove(i);
		}

	}

	/*
	 * <h> Gaat de hashcode returnen </h>
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gebruikersnaam == null) ? 0 : gebruikersnaam.hashCode());
		result = prime * result + ((wachtwoord == null) ? 0 : wachtwoord.hashCode());
		return result;
	}

	/**
	 * <h> Equals methode voor gebruiker object </h>
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
		Gebruiker other = (Gebruiker) obj;
		if (gebruikersnaam == null) {
			if (other.gebruikersnaam != null)
				return false;
		} else if (!gebruikersnaam.equals(other.gebruikersnaam))
			return false;
		if (wachtwoord == null) {
			if (other.wachtwoord != null)
				return false;
		} else if (!wachtwoord.equals(other.wachtwoord))
			return false;
		return true;
	}

}