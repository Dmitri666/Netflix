package com.watchandchill.table.movies;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TrailersOnMovies extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT Bezeichnung as Trailer,Qualitat,FilmBezeichnung as Film FROM Trailer ";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE FilmBezeichnung LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return "SELECT Bezeichnung as Trailer,FilmBezeichnung as Film,Qualitat FROM Trailer  WHERE Bezeichnung = '" + data.get("Trailer.Trailer") + "'";
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }

        PreparedStatement pstmt = Application.getInstance().getConnection().prepareStatement("INSERT INTO Trailer(Bezeichnung,FilmBezeichnung,Qualitat) VALUES(?,?,?)");
        pstmt.setObject(1, data.get("Trailer.Trailer"));
        pstmt.setObject(2, data.get("Trailer.Film"));
        pstmt.setObject(3, data.get("Trailer.Qualitat"));
        pstmt.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Trailer SET Bezeichnung = ?,FilmBezeichnung = ?,Qualitat = ? WHERE Bezeichnung = ?");
        preparedStatement.setObject(1, newData.get("Trailer.Trailer"));
        preparedStatement.setObject(2, newData.get("Trailer.Film"));
        preparedStatement.setObject(3, newData.get("Trailer.Qualitat"));
        preparedStatement.setObject(4, oldData.get("Trailer.Trailer"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().
                prepareStatement("DELETE FROM Trailer WHERE Bezeichnung = ?");
        preparedStatement.setObject (1 , data.get("Trailer.Trailer"));
        preparedStatement.executeUpdate () ;
    }
}
