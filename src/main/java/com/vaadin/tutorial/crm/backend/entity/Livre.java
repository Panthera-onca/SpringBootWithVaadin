package com.vaadin.tutorial.crm.backend.entity;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
public class Livre extends AbstractEntity implements Cloneable{
	
	
	public enum Categorie {
	    SYSTEME, DEVELOPPEMENT, WEB
	  }
	public enum Campus{
		Campus_de_Rennes, Campus_Nantes_Faraday, Campus_de_Quimper
	}
	
	private @Id @GeneratedValue Long id;
	
	@NotNull
	@NotEmpty
	private String titreLivre = "";
	
	@NotNull
	@NotEmpty
	private String description = "";
	
	@NotNull
	@NotEmpty
	private String auteur = "";
	
	@NotNull
	@NotEmpty
	private String refeni;
	
	@NotNull
	@NotEmpty
	private String isbn;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Campus campus;
	
	@Enumerated(EnumType.STRING)
	@NotNull
    private Disponibilite disponibilite = Disponibilite.DISPONIBLE;
	
	@ManyToOne
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;
	
	@Min(value = 0, message = "Can't have negative amount in stock")
    private int stockCount = 0;
	
	
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Livre.Categorie categorie;
	
	
	
	
	
	
	public Livre() {}






	public Livre(Long id, @NotNull @NotEmpty String titreLivre, @NotNull @NotEmpty String description,
			@NotNull @NotEmpty String auteur, @NotNull @NotEmpty String refeni, @NotNull @NotEmpty String isbn,
			@NotNull Campus campus, @NotNull Disponibilite disponibilite, Reservation reservation,
			@Min(value = 0, message = "Can't have negative amount in stock") int stockCount,
			@NotNull Livre.Categorie categorie) {
		this.id = id;
		this.titreLivre = titreLivre;
		this.description = description;
		this.auteur = auteur;
		this.refeni = refeni;
		this.isbn = isbn;
		this.campus = campus;
		this.disponibilite = disponibilite;
		this.reservation = reservation;
		this.stockCount = stockCount;
		this.categorie = categorie;
	}






	public Long getId() {
		return id;
	}






	public void setId(Long id) {
		this.id = id;
	}






	public String getTitreLivre() {
		return titreLivre;
	}






	public void setTitreLivre(String titreLivre) {
		this.titreLivre = titreLivre;
	}






	public String getDescription() {
		return description;
	}






	public void setDescription(String description) {
		this.description = description;
	}






	public String getAuteur() {
		return auteur;
	}






	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}






	public String getRefeni() {
		return refeni;
	}






	public void setRefeni(String refeni) {
		this.refeni = refeni;
	}






	public String getIsbn() {
		return isbn;
	}






	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}






	public Campus getCampus() {
		return campus;
	}






	public void setCampus(Campus campus) {
		this.campus = campus;
	}






	public Disponibilite getDisponibilite() {
		return disponibilite;
	}






	public void setDisponibilite(Disponibilite disponibilite) {
		this.disponibilite = disponibilite;
	}






	public Reservation getReservation() {
		return reservation;
	}






	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}






	public int getStockCount() {
		return stockCount;
	}






	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}






	public Livre.Categorie getCategorie() {
		return categorie;
	}






	public void setCategorie(Livre.Categorie categorie) {
		this.categorie = categorie;
	}






	@Override
	public String toString() {
		return "Livre [id=" + id + ", titreLivre=" + titreLivre + ", description=" + description + ", auteur=" + auteur
				+ ", refeni=" + refeni + ", isbn=" + isbn + ", campus=" + campus + ", disponibilite=" + disponibilite
				+ ", reservation=" + reservation + ", stockCount=" + stockCount + ", categorie=" + categorie + "]";
	}






	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(auteur, campus, categorie, description, disponibilite, id, isbn, refeni,
				reservation, stockCount, titreLivre);
		return result;
	}






	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livre other = (Livre) obj;
		return Objects.equals(auteur, other.auteur) && campus == other.campus && categorie == other.categorie
				&& Objects.equals(description, other.description) && disponibilite == other.disponibilite
				&& Objects.equals(id, other.id) && Objects.equals(isbn, other.isbn)
				&& Objects.equals(refeni, other.refeni) && Objects.equals(reservation, other.reservation)
				&& stockCount == other.stockCount && Objects.equals(titreLivre, other.titreLivre);
	}




	


	
}
