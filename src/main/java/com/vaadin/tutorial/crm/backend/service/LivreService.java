package com.vaadin.tutorial.crm.backend.service;


import com.vaadin.tutorial.crm.backend.entity.Livre;
import com.vaadin.tutorial.crm.backend.entity.Reservation;
import com.vaadin.tutorial.crm.backend.repository.LivreRepository;
import com.vaadin.tutorial.crm.backend.repository.ReservationRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;


@Service
public class LivreService {
	private static final Logger LOGGER = Logger.getLogger(LivreService.class.getName());
	private LivreRepository livreRepository;
	private ReservationRepository reservationRepository;
	public LivreService(LivreRepository livreRepository) {
		this.livreRepository = livreRepository;
	}
	
	public List<Livre> findAll() {
		return livreRepository.findAll();
	}
	
	
	public List<Livre> findAll(String stringFilter) { 
		if (stringFilter == null || stringFilter.isEmpty()) { 
			return livreRepository.findAll();
		} else {
			return livreRepository.search(stringFilter); 
		}
	}
	public Optional<Livre> findById() {
		return livreRepository.findById(null);
	}
	public Livre updateLivre(@RequestBody Livre  newLivre, @PathVariable Long id) {
		 
        return livreRepository.findById(id).map(livre -> {
            livre.setTitreLivre(newLivre.getTitreLivre());
            livre.setDescription(newLivre.getDescription());
            livre.setAuteur(newLivre.getAuteur());
            livre.setRefeni(newLivre.getRefeni());
            livre.setIsbn(newLivre.getIsbn());
            livre.setCampus(newLivre.getCampus());
            livre.setDisponibilite(newLivre.isDisponibilite());
            livre.setReservation(newLivre.getReservation());
            livre.setStockCount(newLivre.getStockCount());
            livre.setCategorie(newLivre.getCategorie());
            return livreRepository.save(livre);
        }).orElseGet(() -> {
            newLivre.setId(id);
            return livreRepository.save(newLivre);
        });
    }
	
	 
	public long count() {
		return livreRepository.count();
	}
	
	
	
	public void delete(Livre livre) {
		livreRepository.delete(livre);
	}
	
	public void save(Livre livre) {
		if (livre == null) { 
			LOGGER.log(Level.SEVERE,
					"Livre is null. Are you sure you have connected your form to the application?");
			return;
		}
		livreRepository.save(livre);
	}
	
	
		
	}

