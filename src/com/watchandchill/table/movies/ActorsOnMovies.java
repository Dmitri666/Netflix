package com.watchandchill.table.movies;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActorsOnMovies extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT Benutzername AS Schauspieler ,Bezeichnung AS Film FROM Wirkt_mit_Film ";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " WHERE Film LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return "SELECT Benutzername AS Schauspieler ,Bezeichnung AS Film FROM Wirkt_mit_Film " +
                " WHERE Benutzername = '" + data.get("Wirkt_mit_Film.Schauspieler") + "' AND Bezeichnung = '"  + data.get("Wirkt_mit_Film.Film") + "'" ;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Wirkt_mit_Film(Benutzername, Bezeichnung) VALUES (?, ?)");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("Wirkt_mit_Film.Film"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        } else if (!Application.getInstance().getData().get("username").equals(oldData.get("Wirkt_mit_Film.Schauspieler"))) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Wirkt_mit_Film SET Benutzername = ?, Bezeichnung = ? WHERE Benutzername = ? AND Bezeichnung = ?");
        preparedStatement.setObject(1, newData.get("Wirkt_mit_Film.Schauspieler"));
        preparedStatement.setObject(2, newData.get("Wirkt_mit_Film.Film"));
        preparedStatement.setObject(3, oldData.get("Wirkt_mit_Film.Schauspieler"));
        preparedStatement.setObject(4, oldData.get("Wirkt_mit_Film.Film"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        } else if (!Application.getInstance().getData().get("username").equals(data.get("Wirkt_mit_Film.Schauspieler"))) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Wirkt_mit_Film WHERE Benutzername = ? AND Bezeichnung = ?");
        preparedStatement.setObject(1, data.get("Wirkt_mit_Film.Schauspieler"));
        preparedStatement.setObject(2, data.get("Wirkt_mit_Film.Film"));
        preparedStatement.executeUpdate();
    }
}
