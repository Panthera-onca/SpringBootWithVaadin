package com.vaadin.tutorial.crm.UI.views.list;

import org.springframework.context.annotation.Scope;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.tutorial.crm.backend.entity.Reservation;
import com.vaadin.tutorial.crm.backend.repository.ReservationRepository;


@Component
@Scope("prototype")
@Route(value = "/reservation", layout = MainLayout.class)
@Secured("ROLE_User")
@PageTitle("Reservations | ENI Ecole Informatique")
public class ReservationView extends VerticalLayout{
	
	private transient ReservationRepository repo;
	private final Grid<Reservation> reservations = new Grid<>(Reservation.class);
	
	public  ReservationView(ReservationRepository repo) {
        this.repo = repo;
        // Build the layout
        H1 heading = new H1("List des reservations");
        Button update = new Button(VaadinIcon.REFRESH.create());
        RouterLink orderView = new RouterLink("Submit new registration", ResView.class);
        add(heading, update, reservations, orderView);
        
        reservations.setColumns("createdAt", "reservations", "disponibilite");
        reservations.addComponentColumn(order -> {
            Button deleteBtn = new Button(VaadinIcon.TRASH.create());
            deleteBtn.addClickListener(e -> {
                repo.delete(order);
                listOrders();
            });
            return deleteBtn;
        });
        listOrders();
        
        update.addClickListener(e -> listOrders());
        
    }
	
	public void listOrders() {
        reservations.setItems(repo.findAll());
    }

}
