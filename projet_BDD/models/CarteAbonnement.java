package models;

import dao.CarteAbonnementDAO;
import java.sql.SQLException;
import java.util.ArrayList;

public class CarteAbonnement {
    private int id;
    private float solde;
    private int nbFilmsLoues;
    private ArrayList<Film> historique;

    public CarteAbonnement(int id, float solde, int nbFilmsLoues, ArrayList<Film> historique) {
        this.id = id;
        this.solde = solde;
        this.nbFilmsLoues = nbFilmsLoues;
        this.historique = historique;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public float getSolde() {
        return solde;
    }

    public void setSolde(float solde) {
        if (solde >= 0) {
            this.solde = solde;
        } else {
            throw new IllegalArgumentException("Le solde ne peut pas être négatif !");
        }
    }

    public int getNbFilmsLoues() {
        return nbFilmsLoues;
    }

    public void setNbFilmsLoues(int nbFilmsLoues) {
        this.nbFilmsLoues = nbFilmsLoues;
    }

    public ArrayList<Film> getHistorique() {
        return historique;
    }

    public void setHistorique(ArrayList<Film> historique) {
        this.historique = historique;
    }

    // Ajouter une nouvelle carte d'abonnement
    public void add(CarteAbonnementDAO carteAbonnementDAO) throws SQLException {
        carteAbonnementDAO.addCarteAbonnement(this);
    }

    // Rechercher une carte d'abonnement par ID
    public static CarteAbonnement findById(int id, CarteAbonnementDAO carteAbonnementDAO) throws SQLException {
        return carteAbonnementDAO.findById(id);
    }

    // Mettre à jour une carte d'abonnement
    public void update(CarteAbonnementDAO carteAbonnementDAO) throws SQLException {
        carteAbonnementDAO.updateCarteAbonnement(this);
    }
}



