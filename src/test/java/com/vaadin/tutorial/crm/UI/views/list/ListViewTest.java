package com.vaadin.tutorial.crm.UI.views.list;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.tutorial.crm.backend.entity.Livre;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ListViewTest {
	@Autowired
    private ListView listView;
	
	@Test
    public void formShownWhenContactSelected() {
		Grid<Livre> grid = listView.grid;
        Livre firstLivre = getFirstItem(grid);
        LivreForm form = listView.form1;
        Assert.assertFalse(form.isVisible());
		grid.asSingleSelect().setValue(firstLivre);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstLivre.getTitreLivre(), form.titreLivre.getValue());
    }
	
	@SuppressWarnings("unchecked")
	private Livre getFirstItem(Grid<Livre> grid) {
		return( (ListDataProvider<Livre>) grid.getDataProvider()).getItems().iterator().next();
	}

}
