import java.util.*;
import java.sql.*;


// Interface LocationDAO
public interface LocationDAO {
    void ajouterLocation(Location location) throws SQLException;
    Location obtenirLocationParId(int idLocation) throws SQLException;
    List<Location> obtenirToutesLesLocations() throws SQLException;
}
