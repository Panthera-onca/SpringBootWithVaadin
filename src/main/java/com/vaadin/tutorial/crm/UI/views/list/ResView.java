package com.vaadin.tutorial.crm.UI.views.list;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Scope;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.tutorial.crm.backend.entity.Disponibilite;
import com.vaadin.tutorial.crm.backend.entity.Reservation;
import com.vaadin.tutorial.crm.backend.service.ReservationService;
import com.vaadin.ui.DateField;

import elemental.json.JsonArray;


@Component
@Scope("prototype")
@Route(value = "/resview", layout = MainLayout.class)
@Secured("ROLE_User")
@PageTitle("Reservations | ENI Ecole Informatique")
public class ResView extends VerticalLayout{
	 private transient ReservationService service;
	 private Disponibilite disponibilite1;

	 private Binder<Reservation> binder = new BeanValidationBinder<>(Reservation.class);
	 
	 DateField createdAt = new DateField("createdAt");
	 private ComboBox<String> disponibilite2 = new ComboBox<String>("Disponibilité");
	 
	 public ResView(ReservationService service) {
		 this.service = service;
		 
		 H1 heading = new H1("Sousmettre une reservation");
	        Button submit = new Button("Submit");
	        setDefaultHorizontalComponentAlignment(FlexLayout.Alignment.CENTER);
	        RouterLink listOrders = new RouterLink("List des reservations", ReservationView.class);
	        add(
	                heading,
	                disponibilite2,
	                submit,
	                listOrders
	        );
	        disponibilite2.setItems(service.getDisponibilite());
	        
	        submit.addClickListener(e -> {
	            submitOrder();
	            String msg = String.format(
	                    "Merci %s, votre reservation était soumis!",
	                    binder.getBean().getCreatedAt());
	            Notification.show(msg, 3000, Notification.Position.MIDDLE);
	            init();
	        });
	 }
	 
	 private void submitOrder() {
	        service.register(binder.getBean());
	    }

	    private void init() {
	        binder.setBean(new Reservation(null, null, null, disponibilite1));
	    }
	 
	 
	 
	 
	 

}
