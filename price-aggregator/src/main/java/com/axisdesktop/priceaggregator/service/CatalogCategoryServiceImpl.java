package com.axisdesktop.priceaggregator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axisdesktop.priceaggregator.entity.CatalogCategory;
import com.axisdesktop.priceaggregator.repository.CatalogCategoryRepository;
import com.axisdesktop.priceaggregator.repository.CatalogCategoryStatusRepository;

@Service
public class CatalogCategoryServiceImpl implements CatalogCategoryService {

	@Autowired
	private CatalogCategoryRepository ссRepository;

	@Autowired
	private CatalogCategoryStatusRepository cсStatusRepository;

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<CatalogCategory> list() {
		return ссRepository.findAll();
	}

	@Override
	public List<CatalogCategory> listAsTree() {
		return em.createNamedQuery( "CatalogCategory.listAsTree", CatalogCategory.class ).getResultList();
	}

	@Override
	public List<CatalogCategory> listAsTreeWithLevel() {
		List<Object[]> tmp = em.createNamedQuery( "CatalogCategory.listAsTreeWithLevel", Object[].class )
				.getResultList();

		List<CatalogCategory> res = new ArrayList<>();

		for( Object[] obj : tmp ) {
			( (CatalogCategory)obj[0] ).setLevel( (long)obj[1] );
			res.add( ( (CatalogCategory)obj[0] ) );
		}

		return res;
	}

	@Override
	public CatalogCategory getById( int id ) {
		return ссRepository.findOne( id );
	}

	@Override
	public CatalogCategory create( CatalogCategory category ) {
		return this.prependCategory( category );
	}

	@Override
	@Transactional
	public CatalogCategory prependCategory( CatalogCategory category ) {
		CatalogCategory parent = ссRepository.findOne( category.getParentId() );
		CatalogCategory newCat = null;

		if( parent != null ) {
			category.setIdxLeft( parent.getIdxLeft() + 1 );
			category.setIdxRight( parent.getIdxLeft() + 2 );

			this.composeUriPath( category, parent );

			em.createQuery( "UPDATE CatalogCategory SET idxLeft = idxLeft + 2 WHERE idxLeft > ?0" )
					.setParameter( 0, parent.getIdxLeft() ).executeUpdate();
			em.createQuery( "UPDATE CatalogCategory SET idxRight = idxRight + 2 WHERE idxRight > ?0" )
					.setParameter( 0, parent.getIdxLeft() ).executeUpdate();

			newCat = ссRepository.save( category );
		}

		return newCat;
	}

	@Override
	@Transactional
	public CatalogCategory update( CatalogCategory category ) {
		if( ссRepository.exists( category.getId() ) ) {
			this.composeUriPath( category, this.getParentCategory( category ) );
			ссRepository.save( category );

			return category;
		}

		return null;
	}

	@Override
	@Transactional
	public CatalogCategory delete( int id ) {
		CatalogCategory category = ссRepository.findOne( id );

		if( category != null ) {
			ссRepository.delete( category );
		}

		return null;
	}

	@Override
	public List<CatalogCategory> megamenu() {
		List<CatalogCategory> res = em.createNamedQuery( "CatalogCategory.megamenu", CatalogCategory.class )
				.getResultList();
		// List<CatalogCategory> res = query.getResultList();

		CatalogCategory megamenu = this._list2tree( res, res.remove( 0 ) );

		// for( CatalogCategory cc : megamenu ) {
		// System.out.println( cc.getName() + "======> "
		// + cc.getChildren().size() );
		// }

		return megamenu.getChildren();
	}

	@Override
	public CatalogCategory getParentCategory( CatalogCategory category ) {
		CatalogCategory parent = null;

		if( category != null ) {
			TypedQuery<CatalogCategory> query = em
					.createNamedQuery( "CatalogCategory.getParent", CatalogCategory.class );
			parent = query.setParameter( "idxLeft", category.getIdxLeft() )
					.setParameter( "idxRight", category.getIdxRight() ).setMaxResults( 1 ).getSingleResult();
		}

		return parent;
	}

	@Override
	// TODO make recursive change path
	public void composeUriPath( CatalogCategory category, CatalogCategory parent ) {
		if( category == null || parent == null ) throw new IllegalArgumentException(
				"catalog & parent must be not null" );

		if( category.getIdxLeft() == 1 ) {
			category.setPath( "/" );
			category.setUri( "" );
		}
		else if( parent.getUri().length() == 0 ) {
			category.setPath( "" );
		}
		else {
			String uri = category.getUri();

			if( uri.length() > 0 ) {
				uri = uri.replace( "/", "" );
				category.setPath( "/" + category.getUri() );

				if( !parent.getPath().equals( "/" ) ) {
					category.setPath( parent.getPath() + category.getPath() );
				}
			}
			else {
				category.setPath( "" );
			}
		}
	}

	private CatalogCategory _list2tree( List<CatalogCategory> list, CatalogCategory cat ) {
		CatalogCategory catNew = new CatalogCategory( cat );

		for( ListIterator<CatalogCategory> it = list.listIterator(); it.hasNext(); ) {
			CatalogCategory cc = it.next();

			if( cc == null || cc.getIdxLeft() < catNew.getIdxLeft() || cc.getIdxRight() > catNew.getIdxRight() ) continue;

			it.set( null );

			if( cc.getIdxRight() - cc.getIdxLeft() == 1 ) {
				catNew.getChildren().add( new CatalogCategory( cc ) );
			}
			else {
				catNew.getChildren().add( this._list2tree( list, cc ) );
			}
		}

		return catNew;
	}
}
