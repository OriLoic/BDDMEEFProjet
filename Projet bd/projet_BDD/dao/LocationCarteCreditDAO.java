package dao;

import models.LocationCarteCredit;
import models.Support;

import java.sql.*;

public class LocationCarteCreditDAO {
    private Connection connection;

    public LocationCarteCreditDAO(Connection connection) {
        this.connection = connection;
    }

    // Ajouter une location carte crédit
    public void addLocationCarteCredit(LocationCarteCredit location) throws SQLException {
        String query = "INSERT INTO location_carte_credit (supportLocation, dureeLocation) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, location.getSupportLocation().name());
            stmt.setInt(2, location.getDureeLocation());
            stmt.executeUpdate();
        }
    }
}

