package com.vaadin.tutorial.crm.backend.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vaadin.tutorial.crm.backend.entity.Livre;
import com.vaadin.tutorial.crm.backend.entity.Reservation;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
	@Query("select l from Livre  l " +
		      "where lower(l.titreLivre) like lower(concat('%', :searchTerm, '%')) ")
	List<Reservation> search(@Param("searchTerm") String searchTerm);

}
