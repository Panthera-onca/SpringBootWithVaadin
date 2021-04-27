package com.vaadin.tutorial.crm.backend.service;


import com.vaadin.tutorial.crm.backend.entity.Livre;
import com.vaadin.tutorial.crm.backend.entity.Reservation;
import com.vaadin.tutorial.crm.backend.repository.LivreRepository;
import com.vaadin.tutorial.crm.backend.repository.ReservationRepository;

import org.springframework.stereotype.Service;

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

