package models;

import dao.LocationAbonneDAO;
import java.sql.SQLException;

public class LocationAbonne {
    private Support supportLocation;
    private int dureeLocation;

    public LocationAbonne(Support supportLocation, int dureeLocation) {
        this.supportLocation = supportLocation;
        this.dureeLocation = dureeLocation;
    }

    // Getters et setters
    public Support getSupportLocation() {
        return supportLocation;
    }

    public void setSupportLocation(Support supportLocation) {
        this.supportLocation = supportLocation;
    }

    public int getDureeLocation() {
        return dureeLocation;
    }

    public void setDureeLocation(int dureeLocation) {
        this.dureeLocation = dureeLocation;
    }

    // Méthodes interactives
    public void add(LocationAbonneDAO locationAbonneDAO) throws SQLException {
        locationAbonneDAO.addLocationAbonne(this);
    }
}

