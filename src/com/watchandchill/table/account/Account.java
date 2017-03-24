package com.watchandchill.table.account;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class Account extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            return "SELECT Benutzername , EMail, Passwort  FROM Nutzer WHERE Benutzername = '" + Application.getInstance().getData().get("username") + "'";
        }
        else {
            return "SELECT N.Benutzername , N.EMail, N.Passwort , P.Vorname , P.Nachname, P.Kunstlername, P.Geburtsdatum , P.Geburtsort FROM Nutzer N, Schauspieler P WHERE N.Benutzername = P.Benutzername AND N.Benutzername = '" + Application.getInstance().getData().get("username") + "'";
        }
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            return "SELECT Benutzername , EMail, Passwort  FROM Nutzer WHERE Benutzername = '" + Application.getInstance().getData().get("username") + "'";
        }  else {
            return "SELECT N.Benutzername , N.EMail, N.Passwort , P.Vorname , P.Nachname, P.Kunstlername, P.Geburtsdatum , P.Geburtsort FROM Nutzer N, Schauspieler P WHERE N.Benutzername = P.Benutzername AND N.Benutzername = '" + Application.getInstance().getData().get("username") + "'";
        }
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {

        throw new SQLException(getClass().getName() + ".updateRowWithData(Data, Data) nicht implementiert.");

    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Nutzer SET EMail = ?,Passwort = ? WHERE Benutzername = ?");
        preparedStatement.setObject(1, newData.get("Nutzer.EMail"));
        preparedStatement.setObject(2, newData.get("Nutzer.Passwort"));
        preparedStatement.setObject(3, Application.getInstance().getData().get("username"));
        preparedStatement.executeUpdate();

        if ((Integer) Application.getInstance().getData().get("permission") == 0) {

            PreparedStatement pstm = Application.getInstance().getConnection().prepareStatement("UPDATE Schauspieler SET Vorname = ?,Nachname = ? ,Kunstlername = ? ,Geburtsdatum = ? ,Geburtsort = ? WHERE Benutzername = ?");
            pstm.setObject(1, newData.get("Schauspieler.Vorname"));
            pstm.setObject(2, newData.get("Schauspieler.Nachname"));
            pstm.setObject(3, newData.get("Schauspieler.Kunstlername"));
            pstm.setObject(4, newData.get("Schauspieler.Geburtsdatum"));
            pstm.setObject(5, newData.get("Schauspieler.Geburtsort"));
            pstm.setObject(6, Application.getInstance().getData().get("username"));
            pstm.executeUpdate();
        } else if ((Integer) Application.getInstance().getData().get("permission") == 1) {


        }
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + ".updateRowWithData(Data, Data) nicht implementiert.");
    }
}
