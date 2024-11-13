
import java.util.Date;


// Classe Location
public class Location {
    private int idLocation;
    private Date dateLocation;
    private String support;
    private int duree;
    private float prix;

    // Constructeur
    public Location(int idLocation, Date dateLocation, String support, int duree, float prix) {
        this.idLocation = idLocation;
        this.dateLocation = dateLocation;
        this.support = support;
        this.duree = duree;
        this.prix = prix;
    }

    // Getters et Setters
    public int getIdLocation() { return idLocation; }
    public void setIdLocation(int idLocation) { this.idLocation = idLocation; }
    public Date getDateLocation() { return dateLocation; }
    public void setDateLocation(Date dateLocation) { this.dateLocation = dateLocation; }
    public String getSupport() { return support; }
    public void setSupport(String support) { this.support = support; }
    public int getDuree() { return duree; }
    public void setDuree(int duree) { this.duree = duree; }
    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }

    // Méthode pour calculer le coût
    public float calculerCout() { return this.duree * this.prix; }
}

