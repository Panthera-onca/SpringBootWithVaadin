package com.vaadin.tutorial.crm.UI.views.list;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.tutorial.crm.UI.views.list.ListView;


@PWA(
	    name = "Vaadin CRM",
	    shortName = "CRM",
	    offlineResources = {
	        "./styles/offline.css",
	        "./images/offline.png"
	    }
	)
	@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3861556402579812524L; 
	public MainLayout() {
		createHeader();
		createDrawer();
	}

	private void createDrawer() {
		H1 logo = new H1("ENI Ecole Informatique");
        logo.addClassName("logo");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo); 

        header.setDefaultVerticalComponentAlignment(
             FlexComponent.Alignment.CENTER); 
        header.setWidth("100%");
        header.addClassName("header");


        addToNavbar(header);
        
        RouterLink listLink = new RouterLink("Livre", ListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
            listLink,
            new RouterLink("Reservations", ReservationForm.class)
        ));
        
        RouterLink mainLink = new RouterLink("Reservation", ReservationView.class);
        mainLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(
        		mainLink,
                new RouterLink("Reservation", ReservationView.class)
            ));
        
        
		
	}

	private void createHeader() {
		RouterLink resLink = new RouterLink("resform", ReservationForm.class); 
        resLink.setHighlightCondition(HighlightConditions.sameLocation()); 

        addToDrawer(new VerticalLayout(resLink)); 
        H1 logo = new H1("ENI Ecole Informatique");
        logo.addClassName("logo");

        Anchor logout = new Anchor("logout", "Log out"); 

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout); 
        header.expand(logo); 
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");

        addToNavbar(header);
		
	}
	
	
	

}
