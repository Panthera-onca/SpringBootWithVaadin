package com.vaadin.tutorial.crm.backend.entity;

public enum Disponibilite {
	DISPONIBLE("Disponible"), INDISPONIBLE("Indisponible"), EMPRUNTE("Emprunté");

    private final String nom;

    private Disponibilite(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return nom;
    }

}
