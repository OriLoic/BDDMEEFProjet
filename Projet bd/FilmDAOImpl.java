import java.util.*;
import java.sql.*;


public class FilmDAOImpl implements FilmDAO {
    private Connection connection;

    public FilmDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void ajouterFilm(Film film) throws SQLException {
        String sql = "INSERT INTO film (idFilm, titre, realisateur, theme, supportDisponible) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, film.getIdFilm());
            stmt.setString(2, film.getTitre());
            stmt.setString(3, film.getRealisateur());
            stmt.setString(4, film.getTheme());
            stmt.setString(5, film.getSupportDisponible());
            stmt.executeUpdate();
        }
    }

    @Override
    public Film obtenirFilmParId(int idFilm) throws SQLException {
        String sql = "SELECT * FROM film WHERE idFilm = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idFilm);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Film(
                        rs.getInt("idFilm"),
                        rs.getString("titre"),
                        rs.getString("realisateur"),
                        Arrays.asList(rs.getString("acteurs").split(",")), // Conversion chaîne -> liste
                        rs.getString("theme"),
                        rs.getString("supportDisponible")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Film> obtenirTousLesFilms() throws SQLException {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT * FROM film";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                films.add(new Film(
                    rs.getInt("idFilm"),
                    rs.getString("titre"),
                    rs.getString("realisateur"),
                    Arrays.asList(rs.getString("acteurs").split(",")), // Conversion chaîne -> liste
                    rs.getString("theme"),
                    rs.getString("supportDisponible")
                ));
            }
        }
        return films;
    }
}

