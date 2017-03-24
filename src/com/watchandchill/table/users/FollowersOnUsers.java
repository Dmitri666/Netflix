package com.watchandchill.table.users;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FollowersOnUsers extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT Benutzername AS Follower , Benutzername1 AS Premiumnutzer  FROM Folgt ";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE Benutzername1 LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return "SELECT Benutzername AS Follower ,Benutzername1 AS Premiumnutzer FROM Folgt  WHERE Benutzername = '" + data.get("Folgt.Follower") + "' AND Benutzername1 = '" + data.get("Folgt.Premiumnutzer") + "'";
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 1) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Folgt(Benutzername, Benutzername1) VALUES (?, ?)");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("Folgt.Premiumnutzer"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 1) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        } else if (!Application.getInstance().getData().get("username").equals(oldData.get("Folgt.Follower"))) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Folgt SET Benutzername1 = ? WHERE Benutzername = ? AND Benutzername1 = ? ");
        preparedStatement.setObject(1, newData.get("Folgt.Premiumnutzer"));
        preparedStatement.setObject(2, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(3, oldData.get("Folgt.Premiumnutzer"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 1) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        } else if (!Application.getInstance().getData().get("username").equals(data.get("Folgt.Follower"))) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Folgt WHERE Benutzername = ? AND Benutzername1 = ?");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("Folgt.Premiumnutzer"));
        preparedStatement.executeUpdate();
    }
}
