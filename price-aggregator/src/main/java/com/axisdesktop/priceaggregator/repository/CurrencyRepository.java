package com.axisdesktop.priceaggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axisdesktop.priceaggregator.entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

}
