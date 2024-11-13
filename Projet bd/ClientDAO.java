import java.util.*;
import java.sql.*;

// Interface ClientDAO
public interface ClientDAO {
    void ajouterClient(Client client) throws SQLException;
    Client obtenirClientParId(int idClient) throws SQLException;
    List<Client> obtenirTousLesClients() throws SQLException;
}

