package models;

import dao.CarteAbonnementDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CarteAbonnement {
    private int id;
    private int numeroCarte;  // Numéro de la carte d'abonnement
    private float solde;
    private int nbFilmsLoues;
    private ArrayList<Film> historique;  // Liste des films loués

    public CarteAbonnement(int numeroCarte, float solde, int nbFilmsLoues, ArrayList<Film> historique) {
        this.numeroCarte = numeroCarte;
        this.solde = solde;
        this.nbFilmsLoues = nbFilmsLoues;
        this.historique = historique;
    }

    // Getters et setters
    public int getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(int numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    public float getSolde() {
        return solde;
    }

    public void setSolde(float solde) {
        this.solde = solde;
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

    public void addFilmToHistorique(Film film) {
        if (historique == null) {
            historique = new ArrayList<>();
        }
        historique.add(film);
    }

    // Rechercher une carte par numéro de carte
    public static CarteAbonnement findByNumeroCarte(int numeroCarte) throws SQLException {
        return CarteAbonnementDAO.findByNumeroCarte(numeroCarte);
    }

    // Ajouter une carte
    public void add() throws SQLException {
        CarteAbonnementDAO.addCarteAbonnement(this);
    }

    // Mettre à jour une carte
    public void update() throws SQLException {
        CarteAbonnementDAO.updateCarteAbonnement(this);
    }
}




