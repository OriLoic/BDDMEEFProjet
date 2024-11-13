import java.util.*;
import java.sql.*;


// Implémentation ClientDAO
public class ClientDAOImpl implements ClientDAO {
    private Connection connection;

    public ClientDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void ajouterClient(Client client) throws SQLException {
        String sql = "INSERT INTO client (idClient, nom, prenom, email, typeCarte) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, client.getIdClient());
            stmt.setString(2, client.getNom());
            stmt.setString(3, client.getPrenom());
            stmt.setString(4, client.getEmail());
            stmt.setString(5, client.getTypeCarte());
            stmt.executeUpdate();
        }
    }

    @Override
    public Client obtenirClientParId(int idClient) throws SQLException {
        String sql = "SELECT * FROM client WHERE idClient = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idClient);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                        rs.getInt("idClient"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("typeCarte")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Client> obtenirTousLesClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(new Client(
                    rs.getInt("idClient"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("typeCarte")
                ));
            }
        }
        return clients;
    }
}
