package com.vaadin.tutorial.crm.UI.views.list;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.vaadin.tutorial.crm.backend.entity.Reservation;
import com.vaadin.tutorial.crm.backend.repository.ReservationRepository;
import com.vaadin.tutorial.crm.backend.entity.Livre;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class LivreFormTest {
	private List<Reservation> reservations;
	private Livre ProgrammerenMATLAB;
	private Reservation reservation;
	private ReservationRepository reservationRepository;
	
	@Before
    public void setupData() {
		reservations = new ArrayList<>();
		Random r = new Random(0);
		reservations.add(1, reservation);

		ProgrammerenMATLAB = new Livre();
		ProgrammerenMATLAB.setTitreLivre("Programmer en MATLAB");
		ProgrammerenMATLAB.setDescription("un véritable manuel d’apprentissage complet");
		ProgrammerenMATLAB.setAuteur("Mohamed Fadhel SAAD");
		ProgrammerenMATLAB.setRefeni("RIMATLAB");
		ProgrammerenMATLAB.setIsbn("9782409028120");
		ProgrammerenMATLAB.setCategorie(Livre.Categorie.DEVELOPPEMENT);
    }
	
	@Test
    public void formFieldsPopulated() {
        LivreForm form = new LivreForm(reservations);
        form.setLivre(ProgrammerenMATLAB);
        Assert.assertEquals("Programmer en MATLAB", form.titreLivre.getValue());
        Assert.assertEquals("un véritable manuel d’apprentissage complet", form.description.getValue());
        Assert.assertEquals("Mohamed Fadhel SAAD", form.auteur.getValue());
        Assert.assertEquals("RIMATLAB", form.refeni.getValue());
        Assert.assertEquals((int) 9782409028120L, form.isbn.getValue());
        Assert.assertEquals(Livre.Campus.Campus_de_Quimper, form.campus.getValue());
        Assert.assertEquals(Livre.Categorie.DEVELOPPEMENT, form.categorie.getValue());
        
    }
	
	@Test
	public void saveEventHasCorrectValues() {
	    LivreForm form = new LivreForm(reservations);
	    Livre livre = new Livre();
	    form.setLivre(livre);
	    
	    form.titreLivre.setValue("Python, Raspberry Pi et Flask");
	    form.description.setValue("Capturez des données télémétriques et réalisez des tableaux de bord web");
	    form.auteur.setValue("Dominique MEURISSE");
	    form.refeni.setValue("LF2PYRASPFL");
	    form.isbn.setValue("9782409029882L");
	    form.categorie.setValue(Livre.Categorie.DEVELOPPEMENT);
	    
	    AtomicReference<Livre> savedLivreRef = new AtomicReference<>(null);
	    form.addListener(LivreForm.SaveEvent.class, e -> {
	        savedLivreRef.set(e.getLivre()); 
	    });
	    form.save.click();
	    Livre savedLivre = savedLivreRef.get();
	    
	    Assert.assertEquals("Python, Raspberry Pi et Flask", savedLivre.getTitreLivre());
	    Assert.assertEquals("Capturez des données télémétriques et réalisez des tableaux de bord web", savedLivre.getDescription());
	    Assert.assertEquals("Dominique MEURISSE", savedLivre.getAuteur());
	    Assert.assertEquals("LF2PYRASPFL", savedLivre.getRefeni());
	    Assert.assertEquals("9782409029882L", savedLivre.getIsbn());
	    Assert.assertEquals(Livre.Categorie.DEVELOPPEMENT, savedLivre.getCategorie());
	    
	}
}
