import java.util.*;
import java.sql.*;


public class LocationDAOImpl implements LocationDAO {
    private Connection connection;

    public LocationDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void ajouterLocation(Location location) throws SQLException {
        String sql = "INSERT INTO location (idLocation, dateLocation, support, duree, prix) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, location.getIdLocation());
            stmt.setDate(2, new java.sql.Date(location.getDateLocation().getTime()));
            stmt.setString(3, location.getSupport());
            stmt.setInt(4, location.getDuree());
            stmt.setFloat(5, location.getPrix());
            stmt.executeUpdate();
        }
    }

    @Override
    public Location obtenirLocationParId(int idLocation) throws SQLException {
        String sql = "SELECT * FROM location WHERE idLocation = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idLocation);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Location(
                        rs.getInt("idLocation"),
                        rs.getDate("dateLocation"),
                        rs.getString("support"),
                        rs.getInt("duree"),
                        rs.getFloat("prix")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Location> obtenirToutesLesLocations() throws SQLException {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM location";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                locations.add(new Location(
                    rs.getInt("idLocation"),
                    rs.getDate("dateLocation"),
                    rs.getString("support"),
                    rs.getInt("duree"),
                    rs.getFloat("prix")
                ));
            }
        }
        return locations;
    }
}

