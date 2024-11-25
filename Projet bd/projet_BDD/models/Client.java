package models;

import dao.ClientDAO;
import java.sql.SQLException;

public class Client {
    private String nom;
    private int numeroCarte;

    public Client(String nom, int numeroCarte) {
        this.nom = nom;
        this.numeroCarte = numeroCarte;
    }

    // Getters et setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(int numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    // Méthodes interactives
    public void add(ClientDAO clientDAO) throws SQLException {
        clientDAO.addClient(this);
    }

    public static Client findByNumeroCarte(int numeroCarte, ClientDAO clientDAO) throws SQLException {
        return clientDAO.findByNumeroCarte(numeroCarte);
    }
}
