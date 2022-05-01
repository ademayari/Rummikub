package domein;

import persistentie.GebruikerMapper;

public class GebruikerRepository {
	/**
	 * Nieuwe mapper.
	 */
	private final GebruikerMapper mapper;

	/**
	 * <h> Nieuwe gebruikersrepository </h>
	 * <p>
	 * Maakt een nieuwe repository aan per gebruiker, gebruikmakend van de mapper.
	 * /p>
	 */
	public GebruikerRepository() {
		mapper = new GebruikerMapper();
	}

	/**
	 * <h> Gebruiker getter </h>
	 * <p>
	 * Gaat de gebruiker ophalen wiens info voldoet aan de meegegevn informatie van
	 * de parameters. En een nieuw gebruiker objectje maken van de gebruiker die
	 * overeen komt.
	 * </p>
	 * 
	 * @param gebruikersnaam gebruikersnaam van de gebruiker
	 * @param wachtwoord     wachtwoord van de gebruiker
	 * @param score          score van de gebruiker
	 * @return een gebruikerobjectje van de gebruiker wiens info overeenkomt met de
	 *         meegegevn info van de parameters
	 */

	public Gebruiker geefGebruiker(String gebruikersnaam, String wachtwoord, int score) {

		Gebruiker g = mapper.geefGebruiker(gebruikersnaam, wachtwoord, score);

		return g;

	}

}
