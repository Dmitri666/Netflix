package com.watchandchill.table.users;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActorsOnUsers extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT Vorname,Nachname,Kunstlername,Geburtsdatum,Geburtsort,Benutzername FROM Schauspieler";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE Benutzername LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return "SELECT Benutzername,Vorname,Nachname,Kunstlername,Geburtsdatum,Geburtsort FROM Schauspieler  WHERE Benutzername = '" + data.get("Schauspieler.Benutzername") + "'";
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + ".insertRowWithData(Data) nicht implementiert.");
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if (!Application.getInstance().getData().get("username").equals(oldData.get("Schauspieler.Benutzername"))) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Schauspieler SET Vorname = ? ,Nachname = ? ,Kunstlername = ? ,Geburtsdatum = ? ,Geburtsort = ? WHERE Benutzername = ?");
        preparedStatement.setObject(1, newData.get("Schauspieler.Vorname"));
        preparedStatement.setObject(2, newData.get("Schauspieler.Nachname"));
        preparedStatement.setObject(3, newData.get("Schauspieler.Kunstlername"));
        preparedStatement.setObject(4, newData.get("Schauspieler.Geburtsdatum"));
        preparedStatement.setObject(5, newData.get("Schauspieler.Geburtsort"));

        preparedStatement.setObject(6, oldData.get("Schauspieler.Benutzername"));

        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + ".deleteRowWithData(Data) nicht implementiert.");
    }
}
