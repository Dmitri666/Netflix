package com.watchandchill.gui;

import java.io.IOException;
import java.sql.*;

import com.alexanderthelen.applicationkit.database.Data;
import com.watchandchill.Application;

public class AuthenticationViewController extends com.alexanderthelen.applicationkit.gui.AuthenticationViewController {
	public static AuthenticationViewController createWithName(String name) throws IOException {
		AuthenticationViewController viewController = new AuthenticationViewController(name);
		viewController.loadView();
		return viewController;
	}

	protected AuthenticationViewController(String name) {
		super(name);
	}

	@Override
	public void loginUser(Data data) throws SQLException {
		String selectQuery = "SELECT Benutzername,EMail, Passwort FROM Nutzer WHERE Benutzername ='"  + data.get("username") + "' AND Passwort = '" + data.get("password") + "'";
		ResultSet result = Application.getInstance().getConnection().executeQuery(selectQuery);
		if(!result.next()) {
			throw new SQLException("Falsche Benutzername oder Passwort.");
		}

		Application.getInstance().getData().put("email",result.getString("EMail"));
		Application.getInstance().getData().put("username",result.getString("Benutzername"));
		Application.getInstance().getData().put("permission",2);

		result = Application.getInstance().getConnection().executeQuery("SELECT Benutzername FROM Premium_Nutzer WHERE Benutzername ='"  + data.get("username") + "'");
		if(result.next()) {
			Application.getInstance().getData().replace("permission",1);
		}

		result = Application.getInstance().getConnection().executeQuery("SELECT Benutzername FROM Schauspieler WHERE Benutzername ='"  + data.get("username") + "'");
		if(result.next()) {
			Application.getInstance().getData().replace("permission",0);
		}
	}

	@Override
	public void registerUser(Data data) throws SQLException {


		String benutzernameQuery = "SELECT Benutzername FROM Nutzer WHERE Benutzername ='" + data.get("username") + "'";
		ResultSet result = Application.getInstance().getConnection().executeQuery(benutzernameQuery);
		if (result.next()) {
			throw new SQLException("Benutzername schon vergeben.");
		}

		String eMailQuery = "SELECT EMail FROM Nutzer WHERE EMail ='" + data.get("email") + "'";
		result = Application.getInstance().getConnection().executeQuery(eMailQuery);
		if (result.next()) {
			throw new SQLException("EMail schon vergeben.");
		}

		com.alexanderthelen.applicationkit.database.Connection conn = Application.getInstance().getConnection();
		conn.getRawConnection().setAutoCommit(false);

		try {
			String sql = "INSERT INTO Nutzer(Benutzername,EMail,Passwort) VALUES(?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, data.get("username").toString());
			pstmt.setString(2, data.get("email").toString());
			pstmt.setString(3, data.get("password").toString());
			pstmt.executeUpdate();

			if ((boolean) data.get("isPremium")) {
				String premiumSql = "INSERT INTO Premium_Nutzer(Benutzername) VALUES(?)";
				PreparedStatement pstmt1 = Application.getInstance().getConnection().prepareStatement(premiumSql);

				pstmt1.setString(1, data.get("username").toString());

				pstmt1.executeUpdate();
			}

			if ((boolean) data.get("isActor")) {
				String actorSql = "INSERT INTO Schauspieler(Benutzername,Vorname,Nachname,Kunstlername,Geburtsdatum,Geburtsort) VALUES(?,?,?,?,?,?)";
				PreparedStatement pstmt2 = Application.getInstance().getConnection().prepareStatement(actorSql);

				pstmt2.setString(1, data.get("username").toString());
				pstmt2.setString(2, data.get("firstName").toString());
				pstmt2.setString(3, data.get("lastName").toString());
				if (data.containsKey("alias")) {
					pstmt2.setString(4, data.get("alias").toString());
				} else {
					pstmt2.setNull(4, Types.VARCHAR);
				}

				pstmt2.setString(5, data.get("birthdate").toString());
				pstmt2.setString(6, data.get("birthplace").toString());

				pstmt2.executeUpdate();
			}

			conn.getRawConnection().commit();
		}
		catch (Exception ex) {
			try {
				if (conn != null) {
					conn.getRawConnection().rollback();
				}
			} catch (SQLException e2) {
				throw e2;
			}
			throw ex;

		}
	}
}
