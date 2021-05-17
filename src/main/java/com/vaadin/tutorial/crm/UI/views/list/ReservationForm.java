package com.vaadin.tutorial.crm.UI.views.list;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.shared.Registration;
import com.vaadin.tutorial.crm.backend.entity.Livre;
import com.vaadin.tutorial.crm.backend.entity.Reservation;
import com.vaadin.tutorial.crm.backend.service.ReservationService;
import com.vaadin.ui.DateField;

import elemental.json.JsonArray;


@Component
@Scope("prototype")
@Route(value = "resview", layout = MainLayout.class)
@Secured("ROLE_User")
@PageTitle("Reservations | ENI Ecole Informatique")
public class ReservationForm extends FormLayout{
	 private transient ReservationService service;
	 private Reservation reservation;
	 
	 TextField adress = new TextField();
	 DateField createdAt = new DateField("createdAt");
	 ComboBox<Livre.Disponibilite> disponibilite = new ComboBox<>("Disponibilite");
	 Button save = new Button("Save"); 
     Button delete = new Button("Delete");
	 Button close = new Button("Cancel");

	 private Binder<Reservation> binder = new BeanValidationBinder<>(Reservation.class);
	 
	 public ReservationForm(List<Livre> list) {
		 addClassName("contact-form");
         binder.bindInstanceFields(this);
	     add(       
	                adress,
	                createButtonsLayout()
	        );
	    }
	 
	 public void setReservation(Reservation reservation) {
	        this.reservation = reservation;
	        binder.readBean(reservation);
	    }
	 
	 private HorizontalLayout createButtonsLayout() {
	        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
	        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
	        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

	        save.addClickShortcut(Key.ENTER);
	        close.addClickShortcut(Key.ESCAPE);

	        save.addClickListener(click -> validateAndSave());
	        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, reservation)));
	        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

	        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

	        return new HorizontalLayout(save, delete, close);
	    }
	 
	 private void validateAndSave() {

	      try {
	        binder.writeBean(reservation);
	        fireEvent(new SaveEvent(this, reservation));
	      } catch (ValidationException e) {
	        e.printStackTrace();
	      }
	    }
	 
	 public static abstract class ReservationFormEvent extends ComponentEvent<ReservationForm> {
	      private Reservation reservation;

	      protected ReservationFormEvent(ReservationForm source, Reservation reservation) {
	        super(source, false);
	        this.reservation = reservation;
	      }

	      public Reservation getReservation() {
	        return reservation;
	      }
	    }

	    public static class SaveEvent extends ReservationFormEvent {
	      SaveEvent(ReservationForm source, Reservation reservation) {
	        super(source, reservation);
	      }
	    }

	    public static class DeleteEvent extends ReservationFormEvent {
	      DeleteEvent(ReservationForm source, Reservation reservation) {
	        super(source, reservation);
	      }

	    }

	    public static class CloseEvent extends ReservationFormEvent {
	      CloseEvent(ReservationForm source) {
	        super(source, null);
	      }
	    }

	    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
	                                                                  ComponentEventListener<T> listener) {
	      return getEventBus().addListener(eventType, listener);
	    }

	 
	 
	 
	 
	 

}
