package com.vaadin.tutorial.crm.UI.views.dashboard;


import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.tutorial.crm.UI.views.list.MainLayout;
import com.vaadin.tutorial.crm.backend.service.LivreService;
import com.vaadin.tutorial.crm.backend.service.ReservationService;


@PageTitle("Dashboard | ENI Ecole Informatique")
@Route(value = "dashboard", layout = MainLayout.class)
public class DashboardView extends VerticalLayout{
	private LivreService livreService;
	private ReservationService reservationService;
	
	public DashboardView(LivreService livreService, ReservationService reservationRepository) { 
        this.livreService = livreService;
        this.reservationService = reservationService;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER); 
    }
	
	private Component getLivresStats() {
	    Span stats = new Span(livreService.count() + " livres"); 
	    stats.addClassName("livre-stats");
	    return stats;
	}
	
	

}
