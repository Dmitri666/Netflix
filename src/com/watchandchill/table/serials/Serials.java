package com.watchandchill.table.serials;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Serials extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT ID,Serienname FROM Serie";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE Serienname LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return "SELECT ID,Serienname FROM Serie  WHERE ID = " + data.get("Serie.ID");
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }

        PreparedStatement pstmt = Application.getInstance().getConnection().prepareStatement("INSERT INTO Serie(Serienname) VALUES(?)");
        pstmt.setObject(1, data.get("Serie.Serienname"));
        pstmt.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Serie SET Serienname = ? WHERE ID = ?");
        preparedStatement.setObject(1, newData.get("Serie.Serienname"));
        preparedStatement.setObject(2, oldData.get("Serie.ID"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().
                prepareStatement("DELETE FROM Serie WHERE ID = ?");
        preparedStatement.setObject (1 , data.get("Serie.ID"));
        preparedStatement.executeUpdate () ;
    }
}
