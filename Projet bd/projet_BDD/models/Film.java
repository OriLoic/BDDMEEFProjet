package models;

import dao.FilmDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Film {
    private int idFilm;
    private String titre;
    private String realisateur;
    private ArrayList<String> acteurs;
    private String theme;
    private ArrayList<Support> supportDisponible;
    private String dateSortie;
    private int noteFilm;

    public Film(int idFilm, String titre, String realisateur, ArrayList<String> acteurs, String theme,
                ArrayList<Support> supportDisponible, String dateSortie, int noteFilm) {
        this.idFilm = idFilm;
        this.titre = titre;
        this.realisateur = realisateur;
        this.acteurs = acteurs;
        this.theme = theme;
        this.supportDisponible = supportDisponible;
        this.dateSortie = dateSortie;
        this.noteFilm = noteFilm;
    }
    public String getDetails() {
        StringBuilder details = new StringBuilder();
    
        details.append("Film ID: ").append(idFilm).append("\n");
        details.append("Titre: ").append(titre).append("\n");
        details.append("Réalisateur: ").append(realisateur).append("\n");
    
        if (acteurs != null && !acteurs.isEmpty()) {
            details.append("Acteurs: ").append(String.join(", ", acteurs)).append("\n");
        } else {
            details.append("Acteurs: Aucun renseigné\n");
        }
    
        details.append("Thème: ").append(theme).append("\n");
    
        if (supportDisponible != null && !supportDisponible.isEmpty()) {
            details.append("Supports disponibles: ")
                    .append(String.join(", ", supportDisponible.stream()
                            .map(Support::name)
                            .toArray(String[]::new)))
                    .append("\n");
        } else {
            details.append("Supports disponibles: Aucun\n");
        }
    
        details.append("Date de sortie: ").append(dateSortie).append("\n");
        details.append("Note du film: ").append(noteFilm).append("/10\n");
    
        return details.toString();
    }
    
    // Getters et setters
    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public ArrayList<String> getActeurs() {
        return acteurs;
    }

    public void setActeurs(ArrayList<String> acteurs) {
        this.acteurs = acteurs;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public ArrayList<Support> getSupportDisponible() {
        return supportDisponible;
    }

    public void setSupportDisponible(ArrayList<Support> supportDisponible) {
        this.supportDisponible = supportDisponible;
    }

    public String getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(String dateSortie) {
        this.dateSortie = dateSortie;
    }

    public int getNoteFilm() {
        return noteFilm;
    }

    public void setNoteFilm(int noteFilm) {
        this.noteFilm = noteFilm;
    }

    // Méthodes interactives
    public void add(FilmDAO filmDAO) throws SQLException {
        filmDAO.addFilm(this);
    }

    public static Film findById(int idFilm, FilmDAO filmDAO) throws SQLException {
        return filmDAO.findById(idFilm);
    }

    public static List<Film> search(String criteria, FilmDAO filmDAO) throws SQLException {
        return filmDAO.search(criteria);
    }
}
