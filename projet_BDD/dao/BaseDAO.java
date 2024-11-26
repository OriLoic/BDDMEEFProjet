package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDAO {

    // Informations de connexion
    private static final String URL = "jdbc:mysql://localhost:3306/projet-BD";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // Obtenir une connexion à la base de données
    protected static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
