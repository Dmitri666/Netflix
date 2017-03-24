package com.watchandchill.table.movies;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;

import java.sql.SQLException;

public class Top10OnMovies extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        return "SELECT Bezeichnung AS Film,sum(Bewertung) * 1.0 /(count(Bewertung) * 1.0) AS Bewertung FROM Bewertet GROUP BY Bezeichnung ORDER BY Bewertung DESC LIMIT 10";
        //String selectQuery = "SELECT  Bezeichnung as Titel FROM Film F, Bewertet B WHERE F.Bezeichnung = B.Bezeichnung GROUP BY  LIMIT 10";

        //return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return null;
    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + ".insertRowWithData(Data) nicht implementiert.");
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        throw new SQLException(getClass().getName() + ".updateRowWithData(Data, Data) nicht implementiert.");
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        throw new SQLException(getClass().getName() + ".deleteRowWithData(Data) nicht implementiert.");
    }
}
