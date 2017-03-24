package com.watchandchill.table.serials;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EpisodesOnSerials extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT F.Bezeichnung AS Folge, ST.Staffelnummer , S.Serienname FROM Folge F, Staffel ST, Serie S " +
                "WHERE F.StaffelId = ST.ID AND ST.SerieID = S.ID ";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " AND S.Serienname LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return "SELECT Bezeichnung AS Video, StaffelId AS \"ID von Staffel\" FROM Folge  WHERE Bezeichnung = '" + data.get("Folge.Folge") + "'";
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }

        PreparedStatement pstmt = Application.getInstance().getConnection().prepareStatement("INSERT INTO Folge(Bezeichnung,StaffelId) VALUES(?,?)");
        pstmt.setObject(1, data.get("Folge.Video"));
        pstmt.setObject(2, data.get("Folge.ID von Staffel"));
        pstmt.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Folge SET Bezeichnung = ?,StaffelId = ? WHERE Bezeichnung = ?");
        preparedStatement.setObject(1, newData.get("Folge.Video"));
        preparedStatement.setObject(2, newData.get("Folge.ID von Staffel"));
        preparedStatement.setObject(2, oldData.get("Folge.Video"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().
                prepareStatement("DELETE FROM Folge WHERE Bezeichnung = ?");
        preparedStatement.setObject (1 , data.get("Folge.Folge"));
        preparedStatement.executeUpdate () ;
    }
}
