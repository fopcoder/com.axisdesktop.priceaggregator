package com.axisdesktop.priceaggregator.service;

import java.util.List;

import com.axisdesktop.priceaggregator.exception.NoSuchEntityException;

public interface BasicService<T> {
	List<T> list();

	T create( T provider );

	T getById( int id ) throws NoSuchEntityException;

	T update( T provider ) throws NoSuchEntityException;

	T delete( int id ) throws NoSuchEntityException;
}
