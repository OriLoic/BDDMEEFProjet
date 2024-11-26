package com.models;

import com.dao.LocationAbonneDAO;

import java.sql.SQLException;

public class LocationAbonne {
    private Support supportLocation;
    private int dureeLocation;

    public LocationAbonne(Support supportLocation, int dureeLocation) {
        this.supportLocation = supportLocation;
        this.dureeLocation = dureeLocation;
    }

    // Getters
    public Support getSupportLocation() {
        return supportLocation;
    }

    public int getDureeLocation() {
        return dureeLocation;
    }

    // Ajouter une location
    public void add() throws SQLException {
        LocationAbonneDAO.addLocationAbonne(this);
    }
}

