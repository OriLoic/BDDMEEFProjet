package models;

import dao.LocationCarteCreditDAO;
import java.sql.SQLException;

public class LocationCarteCredit {
    private Support supportLocation;
    private int dureeLocation;

    public LocationCarteCredit(Support supportLocation, int dureeLocation) {
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
    public void add(LocationCarteCreditDAO locationCarteCreditDAO) throws SQLException {
        locationCarteCreditDAO.addLocationCarteCredit(this);
    }
}


