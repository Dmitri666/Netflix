package com.watchandchill.table.concerns;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VideosOnConcerns extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT Bezeichnung,Spieldauer,Erscheinungsjahr,Informationen,Medienkonzern_Bezeichnung AS Medienkonzern FROM Video";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE Medienkonzern_Bezeichnung LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return "SELECT Bezeichnung AS \"Video\",Spieldauer AS \"Spieldauer\",Erscheinungsjahr AS \"Erscheinungsjahr\",Informationen AS \"Informationen\",Medienkonzern_Bezeichnung AS \"Medienkonzern\" FROM Video" +
        " WHERE Bezeichnung = '" + data.get("Video.Bezeichnung") + "'";

    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Video(Bezeichnung, Spieldauer, Erscheinungsjahr,Informationen,Medienkonzern_Bezeichnung) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setObject(1, data.get("Video.Video"));
        preparedStatement.setObject(2, data.get("Video.Spieldauer"));
        preparedStatement.setObject(3, data.get("Video.Erscheinungsjahr"));
        preparedStatement.setObject(4, data.get("Video.Informationen"));
        preparedStatement.setObject(5, data.get("Video.Medienkonzern"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Video SET Bezeichnung = ?, Spieldauer = ? , Erscheinungsjahr = ? , Informationen = ? , Medienkonzern_Bezeichnung = ? WHERE Bezeichnung = ?");
        preparedStatement.setObject(1, newData.get("Video.Video"));
        preparedStatement.setObject(2, newData.get("Video.Spieldauer"));
        preparedStatement.setObject(3, newData.get("Video.Erscheinungsjahr"));
        preparedStatement.setObject(1, newData.get("Video.Informationen"));
        preparedStatement.setObject(2, newData.get("Video.Medienkonzern"));
        preparedStatement.setObject(3, oldData.get("Video.Video"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Video WHERE Bezeichnung = ?");
        preparedStatement.setObject(1, data.get("Video.Bezeichnung"));
        preparedStatement.executeUpdate();
    }
}
