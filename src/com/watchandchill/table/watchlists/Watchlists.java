package com.watchandchill.table.watchlists;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Watchlists extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT ID, Bezeichnung AS Titel, Privatspharestatus AS \"Privat?\", Benutzername AS Nutzer FROM Watchlist";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " WHERE Benutzername LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        String selectQuery = "SELECT ID AS \"ID von Watchlist\", Bezeichnung, Privatspharestatus AS Privat, Benutzername AS Premiumnutzer FROM Watchlist WHERE ID = " + data.get("Watchlist.ID");
        return selectQuery;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 1) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Watchlist(Bezeichnung, Privatspharestatus, Benutzername) VALUES (?, ?, ?)");
        preparedStatement.setObject(1, data.get("Watchlist.Bezeichnung"));
        preparedStatement.setObject(2, data.get("Watchlist.Privat"));
        preparedStatement.setObject(3, Application.getInstance().getData().get("username"));
        preparedStatement.executeUpdate();
        /*preparedStatement.getGeneratedKeys().next();
        int index = preparedStatement.getGeneratedKeys().getInt(1);*/
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 1) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        } else if (!Application.getInstance().getData().get("username").equals(oldData.get("Watchlist.Premiumnutzer"))) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Watchlist SET Bezeichnung = ?, Privatspharestatus = ? WHERE ID = ?");
        preparedStatement.setObject(1, newData.get("Watchlist.Bezeichnung"));
        preparedStatement.setObject(2, newData.get("Watchlist.Privat"));
        preparedStatement.setObject(3, oldData.get("Watchlist.ID von Watchlist"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 1) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        } else if (!Application.getInstance().getData().get("username").equals(data.get("Watchlist.Premiumnutzer"))) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Watchlist WHERE ID = ?");
        preparedStatement.setObject(1, data.get("Watchlist.ID"));
        preparedStatement.executeUpdate();
    }
}
