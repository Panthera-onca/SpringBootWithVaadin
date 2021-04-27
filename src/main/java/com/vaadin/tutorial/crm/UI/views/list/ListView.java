package com.vaadin.tutorial.crm.UI.views.list;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import com.vaadin.tutorial.crm.backend.entity.Livre;
import com.vaadin.tutorial.crm.backend.entity.Reservation;
import com.vaadin.tutorial.crm.backend.service.LivreService;
import com.vaadin.tutorial.crm.backend.service.ReservationService;
import com.vaadin.tutorial.crm.UI.views.list.LivreForm;
import com.vaadin.tutorial.crm.UI.views.list.MainLayout;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Component
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@PageTitle("Livres | ENI Ecole Informatique")
public class ListView extends VerticalLayout {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7299743185381390577L;
	LivreService livreService;
	Grid<Livre> grid = new Grid<>(Livre.class);
	TextField filterText = new TextField();
	LivreForm form1;
	
	
  
    
    public ListView(LivreService livreService, ReservationService reservationService) {
    	    this.livreService = livreService;
            addClassName("list-view"); 
            setSizeFull(); 
            configureGrid(); 

            
            
            form1 = new LivreForm(reservationService.findAll());
            form1.addListener(LivreForm.SaveEvent.class, this::saveLivre); 
            form1.addListener(LivreForm.DeleteEvent.class, this::deleteLivre); 
            form1.addListener(LivreForm.CloseEvent.class, e -> closeEditor());

            Div content = new Div(grid, form1); 
            content.addClassName("content");
            content.setSizeFull();
            add(getToolbar(), content); 
            updateList();
            closeEditor();
            }
   
        private void configureGrid() {
            grid.addClassName("livre-grid");
            grid.setSizeFull();
            grid.setColumns("titreLivre", "description", "auteur", "refeni", "isbn", "categorie", "disponibilite", "campus"); 
            grid.addColumn(livre -> { 
                Reservation reservation = livre.getReservation();
                return reservation == null ? "-" : reservation.getCreatedAt();
            }).setHeader("Reservation");
            
            grid.getColumns().forEach(col -> col.setAutoWidth(true));
            grid.asSingleSelect().addValueChangeListener(event -> 
            editLivre(event.getValue()));

        }
        
        public void editLivre(Livre livre) { 
            if (livre == null) {
                closeEditor();
            } else {
                form1.setLivre(livre);
                form1.setVisible(true);
                addClassName("editing");
            }
        }
        
        private void saveLivre(LivreForm.SaveEvent event) {
            livreService.save(event.getLivre());
            updateList();
            closeEditor();
        }

        private void deleteLivre(LivreForm.DeleteEvent event) {
            livreService.delete(event.getLivre());
            updateList();
            closeEditor();
        }
        
        private void closeEditor() {
            form1.setLivre(null);
            form1.setVisible(false);
            removeClassName("editing");
        }
        
        private HorizontalLayout getToolbar() {
            filterText.setPlaceholder("Filtrer par titre ou auteur..."); 
            filterText.setClearButtonVisible(true); 
            filterText.setValueChangeMode(ValueChangeMode.LAZY); 
            filterText.addValueChangeListener(e -> updateList()); 
            
            Button addLivreButton = new Button("Ajouter livre");
            addLivreButton.addClickListener(click -> addLivre()); 

            HorizontalLayout toolbar = new HorizontalLayout(filterText, addLivreButton); 
            toolbar.addClassName("toolbar");
            return toolbar;
        }
        
        void addLivre() {
            grid.asSingleSelect().clear(); 
            editLivre(new Livre()); 
        }
        
        private void updateList() {
            grid.setItems(livreService.findAll(filterText.getValue()));
        }
        
        

   
        

}
