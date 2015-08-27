package com.axisdesktop.priceaggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axisdesktop.priceaggregator.entity.CatalogCategory;

public interface CatalogCategoryRepository extends JpaRepository<CatalogCategory, Integer> {

}
