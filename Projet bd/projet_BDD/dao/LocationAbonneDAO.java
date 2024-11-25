package dao;

import models.LocationAbonne;
import models.Support;

import java.sql.*;

public class LocationAbonneDAO {
    private Connection connection;

    public LocationAbonneDAO(Connection connection) {
        this.connection = connection;
    }

    // Ajouter une location abonné
    public void addLocationAbonne(LocationAbonne location) throws SQLException {
        String query = "INSERT INTO location_abonne (supportLocation, dureeLocation) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, location.getSupportLocation().name());
            stmt.setInt(2, location.getDureeLocation());
            stmt.executeUpdate();
        }
    }
}
