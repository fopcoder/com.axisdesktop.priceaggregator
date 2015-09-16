package com.axisdesktop.priceaggregator.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axisdesktop.priceaggregator.entity.CatalogCategory;
import com.axisdesktop.priceaggregator.exception.NoSuchEntityException;
import com.axisdesktop.priceaggregator.repository.CatalogCategoryRepository;
import com.axisdesktop.priceaggregator.repository.CatalogCategoryStatusRepository;

@Service
public class CatalogCategoryServiceImpl implements CatalogCategoryService {

	@Autowired
	private CatalogCategoryRepository catalogCategoryRepository;
	@Autowired
	private CatalogCategoryStatusRepository catalogCategoryStatusRepository;
	@Autowired
	private EntityManagerFactory emf;

	@Override
	public List<CatalogCategory> list() {

		// EntityManager em = emf.createEntityManager();
		// TypedQuery<CatalogCategory> q = em.createNamedQuery(
		// "CatalogCategory.menu", CatalogCategory.class );
		// List<CatalogCategory> menu = q.getResultList();
		//
		// System.out.println( menu );

		// CriteriaBuilder cb = em.c

		// TypedQuery<Object[]> q = em
		// .createQuery(
		// "SELECT c, ("
		// + " SELECT c2.id FROM CatalogCategory c2 "
		// + " WHERE c2.idx_left < c.idx_left AND c2.idx_right > c.idx_right"
		// + ") AS y FROM CatalogCategory c",
		// Object[].class );
		// List<Object[]> list = q.getResultList();

		// for( Object[] o : list ) {
		// System.out.println( o[0] );
		// System.out.println( o[1] );
		// }

		// em.close();

		return catalogCategoryRepository.findAll();

	}

	@Override
	public CatalogCategory getById( int id ) throws NoSuchEntityException {
		CatalogCategory cat = catalogCategoryRepository.findOne( id );

		if( cat == null ) {
			throw new NoSuchEntityException();
		}

		return cat;
	}

	@Override
	@Transactional
	public CatalogCategory create( CatalogCategory category ) {
		return catalogCategoryRepository.save( category );
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

	@Override
	public List<CatalogCategory> megamenu() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<CatalogCategory> q = em.createNamedQuery( "CatalogCategory.megamenu", CatalogCategory.class );
		List<CatalogCategory> megamenu = q.getResultList();
		em.close();

		for( CatalogCategory cc : megamenu ) {
			System.out.println( cc.getName() + "======> " + cc.getChildren().size() );
		}

		return megamenu;
	}

}
