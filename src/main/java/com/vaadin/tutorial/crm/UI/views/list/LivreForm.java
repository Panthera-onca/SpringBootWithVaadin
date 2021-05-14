package com.vaadin.tutorial.crm.UI.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.select.Select;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.shared.Registration;
import com.vaadin.tutorial.crm.backend.entity.Livre;
import com.vaadin.tutorial.crm.backend.entity.Livre.*;
import com.vaadin.tutorial.crm.backend.entity.Reservation;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;
import java.util.Set;

public class LivreForm extends FormLayout{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4235188788593264058L;
	private Livre livre;
    private final TextField stockCount;
	TextField titreLivre = new TextField("titreLivre");
	TextField description = new TextField("description");
	TextField auteur = new TextField("auteur");
	TextField refeni = new TextField("refeni");
	TextField isbn = new TextField("isbn");
	
    
	ComboBox<Livre.Categorie> categorie = new ComboBox<>("Categorie");
	ComboBox<Reservation> reservation = new ComboBox<Reservation>();
	ComboBox<Livre.Campus> campus = new ComboBox<>("Campus");
	ComboBox<Livre.Disponibilite> disponibilite = new ComboBox<>("Disponibilite");
	Button save = new Button("Save"); 
	Button delete = new Button("Delete");
	Button close = new Button("Cancel");
	Binder<Livre> binder = new BeanValidationBinder<>(Livre.class);

	
	public LivreForm(List<Reservation> reservations) {
		stockCount = new TextField("In stock");
	    stockCount.setValueChangeMode(ValueChangeMode.EAGER);
	    
		addClassName("livre-form");
		binder.forField(stockCount).withConverter(new StockCountConverter())
              .bind("stockCount");
	    binder.bindInstanceFields(this);
	    reservation.setItems(reservations);
	    categorie.setItems(Livre.Categorie.values());
	    campus.setItems(Livre.Campus.values());;
	    disponibilite.setItems(Livre.Disponibilite.values());
	    add(titreLivre,
	    	description,
	    	auteur,
	        refeni,
	        isbn,
	        disponibilite,
	        categorie,
	        campus,
	        stockCount,
	        reservation,
	        createButtonsLayout()); 
	  }
	
	public void setLivre(Livre livre) {
        this.livre = livre; 
        binder.readBean(livre); 
    }
	
	
	
	
	private Component createButtonsLayout() {
	    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY); 
	    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
	    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

	    save.addClickShortcut(Key.ENTER); 
	    close.addClickShortcut(Key.ESCAPE);
	    
	    save.addClickListener(event -> validateAndSave()); 
	    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, livre))); 
	    close.addClickListener(event -> fireEvent(new CloseEvent(this))); 
	    
	    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

	    return new HorizontalLayout(save, delete, close); 
	  }
	
	private void validateAndSave() {
		  try {
		    binder.writeBean(livre); 
		    fireEvent(new SaveEvent(this, livre)); 
		  } catch (ValidationException e) {
		    e.printStackTrace();
		  }
		}
	
	public static abstract class LivreFormEvent extends ComponentEvent<LivreForm> {
		  

		/**
		 * 
		 */
		private static final long serialVersionUID = -7236023661050023675L;
		private Livre livre;

		  protected LivreFormEvent(LivreForm source,Livre livre) { 
		    super(source, false);
		    this.livre = livre;
		  }

		  public Livre getLivre() {
		    return livre;
		  }
		}

		public static class SaveEvent extends LivreFormEvent {
		  SaveEvent(LivreForm source, Livre livre) {
		    super(source, livre);
		  }
		}

		public static class DeleteEvent extends LivreFormEvent {
		  DeleteEvent(LivreForm source, Livre livre) {
		    super(source, livre);
		  }

		}

		public static class CloseEvent extends LivreFormEvent {
		  CloseEvent(LivreForm source) {
		    super(source, null);
		  }
		}

		public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
		    ComponentEventListener<T> listener) { 
		  return getEventBus().addListener(eventType, listener);
		}
		
		@SuppressWarnings("serial")
		private static class StockCountConverter extends StringToIntegerConverter {

	        public StockCountConverter() {
	            super(0, "Could not convert value to " + Integer.class.getName()
	                    + ".");
	        }
		
	

}
}
