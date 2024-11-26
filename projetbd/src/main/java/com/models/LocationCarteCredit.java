package com.models;

import com.dao.LocationCarteCreditDAO;

import java.sql.SQLException;

public class LocationCarteCredit {
    private Support supportLocation;
    private int dureeLocation;

    public LocationCarteCredit(Support supportLocation, int dureeLocation) {
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
        LocationCarteCreditDAO.addLocationCarteCredit(this);
    }
}



