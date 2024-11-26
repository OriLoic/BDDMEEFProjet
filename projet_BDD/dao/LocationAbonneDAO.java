package dao;

import models.LocationAbonne;
import models.Support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LocationAbonneDAO extends BaseDAO {

    // Ajouter une location abonné
    public static void addLocationAbonne(LocationAbonne location) throws SQLException {
        String query = "INSERT INTO location_abonne (supportLocation, dureeLocation) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, location.getSupportLocation().name());
            stmt.setInt(2, location.getDureeLocation());
            stmt.executeUpdate();
        }
    }
}

