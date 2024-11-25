package dao;

import models.CarteAbonnement;
import models.Film;
import java.sql.*;
import java.util.ArrayList;

public class CarteAbonnementDAO {
    private Connection connection;

    public CarteAbonnementDAO(Connection connection) {
        this.connection = connection;
    }

    // Ajouter une nouvelle carte d'abonnement
    public void addCarteAbonnement(CarteAbonnement carte) throws SQLException {
        String query = "INSERT INTO carte_abonnement (id, solde, nbFilmsLoues) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, carte.getId());
            stmt.setFloat(2, carte.getSolde());
            stmt.setInt(3, carte.getNbFilmsLoues());
            stmt.executeUpdate();
        }
    }

    // Rechercher une carte d'abonnement par ID
    public CarteAbonnement findById(int id) throws SQLException {
        String query = "SELECT * FROM carte_abonnement WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    float solde = rs.getFloat("solde");
                    int nbFilmsLoues = rs.getInt("nbFilmsLoues");

                    // Charger l'historique des films associés à cette carte
                    ArrayList<Film> historique = new ArrayList<>();
                    String historiqueQuery = "SELECT filmId FROM historique_films WHERE carteId = ?";
                    try (PreparedStatement historiqueStmt = connection.prepareStatement(historiqueQuery)) {
                        historiqueStmt.setInt(1, id);
                        try (ResultSet historiqueRs = historiqueStmt.executeQuery()) {
                            while (historiqueRs.next()) {
                                Film film = new FilmDAO(connection).findById(historiqueRs.getInt("filmId"));
                                if (film != null) {
                                    historique.add(film);
                                }
                            }
                        }
                    }

                    return new CarteAbonnement(id, solde, nbFilmsLoues, historique);
                }
            }
        }
        return null;
    }

    // Mettre à jour une carte d'abonnement
    public void updateCarteAbonnement(CarteAbonnement carte) throws SQLException {
        String query = "UPDATE carte_abonnement SET solde = ?, nbFilmsLoues = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setFloat(1, carte.getSolde());
            stmt.setInt(2, carte.getNbFilmsLoues());
            stmt.setInt(3, carte.getId());
            stmt.executeUpdate();
        }
    }
}
