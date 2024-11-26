package models;

import dao.ClientDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class Client {
    private String nom;
    private ArrayList<Integer> numerosCartes;  // Liste des numéros de carte (carte de crédit et abonnement)

    public Client(String nom, ArrayList<Integer> numerosCartes) {
        this.nom = nom;
        this.numerosCartes = numerosCartes;
    }

    // Getters et setters
    public String getNom() {
        return nom;
    }

    public ArrayList<Integer> getNumerosCartes() {
        return numerosCartes;
    }

    // Méthode pour ajouter un numéro de carte
    public void addCarte(int numeroCarte) {
        if (numerosCartes == null) {
            numerosCartes = new ArrayList<>();
        }
        numerosCartes.add(numeroCarte);
    }

    // Ajouter un client
    public void add() throws SQLException {
        ClientDAO.addClient(this);
    }

    // Trouver un client par numéro de carte
    public static Client findByNumeroCarte(int numeroCarte) throws SQLException {
        return ClientDAO.findByNumeroCarte(numeroCarte);
    }
}
