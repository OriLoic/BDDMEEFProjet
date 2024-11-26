package models;

import dao.FilmDAO;

import java.sql.SQLException;
import java.util.List;

public class Film {
    private int idFilm;
    private String titre;
    private String realisateur;
    private List<String> acteurs;
    private String theme;
    private List<Support> supportDisponible;
    private String dateSortie;
    private int noteFilm;

    public Film(int idFilm, String titre, String realisateur, List<String> acteurs, String theme,
                List<Support> supportDisponible, String dateSortie, int noteFilm) {
        this.idFilm = idFilm;
        this.titre = titre;
        this.realisateur = realisateur;
        this.acteurs = acteurs;
        this.theme = theme;
        this.supportDisponible = supportDisponible;
        this.dateSortie = dateSortie;
        this.noteFilm = noteFilm;
    }

    // Getters
    public int getIdFilm() {
        return idFilm;
    }

    public String getTitre() {
        return titre;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public List<Support> getSupportDisponible() {
        return supportDisponible;
    }

    // Rechercher un film par ID
    public static Film findById(int idFilm) throws SQLException {
        return FilmDAO.findById(idFilm);
    }

    // Rechercher des films par critères
    public static List<Film> search(String criteria) throws SQLException {
        return FilmDAO.search(criteria);
    }

    // Méthode pour afficher les détails d’un film
    public String getDetails() {
        return String.format("ID: %d | Titre: %s | Réalisateur: %s | Note: %d/10",
                idFilm, titre, realisateur, noteFilm);
    }
}

