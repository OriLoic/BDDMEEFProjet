package dao;

import models.Film;
import models.Support;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO extends BaseDAO {

    // Rechercher un film par ID
    public static Film findById(int idFilm) throws SQLException {
        String query = "SELECT * FROM films WHERE idFilm = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idFilm);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Film(
                        rs.getInt("idFilm"),
                        rs.getString("titre"),
                        rs.getString("realisateur"),
                        null, // Les acteurs gérés séparément
                        rs.getString("theme"),
                        null, // Les supports gérés séparément
                        rs.getString("dateSortie"),
                        rs.getInt("noteFilm")
                    );
                }
            }
        }
        return null;
    }

    // Rechercher des films par critères
    public static List<Film> search(String criteria) throws SQLException {
        String query = "SELECT * FROM films WHERE titre LIKE ? OR realisateur LIKE ? OR theme LIKE ?";
        List<Film> films = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            String searchCriteria = "%" + criteria + "%";
            stmt.setString(1, searchCriteria);
            stmt.setString(2, searchCriteria);
            stmt.setString(3, searchCriteria);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    films.add(new Film(
                        rs.getInt("idFilm"),
                        rs.getString("titre"),
                        rs.getString("realisateur"),
                        null,
                        rs.getString("theme"),
                        null,
                        rs.getString("dateSortie"),
                        rs.getInt("noteFilm")
                    ));
                }
            }
        }
        return films;
    }
}
