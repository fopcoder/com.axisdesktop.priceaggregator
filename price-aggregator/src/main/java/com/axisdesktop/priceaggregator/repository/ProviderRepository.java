package com.axisdesktop.priceaggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axisdesktop.priceaggregator.entity.Provider;

public interface ProviderRepository extends JpaRepository<Provider, Integer> {

}
