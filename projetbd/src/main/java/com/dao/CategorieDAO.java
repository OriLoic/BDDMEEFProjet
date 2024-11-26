package com.dao;

import com.models.Categorie;
import java.sql.*;

public class CategorieDAO {
    private Connection connection;

    public CategorieDAO(Connection connection) {
        this.connection = connection;
    }

    public void addCategorie(Categorie categorie) throws SQLException {
        String query = "INSERT INTO categories (nom) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, categorie.getNom());
            stmt.executeUpdate();
        }
    }
}
