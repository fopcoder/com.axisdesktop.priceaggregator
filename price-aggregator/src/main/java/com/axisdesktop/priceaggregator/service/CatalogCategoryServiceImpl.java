package com.axisdesktop.priceaggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axisdesktop.priceaggregator.repository.CatalogCategoryRepository;

@Service
public class CatalogCategoryServiceImpl implements CatalogCategoryService {

	@Autowired
	private CatalogCategoryRepository catalogCategoryRepository;

	// @Override
	// @Transactional
	// public CatalogCategory create( CatalogCategory category ) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// @Transactional
	// public CatalogCategory delete( int id ) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// @Transactional
	// public List<CatalogCategory> findAll() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// @Transactional
	// public CatalogCategory update( CatalogCategory category ) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// @Transactional
	// public CatalogCategory findById( int id ) {
	// // TODO Auto-generated method stub
	// return null;
	// }

}
