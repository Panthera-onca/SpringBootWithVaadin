package com.vaadin.tutorial.crm.backend.service;

import com.vaadin.tutorial.crm.backend.entity.Disponibilite;
import com.vaadin.tutorial.crm.backend.entity.Reservation;
import com.vaadin.tutorial.crm.backend.repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationService {
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	public ReservationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}
	
	public List<Reservation> findAll(){
		return reservationRepository.findAll();
		
	}
	
	public Collection<String> getDisponibilite() {
        return Arrays.asList("Disponible", "Indisponible", "Emprunté");
    }
	
	public void register(Reservation reservation) {
		reservationRepository.save(reservation);
    }

}
