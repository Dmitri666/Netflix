package com.watchandchill.table.movies;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RatingsOnMovies extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT Benutzername,Bewertung,Bezeichnung as Film FROM Bewertet";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE Film LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return "SELECT Benutzername,Bezeichnung as Film,Bewertung FROM Bewertet  WHERE Benutzername = '" + data.get("Bewertet.Benutzername") + "' AND Bezeichnung = '" + data.get("Bewertet.Film") + "'";
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        PreparedStatement pstmt = Application.getInstance().getConnection().prepareStatement("INSERT INTO Bewertet(Benutzername,Bezeichnung,Bewertung) VALUES(?,?,?)");
        pstmt.setString(1, Application.getInstance().getData().get("username").toString());
        pstmt.setObject(2, data.get("Bewertet.Film"));
        pstmt.setObject(3, data.get("Bewertet.Bewertung"));
        pstmt.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if (!Application.getInstance().getData().get("username").equals(oldData.get("Bewertet.Benutzername"))) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Bewertet SET Bewertung = ? WHERE Benutzername = ? AND Bezeichnung = ?");
        preparedStatement.setObject(1, newData.get("Bewertet.Bewertung"));
        preparedStatement.setObject(2, oldData.get("Bewertet.Benutzername"));
        preparedStatement.setObject(3, oldData.get("Bewertet.Film"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        if (!Application.getInstance().getData().get("username").equals(data.get("Bewertet.Benutzername"))) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().
                prepareStatement("DELETE FROM Bewertet WHERE Benutzername = ? AND Bezeichnung = ?");
        preparedStatement.setString (1 , data.get("Bewertet.Benutzername").toString());
        preparedStatement.setString (2 , data.get("Bewertet.Film").toString());
        preparedStatement.executeUpdate () ;
    }
}
