package com.axisdesktop.priceaggregator.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axisdesktop.priceaggregator.entity.CatalogCategory;
import com.axisdesktop.priceaggregator.repository.CatalogCategoryRepository;

@Service
public class CatalogCategoryServiceImpl implements CatalogCategoryService {

	@Autowired
	private CatalogCategoryRepository catalogCategoryRepository;

	@Override
	public List<CatalogCategory> list() {
		return catalogCategoryRepository.findAll();
	}

	@Override
	@Transactional
	public CatalogCategory create( CatalogCategory category ) {
		return catalogCategoryRepository.save( category );
	}

	@Override
	public CatalogCategory getById( int id ) {
		return catalogCategoryRepository.findOne( id );
	}

	@Override
	@Transactional
	public CatalogCategory update( CatalogCategory category ) {
		if( catalogCategoryRepository.exists( category.getId() ) ) {
			return catalogCategoryRepository.save( category );
		}

		return null;
	}

	@Override
	@Transactional
	public CatalogCategory delete( int id ) {
		CatalogCategory category = catalogCategoryRepository.findOne( id );

		if( category != null ) {
			catalogCategoryRepository.delete( category );
		}

		return null;
	}

}
