package dao;

import models.Film;
import models.Support;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {
    private Connection connection;

    public FilmDAO(Connection connection) {
        this.connection = connection;
    }

    // Ajouter un film
    public void addFilm(Film film) throws SQLException {
        String query = "INSERT INTO films (idFilm, titre, realisateur, theme, dateSortie, noteFilm) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, film.getIdFilm());
            stmt.setString(2, film.getTitre());
            stmt.setString(3, film.getRealisateur());
            stmt.setString(4, film.getTheme());
            stmt.setString(5, film.getDateSortie());
            stmt.setInt(6, film.getNoteFilm());
            stmt.executeUpdate();
        }
    }

    // Recherche un film par ID
    public Film findById(int idFilm) throws SQLException {
        String query = "SELECT * FROM films WHERE idFilm = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idFilm);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Film(
                        rs.getInt("idFilm"),
                        rs.getString("titre"),
                        rs.getString("realisateur"),
                        null, // Les acteurs seront gérés séparément
                        rs.getString("theme"),
                        null, // Supports disponibles gérés séparément
                        rs.getString("dateSortie"),
                        rs.getInt("noteFilm")
                    );
                }
            }
        }
        return null;
    }

    // Recherche des films par critère
    public List<Film> search(String criteria) throws SQLException {
        String query = "SELECT * FROM films WHERE titre LIKE ? OR realisateur LIKE ? OR theme LIKE ?";
        List<Film> films = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
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
