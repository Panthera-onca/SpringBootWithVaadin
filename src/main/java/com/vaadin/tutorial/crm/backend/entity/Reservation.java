package com.vaadin.tutorial.crm.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.vaadin.tutorial.crm.backend.entity.Livre.Disponibilite;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table
public class Reservation extends AbstractEntity{
	
	
	private @Id @GeneratedValue Long id;
	@OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
	private Livre livre;
	@NotNull
	private LocalDateTime createdAt;
	private String adress;
	
	
	public Reservation() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Livre getLivre() {
		return livre;
	}
	public void setLivre(Livre livre) {
		this.livre = livre;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", livre=" + livre + ", createdAt=" + createdAt + ", adress=" + adress + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(adress, createdAt, id, livre);
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
		Reservation other = (Reservation) obj;
		return Objects.equals(adress, other.adress) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(id, other.id) && Objects.equals(livre, other.livre);
	}
	
	
	
	
	
	
	
}