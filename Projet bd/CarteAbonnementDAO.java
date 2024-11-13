import java.util.*;
import java.sql.*;


// Interface CarteAbonnementDAO
public interface CarteAbonnementDAO {
    void ajouterCarte(CarteAbonnement carte) throws SQLException;
    CarteAbonnement obtenirCarteParId(int idCarte) throws SQLException;
    List<CarteAbonnement> obtenirToutesLesCartes() throws SQLException;
}
