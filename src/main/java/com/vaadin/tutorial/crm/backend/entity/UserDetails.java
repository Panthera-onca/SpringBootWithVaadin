package com.vaadin.tutorial.crm.backend.entity;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "USER_DETAILS")
public class UserDetails extends AbstractEntity implements Cloneable{
	private Long id;

    @NotNull
    @NotEmpty
    @Length(min = 1, max = 32)
    private String nom;
    @NotNull
    @NotEmpty
    @Length(min = 1, max = 32)
    private String prenom;

    @NotNull
    @NotEmpty
    @Length(min = 4, max = 64)
    private String handle;


    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Length(min = 8, max = 64)
    private String password;

	public UserDetails() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", handle=" + handle + ", email="
				+ email + ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(email, handle, id, nom, password, prenom);
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
		UserDetails other = (UserDetails) obj;
		return Objects.equals(email, other.email) && Objects.equals(handle, other.handle)
				&& Objects.equals(id, other.id) && Objects.equals(nom, other.nom)
				&& Objects.equals(password, other.password) && Objects.equals(prenom, other.prenom);
	}
    
    

}
