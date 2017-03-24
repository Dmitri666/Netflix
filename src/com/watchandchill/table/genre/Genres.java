package com.watchandchill.table.genre;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Genres extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT Bezeichnung FROM Genre";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE Bezeichnung LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return "SELECT Bezeichnung FROM Genre  WHERE Bezeichnung = '" + data.get("Genre.Bezeichnung") + "'";
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        PreparedStatement pstmt = Application.getInstance().getConnection().prepareStatement("INSERT INTO Genre(Bezeichnung) VALUES(?)");
        pstmt.setObject(1, data.get("Genre.Bezeichnung"));
        pstmt.executeUpdate();

    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Genre SET Bezeichnung = ? WHERE Bezeichnung = ?");
        preparedStatement.setObject(1, newData.get("Genre.Bezeichnung"));
        preparedStatement.setObject(2, oldData.get("Genre.Bezeichnung"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        PreparedStatement preparedStatement = Application.getInstance().getConnection().
                prepareStatement("DELETE FROM Genre WHERE Bezeichnung = ?");
        preparedStatement.setObject (1 , data.get("Genre.Bezeichnung"));
        preparedStatement.executeUpdate () ;
    }
}
