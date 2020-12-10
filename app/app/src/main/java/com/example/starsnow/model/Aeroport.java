package com.example.starsnow.model;

import java.io.Serializable;

//Classe Aeroport avec nom, latitude, longitude et un objet snowtam
public class Aeroport implements Serializable {
    private String OACI;
    private String nom;
    private Snowtam snowtam;
    private double latitude;
    private double longitude;

    public Aeroport(String OACI) {
        this.setOACI(OACI);
        this.setSnowtam(new Snowtam(OACI));
    }

    public Aeroport(String OACI, String plainCodedSnowtam) {
        this.setOACI(OACI);
        this.setSnowtam(new Snowtam(OACI, plainCodedSnowtam));
    }

    public Aeroport(String OACI, String aeroportName, double latitude, double longitude) {
        this.setOACI(OACI);
        this.setNom(aeroportName);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    public String getOACI() {
        return OACI;
    }

    public void setOACI(String OACI) {
        this.OACI = OACI;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Snowtam getSnowtam() {
        return snowtam;
    }

    public void setSnowtam(Snowtam snowtam) {
        this.snowtam = snowtam;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
