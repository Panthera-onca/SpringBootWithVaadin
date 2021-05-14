package com.vaadin.tutorial.crm.backend.service;


import com.vaadin.tutorial.crm.backend.entity.Livre;
import com.vaadin.tutorial.crm.backend.entity.Reservation;
import com.vaadin.tutorial.crm.backend.repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ReservationService {
	private static final Logger LOGGER = Logger.getLogger(ReservationService.class.getName());
	@Autowired
	private ReservationRepository reservationRepository;
	
	public ReservationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}
	
	public List<Reservation> findAll(){
		return reservationRepository.findAll();
		
	}
	
	public List<Reservation> findAll(String stringFilter) { 
		if (stringFilter == null || stringFilter.isEmpty()) { 
			return reservationRepository.findAll();
		} else {
			return reservationRepository.search(stringFilter);}}
	public long count() {
		return reservationRepository.count();
	}

	
	public void save(Reservation reservation) {
		if (reservation == null) { 
			LOGGER.log(Level.SEVERE,
					"Reservation is null. Are you sure you have connected your form to the application?");
			return;
		}
		reservationRepository.save(reservation);
	}

	public void delete(Reservation reservation) {
		reservationRepository.delete(reservation);
	}

}
