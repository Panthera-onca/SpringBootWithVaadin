package com.vaadin.tutorial.crm.UI.views.data;


import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.tutorial.crm.backend.entity.Livre;
import com.vaadin.tutorial.crm.backend.repository.LivreRepository;
import com.vaadin.tutorial.crm.backend.service.LivreService;


/**
 * Utility class that encapsulates filtering and CRUD operations for
 * {@link Product} entities.
 * <p>
 * Used to simplify the code in {@link SampleCrudView} and
 * {@link SampleCrudLogic}.
 */
public class ProductDataProvider extends ListDataProvider<Livre> {

    

	/** Text filter that can be changed separately. */
    private String filterText = "";
    private LivreService livreService;
    private LivreRepository livreRepository;

    public ProductDataProvider(Collection<Livre> items) {
		super(items);
	}

    /**
     * Store given product to the backing data service.
     *
     * @param product
     *            the updated or new product
     */
    public void save(Livre livre) {
        final boolean newLivre = livre.isNewProduct();

        livreService.updateLivre(livre, null);
        if (newLivre) {
            refreshAll();
        } else {
            refreshItem(livre);
        }
    }

    /**
     * Delete given product from the backing data service.
     *
     * @param product
     *            the product to be deleted
     */
    public void delete(Livre product) {
    	livreService.delete(product);
        refreshAll();
    }

    /**
     * Sets the filter to use for this data provider and refreshes data.
     * <p>
     * Filter is compared for product name, availability and category.
     *
     * @param filterText
     *            the text to filter by, never null
     */
    public void setFilter(String filterText) {
        Objects.requireNonNull(filterText, "Filter text cannot be null.");
        if (Objects.equals(this.filterText, filterText.trim())) {
            return;
        }
        this.filterText = filterText.trim().toLowerCase(Locale.ENGLISH);

        setFilter(product -> passesFilter(product.getTitreLivre(), this.filterText)
                || passesFilter(product.getCategorie(), this.filterText));
    }

    @Override
    public Long getId(Livre product) {
        Objects.requireNonNull(product,
                "Cannot provide an id for a null product.");

        return product.getId();
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText);
    }
}
