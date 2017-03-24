package com.watchandchill.table.genre;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VideosOnGenres extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT Video_Bezeichnung AS  Video ,Genre_Bezeichnung AS  Genre  FROM Gehort_an ";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE Genre LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return "SELECT Genre_Bezeichnung AS  Genre ,Video_Bezeichnung AS  Video FROM Gehort_an " +
                " WHERE Genre_Bezeichnung = '" + data.get("Gehort_an.Genre") + "' AND Video_Bezeichnung = '"  + data.get("Gehort_an.Video") + "'" ;

    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Gehort_an(Genre_Bezeichnung, Video_Bezeichnung) VALUES (?, ?)");
        preparedStatement.setObject(1, data.get("Gehort_an.Genre"));
        preparedStatement.setObject(2, data.get("Gehort_an.Video"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Gehort_an SET Genre_Bezeichnung = ?, Video_Bezeichnung = ? WHERE Genre_Bezeichnung = ? AND Video_Bezeichnung = ?");
        preparedStatement.setObject(1, newData.get("Gehort_an.Genre"));
        preparedStatement.setObject(2, newData.get("Gehort_an.Video"));
        preparedStatement.setObject(3, oldData.get("Gehort_an.Genre"));
        preparedStatement.setObject(4, oldData.get("Gehort_an.Video"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Gehort_an WHERE Genre_Bezeichnung = ? AND Video_Bezeichnung = ?");
        preparedStatement.setObject(1, data.get("Gehort_an.Genre"));
        preparedStatement.setObject(2, data.get("Gehort_an.Video"));
        preparedStatement.executeUpdate();
    }
}
