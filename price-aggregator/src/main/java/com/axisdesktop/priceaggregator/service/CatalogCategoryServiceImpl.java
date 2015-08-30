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
	@Override
	// @Transactional
	public List<CatalogCategory> findAll() {
		return catalogCategoryRepository.findAll();
	}
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
