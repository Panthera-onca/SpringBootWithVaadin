package com.vaadin.tutorial.crm.UI.views.list;

import org.springframework.context.annotation.Scope;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.tutorial.crm.backend.entity.Livre;
import com.vaadin.tutorial.crm.backend.entity.Reservation;
import com.vaadin.tutorial.crm.backend.repository.ReservationRepository;
import com.vaadin.tutorial.crm.backend.service.LivreService;
import com.vaadin.tutorial.crm.backend.service.ReservationService;


@Component
@Scope("prototype")
@Route(value = "reservation", layout = MainLayout.class)
@Secured("ROLE_User")
@PageTitle("Reservations | ENI Ecole Informatique")
public class ReservationView extends VerticalLayout{
	
	private ReservationForm form;
	private Reservation reservation;
	private LivreService livreService;
	private final Grid<Reservation> grid = new Grid<>(Reservation.class);
	TextField filterText = new TextField();
	private ReservationService reservationService;
	
	public  ReservationView(ReservationService reservationService) {
        this.reservationService = reservationService;
        // Build the layout
        addClassName("reservation-view");
        setSizeFull();
        configureGrid();


        form = new ReservationForm(livreService.findAll());
        form.addListener(ReservationForm.SaveEvent.class, this::saveReservation);
        form.addListener(ReservationForm.DeleteEvent.class, this::deleteReservation);
        form.addListener(ReservationForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        closeEditor();
        
    }
	
	private void deleteReservation(ReservationForm.DeleteEvent evt) {
		reservationService.delete(evt.getReservation());
        updateList();
        closeEditor();
    }

    private void saveReservation(ReservationForm.SaveEvent evt) {
    	reservationService.save(evt.getReservation());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addReservationButton = new Button("Add contact", click -> addReservation());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addReservationButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addReservation() {
        grid.asSingleSelect().clear();
        editReservation(new Reservation());
    }

    private void configureGrid() {
        grid.addClassName("reservation-grid");
        grid.setSizeFull();
        grid.setColumns("createdAt", "adress", "disponibilite");
        grid.addColumn(reservation -> {
           Livre livre = reservation.getLivre();
           return livre == null ? "-" : livre.getTitreLivre();
        }).setHeader("Company");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editReservation(evt.getValue()));
    }

    private void editReservation(Reservation reservation) {
        if (reservation == null) {
            closeEditor();
        } else {
            form.setReservation(reservation);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setReservation(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(reservationService.findAll(filterText.getValue()));
    }
	
	
}
