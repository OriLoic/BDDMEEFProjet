import java.util.*;



// Classe Film
public class Film {
    private int idFilm;
    private String titre;
    private String realisateur;
    private List<String> acteurs;
    private String theme;
    private String supportDisponible;

    // Constructeur
    public Film(int idFilm, String titre, String realisateur, List<String> acteurs, String theme, String supportDisponible) {
        this.idFilm = idFilm;
        this.titre = titre;
        this.realisateur = realisateur;
        this.acteurs = acteurs;
        this.theme = theme;
        this.supportDisponible = supportDisponible;
    }

    // Getters et Setters
    public int getIdFilm() { return idFilm; }
    public void setIdFilm(int idFilm) { this.idFilm = idFilm; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getRealisateur() { return realisateur; }
    public void setRealisateur(String realisateur) { this.realisateur = realisateur; }
    public List<String> getActeurs() { return acteurs; }
    public void setActeurs(List<String> acteurs) { this.acteurs = acteurs; }
    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
    public String getSupportDisponible() { return supportDisponible; }
    public void setSupportDisponible(String supportDisponible) { this.supportDisponible = supportDisponible; }

    // Méthode pour afficher les détails du film
    public String afficherDetails() {
        return "Titre: " + titre + ", Réalisateur: " + realisateur + ", Acteurs: " + String.join(", ", acteurs);
    }
}
