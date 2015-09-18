package com.axisdesktop.priceaggregator.service;

import java.util.List;

public interface BasicService<T> {
	List<T> list();

	T create( T entity );

	T getById( int id );

	T update( T entity );

	T delete( int id );
}
