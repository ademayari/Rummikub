package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import exceptions.GeenSerieOfRijException;
import exceptions.IncorrecteSelectie;
import exceptions.JokerException;

public class Beurt {
	/**
	 * Een nieuw werkveld.
	 */
	private List<Steen> werkveld;
	/**
	 * Een nieuwe gebruiker.
	 */
	private Gebruiker gebruiker;
	/**
	 * Een nieuw gemeenschappelijk Veld.
	 */
	private List<List<Steen>> gemeenschappelijkVeld;
	/**
	 * Een nieuw kopie van het gemeenschappelijk veld.
	 */
	private List<List<Steen>> copyList;
	/**
	 * Een lijst van de status van het gemeenschappelijk veld net voor de beurt van
	 * een gebruiker gestart word.
	 */
	private List<List<Steen>> resetList;
	/**
	 * Kopie van de gebruiker zijn stenen bij aanvang van elke beurt. Deze word
	 * tijdelijk gebruikt om de beurt te spelen.
	 */
	private List<Steen> gebruikerBackup;

	/**
	 * Kopie van de gebruiker zijn stenen bij aanvang van elke beurt die onveranderd
	 * blijft tijdens elke beurt.
	 */
	private List<Steen> gebruikerBackupIntact;
	/**
	 * Een nieuwe steen in het werkveld.
	 */
	private Steen steen, steenWerkveld;
	/**
	 * Een nieuwe joker.
	 */
	private Steen steenJoker;
	/**
	 * Lijst van indexen van de acties die correct kunnen uitgevoerd worden.
	 */
	private List<Integer> posPersoonlijkBezit;

	/**
	 * <h> Constructor voor een nieuwe Beurt </h>
	 * <p>
	 * Maakt een nieuwe beurt aan voor de gebruiker aan beurt, met de huidige
	 * situatie van het gemeenschappelijk veld.
	 * </p>
	 * 
	 * @param gebruiker             de huidige gebruiker aan beurt
	 * @param gemeenschappelijkVeld het gemeenschappelijk speelveld met de hudige
	 *                              spelsituatie
	 */
	public Beurt(Gebruiker gebruiker, List<List<Steen>> gemeenschappelijkVeld) {
		this.gebruiker = gebruiker;

		gebruikerBackup = new ArrayList<>();
		gebruikerBackupIntact = new ArrayList<>();
		werkveld = new ArrayList<>();
		copyList = new ArrayList<>();
		posPersoonlijkBezit = new ArrayList<>();

		for (Steen steen : gebruiker.getStenen()) {
			gebruikerBackupIntact.add(steen);
		}

		for (Steen steen : gebruiker.getStenen()) {
			gebruikerBackup.add(steen);
		}

		for (List<Steen> lijst : gemeenschappelijkVeld) {
			copyList.add(new ArrayList<>(lijst));
		}

		resetList = new ArrayList<>();
		for (List<Steen> lijst2 : gemeenschappelijkVeld) {
			resetList.add(new ArrayList<>(lijst2));
		}

	}

	/**
	 * <h> Werkveld getter </h>
	 * <p>
	 * Haalt het werkveld met de huidige situatie op.
	 * </p>
	 * 
	 * @return het huidig werkveld
	 */
	public List<Steen> getWerkveld() {
		return werkveld;
	}

	/**
	 * <h> Methode Stop Beurt </h>
	 * <p>
	 * Stopt de beurt van de huidige gebruiker.
	 * </p>
	 */
	public void stopbeurt() {
		gemeenschappelijkVeld = copyList;
		gebruiker.updateStenen(posPersoonlijkBezit);
		posPersoonlijkBezit.clear();
		// .clear() in de plaats van nieuwe lijsten maken mss?

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
		List<Steen> splitsLijst = copyList.get(posVeld);
		List<Steen> lijst1 = new ArrayList<>(splitsLijst.subList(0, Steen1 + 1));
		List<Steen> lijst2 = new ArrayList<>(splitsLijst.subList(Steen2, splitsLijst.size()));

		copyList.add(copyList.indexOf(splitsLijst), lijst1);
		copyList.add(copyList.indexOf(splitsLijst), lijst2);
		copyList.remove(splitsLijst);

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
	public void vervangJoker(int posLijst, int posBezitOfWerkveld, int posJoker, String keuze) {

		List<Steen> geselecteerdeLijst = copyList.get(posLijst);
		int indexVoorJoker = copyList.get(posLijst).indexOf(new Steen()) + -1;

		switch (keuze) {

		case "steen van persoonlijk bezit":
			steen = gebruikerBackup.get(posBezitOfWerkveld);

			break;
		case "steen van werkveld":
			steen = werkveld.get(posBezitOfWerkveld);

			break;
		}

		List<Steen> tijdelijkeGeselecteerdeLijst = new ArrayList<>();

		for (Steen steen2 : geselecteerdeLijst) {
			tijdelijkeGeselecteerdeLijst.add(steen2);
		}

		tijdelijkeGeselecteerdeLijst.removeIf(st -> st.getGetal() == 0);
		tijdelijkeGeselecteerdeLijst.add(steen);

		if (geselecteerdeLijst.stream().map(st -> st.getGetal()).distinct().count() == 2
				&& geselecteerdeLijst.size() >= 3) {

			if (tijdelijkeGeselecteerdeLijst.stream().map(st -> st.getGetal()).distinct().count() == 1
					&& !geselecteerdeLijst.stream().anyMatch(st -> st.equals(steen))) {

				steenJoker = copyList.get(posLijst).get(posJoker);

				werkveld.add(steenJoker);

				switch (keuze) {

				case "steen van werkveld":
					werkveld.remove(posBezitOfWerkveld);
					break;
				case "steen van persoonlijk bezit":
					gebruikerBackup.remove(posBezitOfWerkveld);
					posPersoonlijkBezit.add(posBezitOfWerkveld);
					break;
				}

				Steen persoonlijkeSteen = keuze == "steen van persoonlijk bezit" ? steen : steenWerkveld;

				copyList.get(posLijst).add(persoonlijkeSteen);
				int indexLaatste = copyList.get(posLijst).size() - 1;

				Collections.swap(copyList.get(posLijst), posJoker, indexLaatste);
				copyList.get(posLijst).remove(indexLaatste);

			} else
				throw new JokerException();

		} else if (geselecteerdeLijst.size() >= 3) {

			if (steen.getGetal() != geselecteerdeLijst.get(posJoker).getGetal()
					&& steen.getGetal() == geselecteerdeLijst.get(indexVoorJoker).getGetal() + 1
					&& !steen.getKleur().equals(geselecteerdeLijst.get(posJoker).getKleur())) {

				steenJoker = copyList.get(posLijst).get(posJoker);

				werkveld.add(steenJoker);

				switch (keuze) {

				case "steen van werkveld":
					werkveld.remove(posBezitOfWerkveld);

					break;
				case "steen van persoonlijk bezit":
					gebruikerBackup.remove(posBezitOfWerkveld);
					posPersoonlijkBezit.add(posBezitOfWerkveld);

					break;
				}

				Steen persoonlijkeSteen = keuze == "steen van persoonlijk bezit" ? steen : steenWerkveld;

				copyList.get(posLijst).add(persoonlijkeSteen);
				int indexLaatste = copyList.get(posLijst).size() - 1;

				Collections.swap(copyList.get(posLijst), posJoker, indexLaatste);
				copyList.get(posLijst).remove(indexLaatste);

			} else
				throw new JokerException();
		}

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
	public void legAan(int posSteenBezit, int posInVeld, String keuze, String keuze2) {

		switch (keuze) {

		case "van persoonlijk bezit naar het spelbord":
			steen = gebruikerBackup.get(posSteenBezit);
			break;
		case "van werkveld naar het spelbord":
			steenWerkveld = werkveld.get(posSteenBezit);
			break;
		}

		switch (keuze2) {

		case "nieuwe rij of serie starten":
			List<Steen> stenen = new ArrayList<>();
			switch (keuze) {
			case "van persoonlijk bezit naar het spelbord":
				gebruikerBackup.remove(posSteenBezit);
				posPersoonlijkBezit.add(posSteenBezit);
				break;
			case "van werkveld naar het spelbord":
				werkveld.remove(posSteenBezit);
			}
			stenen.add(keuze == "van persoonlijk bezit naar het spelbord" ? steen : steenWerkveld);
			copyList.add(stenen);
			break;
		case "bestaande rij of serie aanvullen":
			int maxValueSteen = copyList.get(posInVeld).stream().max(Comparator.comparing(st -> st.getGetal())).get()
					.getGetal();

			int indexNaastJoker = copyList.get(posInVeld).indexOf(new Steen()) + 1;

			int indexLaatste = copyList.get(posInVeld).size() - 1;

			Steen persoonlijkeSteen = keuze == "van persoonlijk bezit naar het spelbord" ? steen : steenWerkveld;

			// eerst controleren is de startsteen een joker

			if (copyList.get(posInVeld).get(0).getGetal() == 0) {

				if (copyList.get(posInVeld).size() == 1) {

					copyList.get(posInVeld).add(persoonlijkeSteen);

					// extra toegevoegd
					switch (keuze) {
					case "van persoonlijk bezit naar het spelbord":
						gebruikerBackup.remove(posSteenBezit);
						posPersoonlijkBezit.add(posSteenBezit);

						// iets niet correct nog uitzoeken

						break;
					case "van werkveld naar het spelbord":
						werkveld.remove(posSteenBezit);
					}
				}

				// er ligt al een steen aan de joker wat gebeurt er nu
				else if (copyList.get(posInVeld).size() >= 2) {

					// de steen die er aan ligt is vandezelfde kleur
					if (persoonlijkeSteen.getKleur().equals(copyList.get(posInVeld).get(1).getKleur())) {

						// de steen is 1 hoger of 2 lager
						if (persoonlijkeSteen.getGetal() == maxValueSteen + 1)
							copyList.get(posInVeld).add(persoonlijkeSteen);
						else if (persoonlijkeSteen.getGetal() == copyList.get(posInVeld).get(indexNaastJoker).getGetal()
								- 2)
							copyList.get(posInVeld).add(0, persoonlijkeSteen);
						// extra toegevoegd
						else if (copyList.get(posInVeld).get(indexLaatste).getGetal() == 0
								&& persoonlijkeSteen.getGetal() == maxValueSteen + 2)
							copyList.get(posInVeld).add(persoonlijkeSteen);

						//

						else
							throw new GeenSerieOfRijException();

						switch (keuze) {
						case "van persoonlijk bezit naar het spelbord":
							gebruikerBackup.remove(posSteenBezit);
							posPersoonlijkBezit.add(posSteenBezit);

							break;
						case "van werkveld naar het spelbord":
							werkveld.remove(posSteenBezit);
						}

					}
					// de steen die er aan ligt is van een verschillende kleur
					else if (!persoonlijkeSteen.getKleur().equals(copyList.get(posInVeld).get(1).getKleur())) {

						// de steen heeft hetzelfde getal als op de eerste index
						if (persoonlijkeSteen.getGetal() == copyList.get(posInVeld).get(1).getGetal())
							copyList.get(posInVeld).add(persoonlijkeSteen);

						// extra toegevoegd

						else if (persoonlijkeSteen.getGetal() == 0)
							copyList.get(posInVeld).add(persoonlijkeSteen);

						//
						else
							throw new GeenSerieOfRijException();

						switch (keuze) {
						case "van persoonlijk bezit naar het spelbord":
							gebruikerBackup.remove(posSteenBezit);
							posPersoonlijkBezit.add(posSteenBezit);

							break;
						case "van werkveld naar het spelbord":
							werkveld.remove(posSteenBezit);
						}

					}
				}
			}

			// controle wanneer de joker zich niet op de eerste plek bevindt en zonder joker

			// is de kleur hetzelfde
			else if (persoonlijkeSteen.getKleur().equals(copyList.get(posInVeld).get(0).getKleur()))

			{

				// is de steen 1 tje hoger of lager dan de max of min
				if (persoonlijkeSteen.getGetal() == copyList.get(posInVeld).get(0).getGetal() - 1)
					copyList.get(posInVeld).add(0, persoonlijkeSteen);

				else if (persoonlijkeSteen.getGetal() == maxValueSteen + 1
						&& copyList.get(posInVeld).get(copyList.get(posInVeld).size() - 1).getGetal() != 0)
					copyList.get(posInVeld).add(persoonlijkeSteen);

				else if (copyList.get(posInVeld).get(copyList.get(posInVeld).size() - 1).getGetal() == 0
						&& persoonlijkeSteen.getGetal() == maxValueSteen + 2)
					copyList.get(posInVeld).add(persoonlijkeSteen);
				else
					throw new GeenSerieOfRijException();

				switch (keuze) {
				case "van persoonlijk bezit naar het spelbord":
					gebruikerBackup.remove(posSteenBezit);
					posPersoonlijkBezit.add(posSteenBezit);

					break;
				case "van werkveld naar het spelbord":
					werkveld.remove(posSteenBezit);
				}
			}

			// kleur is niet hetzelfde

			else if (!persoonlijkeSteen.getKleur().equals(copyList.get(posInVeld).get(0).getKleur()))

			{
				// steen is dus gelijk aan index 0
				if (persoonlijkeSteen.getGetal() == copyList.get(posInVeld).get(0).getGetal()
						|| persoonlijkeSteen.getGetal() == 0)
					copyList.get(posInVeld).add(persoonlijkeSteen);
				else
					throw new GeenSerieOfRijException();

				switch (keuze) {
				case "van persoonlijk bezit naar het spelbord":
					gebruikerBackup.remove(posSteenBezit);
					posPersoonlijkBezit.add(posSteenBezit);

					break;
				case "van werkveld naar het spelbord":
					werkveld.remove(posSteenBezit);
				}
			}

		}

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
	public void verplaatsNaarWerkVeld(int posBord, int posSerieOfRij) {

		int posRijLengte = copyList.get(posBord).size() - 1;
		Steen posLaatste = copyList.get(posBord).get(posRijLengte);

		if (((copyList.get(posBord).stream().map(st -> st.getGetal()).distinct().count() <= 1)
				&& (copyList.get(posBord).size() >= 2))

				// hierboven houd rekening dat een lijst vanaf 2 stenen met vershillende kleuren
				// ook kan verplaatst worden naar het werkveld
				// hieronder

				|| (copyList.get(posBord).stream().map(st -> st.getGetal()).distinct().count() == 2
						&& copyList.get(posBord).get(0).getGetal() == 0 && copyList.get(posBord).size() >= 3)) {

			// vragen wnr er een joker tussen twee stenen van gelijke waarde ligt moet je
			// dan ook de buitenste enkel selecteren

			if ((posSerieOfRij != 0) && (posSerieOfRij != copyList.get(posBord).indexOf(posLaatste)))
				throw new IncorrecteSelectie();

		}
		werkveld.add(copyList.get(posBord).get(posSerieOfRij));
		copyList.get(posBord).remove(posSerieOfRij);

		if (copyList.get(posBord).size() == 0)
			copyList.remove(posBord);

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

	/**
	 * <h> Tijdelijk gemeenschappelijk veld getter </h>
	 * <p>
	 * Gaat een werk kopie van het gemeenschappelijk veld in de huidige situatie
	 * gaan opvragen.
	 * </p>
	 * 
	 * @return een kopie van het gemeenschappelijk veld in de huidige situatie
	 */

	public List<List<Steen>> getCopyList() {
		return copyList;
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
		// werkveld toegevoegd omdat dit geleegd moet worden bij een beurt reset
		werkveld = new ArrayList<>();

		// probleem oplossen bij 2 keer resetten dat hij opnieuw onthoud wat er stond is
		// gefixt

		copyList = new ArrayList<>();
		gebruikerBackup = new ArrayList<>();
		for (List<Steen> lijst : resetList) {
			copyList.add(new ArrayList<>(lijst));
		}

		for (Steen st : gebruikerBackupIntact) {
			gebruikerBackup.add(st);
		}

	}

	/**
	 * <h> Kopie van stenen van de gebruiker (beging van beurt)</h>
	 * <p>
	 * Gaat een kopie van de stenen van de gebruiker bij het begin van zijn beurt
	 * (voordat er dus iets aangepast word aan zijn stenen door bv. door iets aan te
	 * leggen) ophalen.
	 * </p>
	 * 
	 * @return een kopie van stenen van de gebruiker aan het begin van zijn beurt
	 */
	public List<Steen> getGebruikerBackupIntact() {
		return gebruikerBackupIntact;
	}

	/**
	 * <h> Gebruiker getter </h>
	 * <p>
	 * Gaat de gebruiker van een beurt ophalen.
	 * </p>
	 * 
	 * @return een gebruiker van een beurt
	 */

	public Gebruiker getGebruiker() {
		return gebruiker;
	}

	/**
	 * <h> Kopie van de stenen van de gebruiker </h>
	 * <p>
	 * Gaat een kopie van de gebruiker zijn stenen bij elke nieuwe beurt ophalen.
	 * </p>
	 * 
	 * @return een kopie van de stenen van de gebruiker
	 */

	public List<Steen> getGebruikerBackup() {
		return gebruikerBackup;
	}

}