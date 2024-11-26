package com.dao;

import com.models.Client;

import java.sql.*;
import java.util.ArrayList;

public class ClientDAO extends BaseDAO {

    // Ajouter un client
    public static void addClient(Client client) throws SQLException {
        String query = "INSERT INTO clients (nom, numerosCartes) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, client.getNom());
            String numerosCartes = String.join(",", 
                client.getNumerosCartes().stream().map(String::valueOf).toArray(String[]::new));
            stmt.setString(2, numerosCartes);
            stmt.executeUpdate();
        }
    }
    

    // Trouver un client par numéro de carte
    public static Client findByNumeroCarte(int numeroCarte) throws SQLException {
        String query = "SELECT * FROM clients WHERE ',' || numerosCartes || ',' LIKE ?"; 
        
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, numeroCarte);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    String numerosCartesStr = rs.getString("numerosCartes");
                    // Convertir la chaîne de numéros de cartes en ArrayList
                    ArrayList<Integer> numerosCartes = new ArrayList<>();
                    for (String numero : numerosCartesStr.split(",")) {
                        numerosCartes.add(Integer.parseInt(numero.trim()));
                    }
                    return new Client(nom, numerosCartes);
                }
            }
        }
        return null;
    }
}
