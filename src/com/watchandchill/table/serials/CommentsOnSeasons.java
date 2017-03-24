package com.watchandchill.table.serials;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentsOnSeasons extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT K.ID AS ID,K.Kommentar,K.Benutzername as Premiumnutzer,ST.Staffelnummer , S.Serienname AS Serie FROM Staffel_Kommentar K , Staffel ST, Serie S " + "" +
                " WHERE K.StaffelId = ST.ID AND ST.SerieID = S.ID";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " AND S.Serienname LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return "SELECT ID AS \"ID von Kommentar\", Kommentar, Benutzername as \"Premiumnutzer\",StaffelId AS \"ID von Staffel\" FROM Staffel_Kommentar  WHERE ID = '" + data.get("Staffel_Kommentar.ID") + "'";
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 1) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }
        PreparedStatement pstmt = Application.getInstance().getConnection().prepareStatement("INSERT INTO Staffel_Kommentar(Kommentar,Benutzername,StaffelId) VALUES(?,?,?)");
        pstmt.setObject(1, data.get("Staffel_Kommentar.Kommentar"));
        pstmt.setObject(2, Application.getInstance().getData().get("username"));
        pstmt.setObject(3, data.get("Staffel_Kommentar.ID von Staffel"));
        pstmt.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 1) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }

        PreparedStatement selectStatement = Application.getInstance().getConnection().prepareStatement("SELECT Benutzername FROM Staffel_Kommentar WHERE ID = ? AND Benutzername = ?");
        selectStatement.setObject(1, oldData.get("Staffel_Kommentar.ID von Kommentar"));
        selectStatement.setObject(2, Application.getInstance().getData().get("username"));
        ResultSet result = selectStatement.executeQuery();
        if (!result.next()) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Staffel_Kommentar SET Kommentar = ? WHERE ID = ?");
        preparedStatement.setObject(1, newData.get("Staffel_Kommentar.Kommentar"));
        preparedStatement.setObject(2, oldData.get("Staffel_Kommentar.ID von Kommentar"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 1) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }

        PreparedStatement selectStatement = Application.getInstance().getConnection().prepareStatement("SELECT Benutzername FROM Staffel_Kommentar WHERE ID = ? AND Benutzername = ?");
        selectStatement.setObject(1, data.get("Staffel_Kommentar.ID"));
        selectStatement.setObject(2, Application.getInstance().getData().get("username"));
        ResultSet result = selectStatement.executeQuery();
        if (!result.next()) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().
                prepareStatement("DELETE FROM Staffel_Kommentar WHERE ID = ?");
        preparedStatement.setObject (1 , data.get("Staffel_Kommentar.ID"));
        preparedStatement.executeUpdate () ;
    }
}
