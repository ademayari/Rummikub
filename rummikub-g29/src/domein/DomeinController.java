package domein;

import java.util.ArrayList;
import java.util.List;

import exceptions.AantalException;
import exceptions.IncorrecteGebruikerException;
import persistentie.ScoreDatabase;

public class DomeinController {

	/**
	 * Een nieuwe klasse met methodes om de scores te registreren.
	 */

	private ScoreDatabase scoreDatabase;

	/**
	 * Een nieuwe gebruikersrepository.
	 */
	private GebruikerRepository gebruikersrepo;
	/**
	 * Lijst van de huide gebruikers die aangemeld zijn.
	 */
	private List<Gebruiker> gebruikers;
	/**
	 * Nieuwe instantie van RummikubSpel.
	 */
	private RummikubSpel rummikubspel;

	/**
	 * Aantal gebruikers die gaan meedoen met het huidig spel.
	 */
	private int aantalGebruikers;
	/**
	 * De keuze van de taal voor het huidig spel.
	 */
	private Taal taal;

	/**
	 * USE CASE 1
	 */

	/**
	 * <h>De DomeinController</h>
	 * <p>
	 * Maakt een nieuwe gebruikersrepository aan, als ook een nieuwe ArrayList van
	 * de gebruikers.
	 * </p>
	 */

	public DomeinController() {
		gebruikersrepo = new GebruikerRepository();
		gebruikers = new ArrayList<>();
		scoreDatabase = new ScoreDatabase();
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

	public Taal getTaal() {
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
		if (!taal.equals("Nederlands") && !taal.equals("English"))
			throw new IllegalArgumentException("Geef een geldige taal op\n");
		this.taal = new Taal(taal);
	}

	/**
	 * <h> Aantal gebruikers registreren </h>
	 * 
	 * <p>
	 * Gaat controleren of de aantal opgegeven gebruikers die een spel willen spelen
	 * voldoen aan de correcte opgegeven regels. Er kunnen minstens 2 en maximaal 4
	 * gebruikers een spel spelen.
	 * </p>
	 * 
	 * @param aantalGebruikers aantal gebruikers die zullen meedoen aan het spel
	 */

	public void registreerAantalGebruikers(int aantalGebruikers) {
		setAantalGebruikers(aantalGebruikers);

	}

	/**
	 * <h>Getter voor de aantal Gebruikers</h>
	 * 
	 * <p>
	 * Gaat de aantal gebruikers, die het spel gaan spelen, ophalen.
	 * </p>
	 * 
	 * @return de correcte hoeveelheid gebruikers die het spel gaan spelen
	 */

	public int getAantalGebruikers() {
		return aantalGebruikers;
	}

	/**
	 * <h> Setter voor aantal Gebruikers </h>
	 * <p>
	 * Gaat de huidige aantal gebruikers, die het spel gaan spelen, instellen.
	 * </p>
	 * 
	 * @param aantalGebruikers aantal gebruikers die zullen meedoen aan het spel
	 */

	private void setAantalGebruikers(int aantalGebruikers) {
		if (aantalGebruikers < 2 || aantalGebruikers > 4)
			throw new AantalException();
		this.aantalGebruikers = aantalGebruikers;

	}

	/**
	 * <h>Getter voor de Gebruikers </h>
	 * 
	 * <p>
	 * Gaat een lijst van de gebruikers die zijn aangemeld ophalen.
	 * </p>
	 * 
	 * @return lijst van de aantal aangemelde gebruikers
	 * 
	 */

	public List<Gebruiker> getGebruikers() {
		return gebruikers;
	}

	/**
	 * <h> Methode Meld Aan </h>
	 * 
	 * <p>
	 * Gaat de gebruiker zijn gebruikersnaam en wachtwoord gebruiken om hem aan te
	 * melden bij het huidige opgestelde spel. Ook word een exception gegooid als de
	 * gebruikersnaaam of wachtwoord niet overeenkomt met die in de databank,
	 * waarbij de gebruiker dan word gevraagd om zijn aanmeldgegevens opnieuw in te
	 * geven.
	 * </p>
	 * 
	 * @param gebruikersnaam de gebruiker zijn persoonlijke gebruikersnaam
	 * @param wachtwoord     de gebruiker zijn persoonlijk wachtwoord
	 */

	public void meldAan(String gebruikersnaam, String wachtwoord, int score) {
		Gebruiker gevondenSpeler = gebruikersrepo.geefGebruiker(gebruikersnaam, wachtwoord, score);
		if (gevondenSpeler != null) {
			gebruikers.add(gevondenSpeler);

		} else
			throw new IncorrecteGebruikerException();
	}

	/**
	 * <h>Geeft de aangemelde Gebruikersnamen </h>
	 * <p>
	 * De methode geeft de gebruikersnaam van alle aangemelde spelers terug in de
	 * vorm van een String.
	 * </p>
	 * 
	 * @return aangemelde gebruikers als String
	 */

	public String geefGebruikersNamen() {

		String aangemeldeGebruikers = "";

		for (Gebruiker g : gebruikers) {
			aangemeldeGebruikers += String.format("%s %n", g);
		}
		return aangemeldeGebruikers;

	}

	/**
	 * USE CASE 2
	 */

	/**
	 * </h> Methode Start Spel</h>
	 * <p>
	 * Start het spel en maakt nieuwe instantie van RummikubSpel met de aangemelde
	 * gebruikers. Dus de gebruiker aan beurt word willekeurig bepaald, de stenen
	 * van de gebruikers worden willekeurig uitgedeeld en de pot word aangemaakt.
	 * </p>
	 * 
	 */

	public void startSpel() {

		rummikubspel = new RummikubSpel(gebruikers);

	}

	/**
	 * <h> Geeft de gebruiker aan beurt weer </h>
	 * <p>
	 * Geeft de naam van de gebruiker aan beurt weer in de vorm van een String.
	 * </p>
	 * 
	 * @return naam van de gebruiker aan beurt als String
	 */

	public String geefNaamGebruikerAanBeurt() {

		return rummikubspel.geefNaamGebruikerAanBeurt();
	}

	/**
	 * <h> Methode is Einde Spel </h>
	 * <p>
	 * Bepaald of het einde van het spel is bereikt. En dat is wanneer een gebruiker
	 * geen stenen meer heeft in persoonlijk bezit. Wanneer het einde van het spel
	 * is bereikt veranderd de boolean gelinkt aan deze methode van False naar True.
	 * </p>
	 * 
	 * @return of al dan niet het einde van de spel bereikt is, zo ja dan geeft het
	 *         True terug, zo niet dan geeft het False terug
	 */

	public boolean isEindeSpel() {

		return rummikubspel.isEindeSpel();
	}

	/**
	 * <h> Methode Geef Scores </h>
	 * <p>
	 * Geeft de scores van de gebruikers, die momenteel aangemeld zijn en een spel
	 * spelen, weer.
	 * </p>
	 * 
	 * @return de spelscores van de aangemelde gebruikers in het huidig spel
	 */

	public String geefScores() {

		return rummikubspel.berekenScores();
	}

	/**
	 * USE CASE 3
	 */

	/**
	 * <h> Methode Start Beurt </h>
	 * <p>
	 * Start de beurt voor de geselecteerde gebruiker of dus de gebruiker aan beurt.
	 * </p>
	 */

	public void startBeurt() {
		rummikubspel.startBeurt();

	}

	/**
	 * <h> Methode toon Spel Situatie </h>
	 * <p>
	 * Toont de huidige spelsituatie, het gemeenschappelijk veld dus, weer.
	 * </p>
	 */

	public void toonSpelSituatie() {
		rummikubspel.toonSpelSituatie();

	}

	/**
	 * <h> Methode Stop Beurt </h>
	 * <p>
	 * Stopt de beurt voor de gebruiker die aan beurt is. Ook word de spelsituatie
	 * geregistreerd en gevalideerd.
	 * </p>
	 */

	public void stopBeurt() {
		rummikubspel.stopBeurt();
	}

	/**
	 * <h> Mehtode verplaats naar Werkveld </h>
	 * <p>
	 * Verplaatst een steen naar keuze vanuit het gemeenschappelijk veld naar het
	 * werkveld.
	 * </p>
	 * 
	 * @param posBord       keuze van de lijst op het werkveld waarin de steen zit
	 *                      die men wilt verplaatsen naar het werkveld
	 * @param posRijOfSerie keuze van de steen, die men wil verplaatsen naar het
	 *                      werkveld, in de geselecteerde lijst
	 */

	public void verplaatsNaarWerkveld(int posBord, int posRijOfSerie) {
		rummikubspel.verplaatsNaarWerkveld(posBord, posRijOfSerie);

	}

	/**
	 * <h> Methode Leg Aan </h>
	 * <p>
	 * Legt een steen naar keuze, vanuit het persoonlijk bezit of het werkveld, aan
	 * op het gemeenschappelijk veld.
	 * </p>
	 * 
	 * @param posSteenInBezit keuze steen die men wilt aanleggen, vanuit persoonlijk
	 *                        bezit of het werkveld
	 * @param posOpVeld       keuze van de stenenlijst op het gemeenschappelijk veld
	 *                        waar men de steen wilt aanleggen
	 * @param keuze           persoonlijk bezit of werkveld, keuze van waar men een
	 *                        steen wilt gebruiken om aan te leggen
	 * @param keuze2          nieuwe rij of serie of bestaande rij of serie, keuze
	 *                        of men de steen wilt toevoegen aan een bestaande lijst
	 *                        op het gemeenschapplijk veld of een nieuwe stenenlijst
	 *                        wilt starten
	 */

	public void legAan(int posSteenInBezit, int posOpVeld, String keuze, String keuze2) {
		rummikubspel.legAan(posSteenInBezit, posOpVeld, keuze, keuze2);

	}

	/**
	 * <h> Methode Vervang Joker </h>
	 * <p>
	 * Vervangt een joker op het veld met een steen uit eigen bezit of het werkveld.
	 * Verplaatst dan de verplaaste joker naar het werkveld.
	 * </p>
	 * 
	 * @param posInGemVeld       keuze van de stenenlijst op het gemeenschappelijk
	 *                           veld, of dus de lijst waarin de joker zit
	 * @param posBezitOfWerkveld keuze van de steen in persoonlijk bezit of het
	 *                           werkveld die men gaat gebruiken voor de joker mee
	 *                           te vervangen
	 * @param posJokerInLijst    keuze van de positie waar de joker zich bevind in
	 *                           de stenenlijst op het gemeenschappelijk veld
	 * @param keuze              werkveld of persoonlijk bezit, de keuze van waar je
	 *                           een steen wilt gebruiken om de joker mee te
	 *                           vervangen
	 */

	public void vervangJoker(int posInGemVeld, int posBezitOfWerkveld, int posJokerInLijst, String keuze) {
		rummikubspel.vervangJoker(posInGemVeld, posBezitOfWerkveld, posJokerInLijst, keuze);

	}

	/**
	 * <h> Methode splits Serie of Rij</h>
	 * <p>
	 * Splitst de geselecteerde stenenlijst op het gemeenschappelijk veld in een
	 * Serie of Rij als het van toepassing is.
	 * </p>
	 * 
	 * @param posVeld is de keuze van de stenenlijst op het gemeenschappelijk veld
	 * @param Steen1  keuze van de eerste steen waartussen je wilt splitsen
	 * @param Steen2  keuze van de tweede steen waartussen je wilt splitsen
	 */

	public void splitsSerieOfRij(int posVeld, int Steen1, int Steen2) {
		rummikubspel.splitsSerieOfRij(posVeld, Steen1, Steen2);
	}

	/**
	 * <h> Methode Reset Beurt </h>
	 * <p>
	 * Reset de beurt voor de huidige speler aan beurt. Dit betekent dat alle
	 * uitvoeringen, in de huidige speler zijn beurt, die tot net voor deze methode
	 * word opgeroepen zijn uitgevoerd, worden gewist en de spelsituatie aan de
	 * start van de beurt opnieuw word gebruikt.
	 * </p>
	 */

	public void resetBeurt() {
		rummikubspel.resetBeurt();
	}

	/**
	 * <h> Getter voor het RummikubSpel </h>
	 * <p>
	 * Deze methode geeft de huidige instantie van het RummikubSpel terug om deze te
	 * kunnen gebruiken in de GUI klassen.
	 * </p>
	 * 
	 * @return huidige instantie van het RummikubSpel
	 */

	public RummikubSpel getRummikubspel() {
		return rummikubspel;
	}

	/**
	 * USE CASE 4
	 */

	/**
	 * <h> Scores getter </h>
	 * <p>
	 * Gaat de scores van het huidig spel per gebruiker gaan ophalen, als ook de
	 * totale scores per speler (=leaderboard).
	 * </p>
	 * 
	 * @return zowel de scores van het huidige spel per aangemelde gebruiker als de
	 *         totale scores van alle spellen in totaal
	 */

	public List<String> getScores() {
		return scoreDatabase.getScores();
	}

	/**
	 * <h> Methode reset score </h>
	 * <p>
	 * Reset de scores van alle gebruikers naar 0.
	 * </p>
	 */

	public void scoreReset() {
		scoreDatabase.resetScores();
	}

}
