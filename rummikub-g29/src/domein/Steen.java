package domein;

public class Steen {

	public enum Kleur {
		RED, BLACK, YELLOW, BLUE, JOKER
	}

	/**
	 * Een nieuw getal.
	 */
	private int getal;
	/**
	 * Een bepaalde kleur vanuit de Kleur enum.
	 */
	private final Kleur kleur;

	/**
	 * <h> Steen Constructor </h>
	 * <p>
	 * Gaat een nieuwe steen aanmaken met een kleur en een getal.
	 * </p>
	 * 
	 * @param kleur de kleur van de aangemaakt steen
	 * @param getal het getal van de aangemaakt steen
	 */
	public Steen(Kleur kleur, int getal) {
		setGetal(getal);
		this.kleur = kleur;

	}

	/**
	 * <h> Kleur getter </h>
	 * <p>
	 * Gaat de kleur van een steen object ophalen. </h>
	 * 
	 * @return de kleur van een steen object
	 */
	public Kleur getKleur() {
		return kleur;
	}

	/**
	 * <h> Getal setter </h>
	 * <p>
	 * Gaat het getal van de steen instellen als huidig getal van de steen.
	 * </p>
	 * 
	 * @param getal het getal van de huidige steen
	 */
	private void setGetal(int getal) {
		this.getal = getal;
	}

	/**
	 * <h> Getal getter </h>
	 * <p>
	 * Gaat het getal van de huidige steen ophalen.
	 * </p>
	 * 
	 * @return het getal van de geselecteerde steen
	 */
	public int getGetal() {
		return getal;
	}

	/**
	 * <h> Joker constructor </h>
	 * <p>
	 * Gaat een nieuwe joker steen aanmaken.
	 * </p>
	 */
	public Steen() {
		kleur = Kleur.JOKER;

	}

	/**
	 * <h> toString Methode voor stenen </h>
	 * <p>
	 * Gaat een String teruggeven van de kleur en getal van een steen.
	 * </p>
	 */
	public String toString() {
		return String.format("%s%d%n", kleur, getal);

	}

	/*
	 * <h> Gaat de hashcode returnen </h>
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getal;
		result = prime * result + ((kleur == null) ? 0 : kleur.hashCode());
		return result;
	}

	/**
	 * <h> Equals methode voor steen object </h>
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
		Steen other = (Steen) obj;
		if (getal != other.getal)
			return false;
		if (kleur != other.kleur)
			return false;
		return true;
	}

}
