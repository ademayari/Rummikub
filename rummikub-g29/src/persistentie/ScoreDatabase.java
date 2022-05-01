package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domein.DomeinController;
import domein.Gebruiker;

public class ScoreDatabase {

	private DomeinController dc;

	public void updateScore(String gebruikersnaam, int score) {
		Gebruiker gebruiker = null;

		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn
						.prepareStatement("select * from ID343381_g29rummikub.Speler where gebruikersnaam =?")) {
			query.setString(1, gebruikersnaam);
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					try (Connection conn2 = DriverManager.getConnection(Connectie.JDBC_URL);
							PreparedStatement query2 = conn2.prepareStatement(
									"update ID343381_g29rummikub.Speler set score = CONCAT(score, ' ', ?),totaalscore=totaalscore+? where gebruikersnaam =?")) {
						query2.setString(1, " " + String.valueOf(score));
						query2.setInt(2, score);
						query2.setString(3, gebruikersnaam);
						query2.executeUpdate();

					} catch (SQLException ex) {
						throw new RuntimeException(ex);
					}
				} else {
					try (Connection conn2 = DriverManager.getConnection(Connectie.JDBC_URL);
							PreparedStatement query2 = conn2.prepareStatement(
									"insert into ID343381_g29rummikub.Speler set score = score +?,totaalscore=+? where gebruikersnaam =?")) {
						query2.setString(1, String.valueOf(score));
						query2.setInt(2, score);
						query2.setString(3, gebruikersnaam);
						query2.executeUpdate();

					} catch (SQLException ex) {
						throw new RuntimeException(ex);
					}
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public List<String> getScores() {
		List<String> ranglijst = new ArrayList<>();
		try (Connection conn3 = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query3 = conn3.prepareStatement(
						"SELECT gebruikersnaam,score,totaalscore FROM ID343381_g29rummikub.Speler ORDER BY  totaalscore DESC, gebruikersnaam ASC")) {

			try (ResultSet rs = query3.executeQuery()) {
				while (rs.next()) {
					String totaal = rs.getString("totaalscore");
					String score = rs.getString("score");
					ranglijst.add(rs.getString("gebruikersnaam"));
					ranglijst.add(totaal);
					ranglijst.add(score);
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return ranglijst;
	}
//	public List<String> getScores() {
//
//		List<String> ranglijst = new ArrayList<>();
//		try (Connection conn3 = DriverManager.getConnection(Connectie.JDBC_URL);
//				PreparedStatement query3 = conn3.prepareStatement(
//						"SELECT gebruikersnaam, score, totaalscore FROM ID343381_g29rummikub.Speler ORDER BY  totaalscore DESC, gebruikersnaam ASC where gebruikersnaam=?")) {
//
//			try (ResultSet rs = query3.executeQuery()) {
//				for (Gebruiker gebr : dc.getGebruikers()) {
//
//					while (rs.next())
//						;
////					if (gebr.getGebruikersnaam().equals(rs.getString("gebruikersnaam"))) {
//					query3.setString(1, gebr.getGebruikersnaam());
//					String totaal = rs.getString("totaalscore");
//					String scores = rs.getString("score");
//					ranglijst.add(totaal);
//					ranglijst.add(scores);
////					}
//
//				}
//
//			} catch (SQLException ex) {
//				System.out.println(ex.getMessage());
//			}
//		} catch (SQLException ex) {
//
//			System.out.println(ex.getMessage());
//		}
//		return ranglijst;
//
//	}

	public void resetScores() {
		try (Connection conn4 = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query4 = conn4
						.prepareStatement("update ID343381_g29rummikub.Speler set score = ?, totaalscore=?")) {

			int int1 = 0;
			int int2 = 0;
			query4.setInt(1, int1);
			query4.setInt(2, int2);
			int rs = query4.executeUpdate();

		} catch (SQLException ex) {

			System.out.println(ex.getMessage());
		}
	}
}
