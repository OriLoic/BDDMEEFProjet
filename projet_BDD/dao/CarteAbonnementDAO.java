package dao;

import models.CarteAbonnement;

import java.sql.*;
import java.util.ArrayList;

public class CarteAbonnementDAO extends BaseDAO {

    // Ajouter une carte d'abonnement
    public static void addCarteAbonnement(CarteAbonnement carte) throws SQLException {
        String query = "INSERT INTO carte_abonnement (numeroCarte, solde, nbFilmsLoues) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, carte.getNumeroCarte());
            stmt.setFloat(2, carte.getSolde());
            stmt.setInt(3, carte.getNbFilmsLoues());
            stmt.executeUpdate();
        }
    }

    // Trouver une carte d'abonnement par numéro de carte
    public static CarteAbonnement findByNumeroCarte(int numeroCarte) throws SQLException {
        String query = "SELECT * FROM carte_abonnement WHERE numeroCarte = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, numeroCarte);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    float solde = rs.getFloat("solde");
                    int nbFilmsLoues = rs.getInt("nbFilmsLoues");
                    ArrayList<Film> historique = new ArrayList<>();
                    return new CarteAbonnement(numeroCarte, solde, nbFilmsLoues, historique);
                }
            }
        }
        return null;
    }

    // Mettre à jour une carte d'abonnement
    public static void updateCarteAbonnement(CarteAbonnement carte) throws SQLException {
        String query = "UPDATE carte_abonnement SET solde = ?, nbFilmsLoues = ? WHERE numeroCarte = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setFloat(1, carte.getSolde());
            stmt.setInt(2, carte.getNbFilmsLoues());
            stmt.setInt(3, carte.getNumeroCarte());
            stmt.executeUpdate();
        }
    }
}

