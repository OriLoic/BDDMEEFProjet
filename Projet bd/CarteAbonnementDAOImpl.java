import java.util.*;
import java.sql.*;

// Implémentation de l'interface CarteAbonnementDAO
public class CarteAbonnementDAOImpl implements CarteAbonnementDAO {
    private Connection connection;

    public CarteAbonnementDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void ajouterCarte(CarteAbonnement carte) throws SQLException {
        String sql = "INSERT INTO carte_abonnement (idCarte, solde, dateCreation) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, carte.getIdCarte());
            stmt.setFloat(2, carte.getSolde());
            stmt.setDate(3, new java.sql.Date(carte.getDateCreation().getTime()));
            stmt.executeUpdate();
        }
    }

    @Override
    public CarteAbonnement obtenirCarteParId(int idCarte) throws SQLException {
        String sql = "SELECT * FROM carte_abonnement WHERE idCarte = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCarte);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new CarteAbonnement(
                        rs.getInt("idCarte"),
                        rs.getFloat("solde"),
                        rs.getDate("dateCreation")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<CarteAbonnement> obtenirToutesLesCartes() throws SQLException {
        List<CarteAbonnement> cartes = new ArrayList<>();
        String sql = "SELECT * FROM carte_abonnement";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cartes.add(new CarteAbonnement(
                    rs.getInt("idCarte"),
                    rs.getFloat("solde"),
                    rs.getDate("dateCreation")
                ));
            }
        }
        return cartes;
    }
}

