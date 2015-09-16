package com.axisdesktop.priceaggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axisdesktop.priceaggregator.entity.CatalogCategoryStatus;

public interface CatalogCategoryStatusRepository extends JpaRepository<CatalogCategoryStatus, Integer> {

}
