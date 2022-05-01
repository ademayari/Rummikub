package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domein.Gebruiker;

public class GebruikerMapper {

	/*
	 * public List<Gebruiker> geefGebruikers() { List<Gebruiker> spelers = new
	 * ArrayList<>();
	 * 
	 * try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
	 * PreparedStatement query =
	 * conn.prepareStatement("SELECT * FROM ID343381_g29rummikub.Speler"); ResultSet
	 * rs = query.executeQuery()) {
	 * 
	 * while (rs.next()) { String gebruikersnaam = rs.getString("gebruikersnaam");
	 * String wachtwoord = rs.getString("wachtwoord");
	 * 
	 * spelers.add(new Gebruiker(gebruikersnaam, wachtwoord)); } } catch
	 * (SQLException ex) { throw new RuntimeException(ex); }
	 * 
	 * return spelers; }
	 */

	public Gebruiker geefGebruiker(String gebruikersnaam, String wachtwoord, int score) {
		Gebruiker gebruiker = null;
       
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(
						"SELECT * FROM ID343381_g29rummikub.Speler WHERE gebruikersnaam =? AND wachtwoord =? ")) {
			query.setString(1, gebruikersnaam);
			query.setString(2, wachtwoord);
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					gebruiker = new Gebruiker(gebruikersnaam, wachtwoord, score);
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);

		}

		return gebruiker;

	}
}
	
	
