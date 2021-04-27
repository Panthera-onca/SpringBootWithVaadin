package com.vaadin.tutorial.crm.backend.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vaadin.tutorial.crm.backend.entity.Livre;

public interface LivreRepository extends JpaRepository<Livre, Long> {

	
	@Query("select l from Livre  l " +
		      "where lower(l.titreLivre) like lower(concat('%', :searchTerm, '%')) " +
		      "or lower(l.auteur) like lower(concat('%', :searchTerm, '%'))")
	List<Livre> search(@Param("searchTerm") String searchTerm);
	
}
