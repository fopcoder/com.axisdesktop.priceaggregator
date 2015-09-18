package com.axisdesktop.priceaggregator.service;

import java.util.List;

import com.axisdesktop.priceaggregator.entity.CatalogCategory;

public interface CatalogCategoryService extends BasicService<CatalogCategory> {
	List<CatalogCategory> megamenu();

	List<CatalogCategory> listAsTree();

	List<CatalogCategory> listAsTreeWithLevel();

	CatalogCategory prependCategory( CatalogCategory category );

	CatalogCategory getParentCategory( CatalogCategory category );

	void composeUriPath( CatalogCategory category, CatalogCategory parent );

}
