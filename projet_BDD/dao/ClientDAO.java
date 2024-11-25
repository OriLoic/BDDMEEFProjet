package dao;

import models.Client;
import java.sql.*;

public class ClientDAO {
    private Connection connection;

    public ClientDAO(Connection connection) {
        this.connection = connection;
    }

    // Ajouter un client
    public void addClient(Client client) throws SQLException {
        String query = "INSERT INTO clients (nom, numeroCarte) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, client.getNom());
            stmt.setInt(2, client.getNumeroCarte());
            stmt.executeUpdate();
        }
    }

    // Recherche un client par numéro de carte
    public Client findByNumeroCarte(int numeroCarte) throws SQLException {
        String query = "SELECT * FROM clients WHERE numeroCarte = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, numeroCarte);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Client(rs.getString("nom"), rs.getInt("numeroCarte"));
                }
            }
        }
        return null;
    }
}
