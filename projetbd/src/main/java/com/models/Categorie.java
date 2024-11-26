package com.models;

import com.dao.CategorieDAO;
import java.sql.SQLException;

public class Categorie {
    private String nom;

    public Categorie(String nom) {
        this.nom = nom;
    }

    // Getters et setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Méthodes interactives
    public void add(CategorieDAO categorieDAO) throws SQLException {
        categorieDAO.addCategorie(this);
    }
}
