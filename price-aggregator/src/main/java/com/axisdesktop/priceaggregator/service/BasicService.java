package com.axisdesktop.priceaggregator.service;

import java.util.List;

public interface BasicService<T> {
	List<T> list();

	T create( T provider );

	T getById( int id );

	T update( T provider );

	T delete( int id );
}
