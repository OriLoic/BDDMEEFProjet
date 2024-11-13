import java.util.Date;


// Classe CarteAbonnement
public class CarteAbonnement {
    private int idCarte;
    private float solde;
    private Date dateCreation;
    private static final float montantMinimum = 15.0f;

    // Constructeur
    public CarteAbonnement(int idCarte, float solde, Date dateCreation) {
        this.idCarte = idCarte;
        this.solde = solde;
        this.dateCreation = dateCreation;
    }

    // Getters et Setters
    public int getIdCarte() { return idCarte; }
    public void setIdCarte(int idCarte) { this.idCarte = idCarte; }
    public float getSolde() { return solde; }
    public void setSolde(float solde) { this.solde = solde; }
    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }

    // Méthodes
    public void crediter(float montant) { this.solde += montant; }
    public boolean debiter(float montant) {
        if (this.solde >= montant) {
            this.solde -= montant;
            return true;
        }
        return false;
    }
    public boolean verifierSolde() { return this.solde >= montantMinimum; }
}