import java.util.*;
import java.sql.*;

// Interface FilmDAO
public interface FilmDAO {
    void ajouterFilm(Film film) throws SQLException;
    Film obtenirFilmParId(int idFilm) throws SQLException;
    List<Film> obtenirTousLesFilms() throws SQLException;
}
