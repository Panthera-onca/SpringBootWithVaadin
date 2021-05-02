package com.vaadin.tutorial.crm.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
public class Reservation extends AbstractEntity{
	
	
	private @Id @GeneratedValue Long id;
	@NotNull
	private LocalDateTime createdAt;
	@OneToMany(mappedBy = "reservation", fetch = FetchType.EAGER)
	private List<Livre> reservations = new LinkedList<>();
	private Disponibilite disponibilite;
	public Reservation(Long id, @NotNull LocalDateTime createdAt, List<Livre> reservations,
			Disponibilite disponibilite) {
		this.id = id;
		this.createdAt = createdAt;
		this.reservations = reservations;
		this.disponibilite = disponibilite;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public List<Livre> getReservations() {
		return reservations;
	}
	public void setReservations(List<Livre> reservations) {
		this.reservations = reservations;
	}
	public Disponibilite getDisponibilite() {
		return disponibilite;
	}
	public void setDisponibilite(Disponibilite disponibilite) {
		this.disponibilite = disponibilite;
	}
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", createdAt=" + createdAt + ", reservations=" + reservations
				+ ", disponibilite=" + disponibilite + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(createdAt, disponibilite, id, reservations);
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
		return Objects.equals(createdAt, other.createdAt) && disponibilite == other.disponibilite
				&& Objects.equals(id, other.id) && Objects.equals(reservations, other.reservations);
	}
	
	
}