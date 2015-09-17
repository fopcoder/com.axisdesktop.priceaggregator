package com.axisdesktop.priceaggregator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axisdesktop.priceaggregator.entity.CatalogCategoryStatus;
import com.axisdesktop.priceaggregator.exception.NoSuchEntityException;
import com.axisdesktop.priceaggregator.repository.CatalogCategoryStatusRepository;

@Service
public class CatalogCategoryStatusServiceImpl implements
		CatalogCategoryStatusService {

	@Autowired
	CatalogCategoryStatusRepository ccSrviceRepository;

	@Override
	public List<CatalogCategoryStatus> list() {
		return ccSrviceRepository.findAll();
	}

	@Override
	public CatalogCategoryStatus create( CatalogCategoryStatus provider ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatalogCategoryStatus getById( int id ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatalogCategoryStatus update( CatalogCategoryStatus provider )
			throws NoSuchEntityException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatalogCategoryStatus delete( int id ) throws NoSuchEntityException {
		// TODO Auto-generated method stub
		return null;
	}

}
