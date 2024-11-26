package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDAO {

    // Informations de connexion pour Oracle
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE"; // Modifier XE si nécessaire
    private static final String USER = "XXX"; // Mettre ici l'utilisateur Oracle
    private static final String PASSWORD = "XXX"; // Mettre ici le mot de passe Oracle

    // Charger le driver Oracle (recommandé)
    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Le driver JDBC Oracle est introuvable.", e);
        }
    }

    // Obtenir une connexion
    protected static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
