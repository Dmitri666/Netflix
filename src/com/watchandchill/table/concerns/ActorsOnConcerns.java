package com.watchandchill.table.concerns;

import com.alexanderthelen.applicationkit.database.Data;
import com.alexanderthelen.applicationkit.database.Table;
import com.watchandchill.Application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActorsOnConcerns extends Table {
    @Override
    public String getSelectQueryForTableWithFilter(String filter) throws SQLException {
        String selectQuery = "SELECT Benutzername AS  Schauspieler ,Bezeichnung AS  Medienkonzern FROM Steht_unter_Vertrag ";
        if ( filter != null && ! filter .isEmpty() )
        {
            selectQuery += " WHERE Bezeichnung LIKE '%" + filter + "%'";
        }
        return selectQuery;
    }

    @Override
    public String getSelectQueryForRowWithData(Data data) throws SQLException {
        return "SELECT Benutzername AS  Schauspieler ,Bezeichnung AS  Medienkonzern FROM Steht_unter_Vertrag " + "" +
                " WHERE Bezeichnung = '" + data.get("Steht_unter_Vertrag.Medienkonzern") + "' AND Benutzername = '"  + data.get("Steht_unter_Vertrag.Schauspieler") + "'" ;

    }

    @Override
    public void insertRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("INSERT INTO Steht_unter_Vertrag(Benutzername, Bezeichnung) VALUES (?, ?)");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("Steht_unter_Vertrag.Medienkonzern"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateRowWithData(Data oldData, Data newData) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        }  else if (!Application.getInstance().getData().get("username").equals(oldData.get("Steht_unter_Vertrag.Schauspieler"))) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("UPDATE Steht_unter_Vertrag SET Bezeichnung = ? WHERE Benutzername = ? AND Bezeichnung = ?");
        preparedStatement.setObject(1, newData.get("Steht_unter_Vertrag.Medienkonzern"));
        preparedStatement.setObject(2, oldData.get("Steht_unter_Vertrag.Schauspieler"));
        preparedStatement.setObject(3, oldData.get("Steht_unter_Vertrag.Medienkonzern"));
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteRowWithData(Data data) throws SQLException {
        if ((Integer) Application.getInstance().getData().get("permission") > 0) {
            throw new SQLException("Nicht die notwendigen Rechte.");
        } else if (!Application.getInstance().getData().get("username").equals(data.get("Steht_unter_Vertrag.Schauspieler"))) {
            throw new SQLException("Nicht der gleiche Nutzer.");
        }

        PreparedStatement preparedStatement = Application.getInstance().getConnection().prepareStatement("DELETE FROM Steht_unter_Vertrag WHERE Benutzername = ? AND Bezeichnung = ?");
        preparedStatement.setObject(1, Application.getInstance().getData().get("username"));
        preparedStatement.setObject(2, data.get("Steht_unter_Vertrag.Medienkonzern"));
        preparedStatement.executeUpdate();
    }
}
