package com.axisdesktop.priceaggregator.service;

import java.util.List;

import com.axisdesktop.priceaggregator.entity.CatalogCategory;

public interface CatalogCategoryService extends BasicService<CatalogCategory> {
	List<CatalogCategory> megamenu();
}
