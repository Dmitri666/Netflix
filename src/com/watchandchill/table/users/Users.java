package com.watchandchill.table.users;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Users extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT EMail, Benutzername FROM Nutzer";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE Benutzername LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return "SELECT Benutzername , EMail  FROM Nutzer  WHERE Benutzername = '" + data.get("Nutzer.Benutzername") + "'";
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + ".insertRowWithData(Data) nicht implementiert.");
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if (!Application.getInstance().getData().get("username").equals(oldData.get("Nutzer.Benutzername"))) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Nutzer SET Benutzername = ? , EMail = ? WHERE Benutzername = ?");
        preparedStatement.setObject(1, newData.get("Nutzer.Benutzername"));
        preparedStatement.setObject(2, newData.get("Nutzer.EMail"));
        preparedStatement.setObject(3, oldData.get("Nutzer.Benutzername"));

        preparedStatement.executeUpdate();

        Application.getInstance().getData().replace("username",newData.get("Nutzer.Benutzername"));
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + ".deleteRowWithData(Data) nicht implementiert.");
    }
}
