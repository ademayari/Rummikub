package domein;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import domein.Steen.Kleur;
import exceptions.BevatJokerException;
import exceptions.GeenSerieOfRijException;
import exceptions.IncorrecteBeurtException;
import exceptions.IncorrecteSpelSituatieException;
import exceptions.SerieOfRijException;
import persistentie.ScoreDatabase;

public class RummikubSpel {
	/**
	 * Totaal aantal stenen in de pot.
	 */
	private final int BEGINAANTALSTENEN = 106;

	/**
	 * Een nieuwe klasse met methodes om de scores te registreren.
	 */

	private ScoreDatabase scoreDatabase = new ScoreDatabase();

	/**
	 * Nieuwe lijst van stenen.
	 */

	private List<Steen> stenen;
	/**
	 * Nieuwe lijst van de huidige gebruikers.
	 */
	private List<Gebruiker> gebruikers;
	/**
	 * De huidige gebruiker aan beurt.
	 */
	private int gebruikerAanBeurt;
	/**
	 * Een nieuwe instantie van beurt.
	 */
	private Beurt beurt;
	/**
	 * Index van de hoeveelste beurt het is.
	 */
	private int beurtIndex;
	/**
	 * Nieuw gemeenschappelijk veld.
	 */

	private List<List<Steen>> gemeenschappelijkVeld;

	/**
	 * <h> Constructor RummikubSpel </h>
	 * <p>
	 * Gaat een nieuwe instantie van RummikubSpel opbouwen met de aangelogde
	 * gebruikers.
	 * </p>
	 * 
	 * @param gebruikers aangemelde gebruikers die een spel gaan spelen
	 */
	public RummikubSpel(List<Gebruiker> gebruikers) {

		stenen = new ArrayList<>(BEGINAANTALSTENEN);
		SecureRandom sr = new SecureRandom();
		this.gebruikers = gebruikers;
		for (Kleur kleur : Kleur.values()) {
			if (kleur != Kleur.JOKER) {
				for (int i = 1; i <= 13; i++) {
					stenen.add(new Steen(kleur, i));
					stenen.add(new Steen(kleur, i));
				}
			}

		}
		stenen.add(new Steen());
		stenen.add(new Steen());

		for (Gebruiker gebr : gebruikers) {
			List<Steen> stenenLijst = new ArrayList<>(); // word per gebruiker een stenenlijst aangemaakt

			for (int i = 0; i < 14; i++) {
				stenenLijst.add(stenen.remove(sr.nextInt(stenen.size()))); // stenenlijst word aangevuld met stenen

			}

			gebr.maakStenenBord(stenenLijst);

		}

		Collections.shuffle(gebruikers);

		gebruikerAanBeurt = 0;
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

		boolean eindeSpel = false;

		for (Gebruiker gebruiker : gebruikers) {

			if (gebruiker.getStenen().isEmpty())
				eindeSpel = true;

		}
		return eindeSpel;
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
		Gebruiker gebruiker = gebruikers.get(gebruikerAanBeurt++);
		if (gebruikerAanBeurt == gebruikers.size())
			gebruikerAanBeurt = 0;
		String printGebruiker = String.format("%s is aan de beurt%n", gebruiker.getGebruikersnaam());

		return printGebruiker;
	}

	/**
	 * <h> Methode Bereken Scores </h>
	 * <p>
	 * Gaat de scores per gebruiker die meedoet aan het huidig spel berekenen.
	 * </p>
	 * 
	 * @return scores van elke gebruiker van het huidig spel
	 */
	public String berekenScores() {

		int scoreWinnaar = 0;
		String scorebord = "";
		int[] scores = new int[3];
		int scoreVerliezers = 0;
		int teller = 0;

		for (Gebruiker gebruiker : gebruikers) {

			if (gebruiker.getStenen().isEmpty()) {

				scoreWinnaar = 0;
				scorebord += String.format("%s met score: %s%n", gebruiker, scoreWinnaar);
				scoreDatabase.updateScore(gebruiker.getGebruikersnaam(), scoreWinnaar);
			} else {

				scores[teller] = 0;
				for (Steen steen : gebruiker.getStenen()) {

					if (steen.getKleur() == Kleur.JOKER)
						scoreVerliezers += 25;
					scoreVerliezers += (steen.getGetal());
					scores[teller] = scoreVerliezers;

				}
				if (scores[teller] != 0) {

					scorebord += String.format("%s met score: %s%n", gebruiker, scoreVerliezers);
					scoreDatabase.updateScore(gebruiker.getGebruikersnaam(), scoreVerliezers);
					scoreVerliezers = 0;
					teller++;
				}
			}
		}
		return scorebord;

	}

	/**
	 * <h> Gebruiker aan beurt getter </h>
	 * <p>
	 * Gaat de huidige gebruiker aan beurt ophalen.
	 * </p>
	 * 
	 * @return de huidige gebruiker aan beurt
	 */
	public int getGebruikerAanBeurt() {
		return gebruikerAanBeurt;
	}

	/**
	 * <h> Gebruikers getter </h>
	 * <p>
	 * Gaat de gebruikers die aangemeld ophalen.
	 * 
	 * @return de aangemelde gebruikers
	 */
	public List<Gebruiker> getGebruikers() {
		return gebruikers;
	}

	/**
	 * <h> Methode Start Beurt </h>
	 * <p>
	 * Start de beurt voor de geselecteerde gebruiker of dus de gebruiker aan beurt.
	 * </p>
	 */
	public void startBeurt() {

		beurtIndex++;

		if (gemeenschappelijkVeld == null) {
			this.gemeenschappelijkVeld = new ArrayList<>();
		} else {
			this.gemeenschappelijkVeld = new ArrayList<>(this.gemeenschappelijkVeld);
		}

		beurt = new Beurt(gebruikers.get(gebruikerAanBeurt), gemeenschappelijkVeld);

	}

	/**
	 * <h> Methode toon Spel Situatie </h>
	 * <p>
	 * Toont de huidige spelsituatie, het gemeenschappelijk veld dus, weer.
	 * </p>
	 */
	public void toonSpelSituatie() {
		beurt.getWerkveld();
		beurt.getGemeenschappelijkVeld();
		gebruikers.get(gebruikerAanBeurt).getStenen();

	}

	/**
	 * <h> Methode Stop Beurt </h>
	 * <p>
	 * Stopt de beurt voor de gebruiker die aan beurt is. Ook word de spelsituatie
	 * geregistreerd en gevalideerd.
	 * </p>
	 */

	public void stopBeurt() {

		if (isEindeSpel() == false) {
			int copyVeld = beurt.getCopyList().stream()
					.mapToInt(list -> list.stream().mapToInt(st -> st.getGetal()).sum()).sum();
			int aantalGebruikersAangemeld = gebruikers.size();
			if (beurtIndex <= aantalGebruikersAangemeld && copyVeld > 0 && copyVeld < 30)
				throw new IncorrecteSpelSituatieException();
			else if (beurtIndex <= aantalGebruikersAangemeld
					&& beurt.getCopyList().stream().anyMatch(list -> list.contains(new Steen())))
				throw new BevatJokerException();
			else if (beurt.getCopyList().stream().anyMatch(l -> l.size() < 3))
				throw new SerieOfRijException();
			else if (!beurt.getWerkveld().isEmpty())
				throw new IncorrecteBeurtException();
			for (List<Steen> list : beurt.getCopyList()) {

				if (list.stream().mapToInt(st -> st.getGetal()).distinct().count() > 2) {

					// bevat een joker

					List<Integer> copyLijstje = list.stream().map(st -> st.getGetal()).collect(Collectors.toList());

					int aantalVoorkomen = Collections.frequency(copyLijstje, 0);

					for (int i = 0; i < aantalVoorkomen; i++) {

						if (copyLijstje.get(0) == 0) {

							int pos = list.get(1).getGetal() - 1;
							copyLijstje.remove(0);
							copyLijstje.add(0, pos);

						}

						else if (copyLijstje.get(copyLijstje.size() - 1) == 0) {

							int pos = list.get(list.size() - 2).getGetal() + 1;
							copyLijstje.remove(list.size() - 1);
							copyLijstje.add(pos);

						}

						else {

							int indexNaastJoker = copyLijstje.indexOf(0) + 1;
							int pos = list.get(indexNaastJoker).getGetal() - 1;
							copyLijstje.remove(indexNaastJoker - 1);
							copyLijstje.add(indexNaastJoker - 1, pos);

						}

					}
					// eventueel bekijken voor meerdere jokers in de lijst maar is zeer zeldzaam

					// bevat geen joker

					copyLijstje.stream().reduce((a, b) -> {
						if (a == b - 1) {
						} else
							throw new GeenSerieOfRijException();

						return b;

					});

				}

			}
			if (!beurt.getWerkveld().isEmpty())
				throw new IncorrecteBeurtException();
			int gemVeld = gemeenschappelijkVeld.stream().mapToInt(List::size).sum();
			int copVeld = beurt.getCopyList().stream().mapToInt(List::size).sum();
			if (gemVeld != copVeld) {

				beurt.stopbeurt();
				this.gemeenschappelijkVeld = beurt.getGemeenschappelijkVeld();

			}

			else {
				gebruikers.get(gebruikerAanBeurt).getStenen().add(haalSteenUitPot());
			}
		}

	}

	/**
	 * <h> Methode haal steen uit pot </h>
	 * <p>
	 * Gaat een random steen uit de pot halen, gebruikt om aan een gebruiker te
	 * geven als hij gedurende zijn beurt geen actie uitvoerd en zijn beurt stopt.
	 * </p>
	 * 
	 * @return een random steen uit de pot
	 */
	public Steen haalSteenUitPot() {

		SecureRandom rn = new SecureRandom();
		Steen steen = stenen.get(rn.nextInt(stenen.size()));
		stenen.remove(steen);
		return steen;

	}

	/**
	 * <h> Methode Verplaats naar Werkveld </h>
	 * <p>
	 * Gaat een gekozen steen uit een serie of rij op het gemeenschappelijkveld
	 * verplaatsen naar het werkveld.
	 * </p>
	 * 
	 * @param posBord       keuze van de serie of rij op het gemeenschappelijk veld
	 *                      waaruit je een steen wilt verplaatsen naar het werkveld
	 * @param posSerieOfRij keuze van de steen in de geselecteerde serie of rij, die
	 *                      men wilt verplaatsen naar het werkveld
	 */
	public void verplaatsNaarWerkveld(int posBord, int posSerieOfRij) {

		beurt.verplaatsNaarWerkVeld(posBord, posSerieOfRij);

	}

	/**
	 * <h> Methode Leg Aan </h>
	 * <p>
	 * Gaat een steen vanuit het werkveld of persoonlijk bezit plaatsen in een
	 * nieuwe of bestaande rij of serie op het gemeenschappelijk veld.
	 * </p>
	 * 
	 * @param posSteenBezit keuze steen die men wil aanleggen vanuit eigen bezit of
	 *                      werkveld
	 * @param posInVeld     keuze van de serie of rij op het gemeenschappelijk veld
	 *                      waar men een steen wilt leggen
	 * @param keuze         keuze of men een steen vanuit het werkveld of
	 *                      persoonlijk bezit wilt aanleggen
	 * @param keuze2        keuze of men een nieuwe serie of rij wilt starten of een
	 *                      bestaande serie of rij aanvullen
	 */
	public void legAan(int posSteenInBezit, int posVeld, String keuze, String keuze2) {
		beurt.legAan(posSteenInBezit, posVeld, keuze, keuze2);

	}

	/**
	 * <h> Methode Vervang Joker </h>
	 * <p>
	 * Gaat een joker in een serie of rij op het gemeenshappelijk veld vervangen met
	 * een steen uit het werkveld of persoonlijk bezit die voldoet aan de voorwarde
	 * om te kunnen vervangen in de gekozen rij of serie op het gemeenschappelijk
	 * veld. Vervolgens word de joker verplaatst naar het werkveld.
	 * </p>
	 * 
	 * @param posLijst           keuze van de rij of serie op het gemeenschappelijk
	 *                           veld
	 * @param posBezitOfWerkveld keuze steen uit het eigen bezit of werkveld
	 * @param posJoker           positie van de joker in de gekozen serie of rij
	 * @param keuze              keuze van waar je een steen wilt gebruiken om te
	 *                           vervangen, het werkeveld of persoonlijk bezit
	 */
	public void vervangJoker(int posInGemVeld, int posBezitOfWerkveld, int posJokerInLijst, String keuze) {

		beurt.vervangJoker(posInGemVeld, posBezitOfWerkveld, posJokerInLijst, keuze);

	}

	/**
	 * <h> Methode Reset Beurt </h>
	 * 
	 * 
	 * <p>
	 * Gaat de beurt van de huidige gebruiker resetten naar de situatie voordat de
	 * gebruiker aan beurt zijn huidige beurt heeft gestart.
	 * </p>
	 * 
	 */
	public void resetBeurt() {
		beurt.resetBeurt();
	}

	/**
	 * <h> Methode Splits Serie of Rij </h>
	 * 
	 * <p>
	 * Gaat de stenen in een specifieke gekozen serie of rij op het
	 * gemeenschappelijk veld splitsen tussen de posities die aangegeven word door
	 * de gebruiker.
	 * </p>
	 * 
	 * @param posVeld plaats van de serie of rij op het gemeenschappelijk veld
	 *                waarin men wil splitsen
	 * @param Steen1  positie van de eerste steen waartussen men wilt splitsen
	 * @param Steen2  positie van de tweede steen waartussen men wilt splitsen
	 */
	public void splitsSerieOfRij(int posVeld, int Steen1, int Steen2) {
		beurt.splitsSerieOfRij(posVeld, Steen1, Steen2);
	}

	/**
	 * <h> Beurt getter </h>
	 * <p>
	 * Gaat de huidige beurt ophalen.
	 * </p>
	 * 
	 * @return de huidige beurt
	 */
	public Beurt getBeurt() {
		return beurt;
	}

	/**
	 * <h> Gemeenschappelijkveld getter </h>
	 * 
	 * <p>
	 * Gaat het gemeenschappelijk veld in de huidige situatie opvragen.
	 * </p>
	 * 
	 * @return het gemeenschappelijk veld in de huidige situatie
	 */
	public List<List<Steen>> getGemeenschappelijkVeld() {
		return gemeenschappelijkVeld;
	}

}
