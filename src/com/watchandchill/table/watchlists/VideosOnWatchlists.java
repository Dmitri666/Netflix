package com.watchandchill.table.watchlists;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VideosOnWatchlists extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT E.WatchlistId AS \"ID von Watchlist\" ,W.Benutzername AS Ersteller, E.Bezeichnung AS Video ,W.Bezeichnung AS \"Watchlist Title\" FROM Enthalt E " +
                ", Watchlist W WHERE W.ID = E.WatchlistId AND (W.Privatspharestatus = 'öffentlich' OR (W.Privatspharestatus = 'privat' AND Benutzername = '" + Application.getInstance().getData().get("username").toString() +"' )) ";
        if (filter != null && !filter.isEmpty()) {
            selectQuery += " AND W.Bezeichnung LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return "SELECT WatchlistId AS \"ID von Watchlist\", Bezeichnung AS Video FROM Enthalt  WHERE  WatchlistId = " + data.get("Enthalt.ID von Watchlist") +
                " AND Bezeichnung = '"+ data.get("Enthalt.Video") + "'";

    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        PreparedStatement selectStatement = Application.getInstance().getConnection().prepareStatement("SELECT Benutzername FROM Watchlist WHERE ID = ? AND Benutzername = ?");
        selectStatement.setObject(1, data.get("Enthalt.ID von Watchlist"));
        selectStatement.setObject(2, Application.getInstance().getData().get("username"));
        ResultSet result = selectStatement.executeQuery();
        if (!result.next()) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Enthalt(Bezeichnung, WatchlistId) VALUES (?, ?)");
        preparedStatement.setObject(1, data.get("Enthalt.Video"));
        preparedStatement.setObject(2, data.get("Enthalt.ID von Watchlist"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        PreparedStatement selectStatement = Application.getInstance().getConnection().prepareStatement("SELECT Benutzername FROM Watchlist WHERE ID = ? AND Benutzername = ?");
        selectStatement.setObject(1, oldData.get("Enthalt.ID von Watchlist"));
        selectStatement.setObject(2, Application.getInstance().getData().get("username"));
        ResultSet result = selectStatement.executeQuery();
        if (!result.next()) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Enthalt SET Bezeichnung = ? WHERE Bezeichnung = ? AND WatchlistId = ?");
        preparedStatement.setObject(1, newData.get("Enthalt.Video"));
        preparedStatement.setObject(2, oldData.get("Enthalt.Video"));
        preparedStatement.setObject(3, oldData.get("Enthalt.ID von Watchlist"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        PreparedStatement selectStatement = Application.getInstance().getConnection().prepareStatement("SELECT Benutzername FROM Watchlist WHERE ID = ? AND Benutzername = ?");
        selectStatement.setObject(1, data.get("Enthalt.ID von Watchlist"));
        selectStatement.setObject(2, Application.getInstance().getData().get("username"));
        ResultSet result = selectStatement.executeQuery();
        if (!result.next()) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Enthalt WHERE Bezeichnung = ? AND WatchlistId = ?");
        preparedStatement.setObject(1, data.get("Enthalt.Video"));
        preparedStatement.setObject(2, data.get("Enthalt.ID von Watchlist"));
        preparedStatement.executeUpdate();
    }
}
