package com.dao;

import com.models.LocationCarteCredit;
import com.models.Support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LocationCarteCreditDAO extends BaseDAO {

    // Ajouter une location carte crédit
    public static void addLocationCarteCredit(LocationCarteCredit location) throws SQLException {
        String query = "INSERT INTO location_carte_credit (supportLocation, dureeLocation) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, location.getSupportLocation().name());
            stmt.setInt(2, location.getDureeLocation());
            stmt.executeUpdate();
        }
    }
}


