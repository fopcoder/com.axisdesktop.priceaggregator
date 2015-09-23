package com.axisdesktop.priceaggregator.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.axisdesktop.priceaggregator.entity.CatalogCategory;
import com.axisdesktop.priceaggregator.exception.NoSuchEntityException;
import com.axisdesktop.priceaggregator.service.CatalogCategoryService;
import com.axisdesktop.priceaggregator.service.CatalogCategoryStatusService;

@RestController
@RequestMapping( "/admin/category" )
public class CatalogCategoryController {

	@Autowired
	CatalogCategoryService ccService;

	@Autowired
	CatalogCategoryStatusService ccStatusService;

	@RequestMapping( value = { "", "/list" }, method = RequestMethod.GET )
	public Map<String, Object> index() {
		Map<String, Object> res = new HashMap<>();

		try {
			res.put( "categories", ccService.listAsTreeWithLevel() );
			res.put( "success", true );
		}
		catch( Exception e ) {
			res.put( "success", false );
			res.put( "message", e.getMessage() );
		}

		return res;
	}

	@RequestMapping( value = "/{id}", method = RequestMethod.GET )
	public Map<String, Object> view( @PathVariable int id ) {
		Map<String, Object> res = new HashMap<>();

		try {
			CatalogCategory cat = ccService.getById( id );
			if( cat == null ) throw new NoSuchEntityException( "Category id = " + id + " does not exists" );

			CatalogCategory parent = ccService.getParentCategory( cat );
			if( parent == null ) throw new NoSuchEntityException( "Category parent for id = " + cat.getId()
					+ " does not exists" );

			res.put( "statuses", ccStatusService.list() );
			res.put( "category", cat );
			res.put( "parent", parent );
			res.put( "success", true );
		}
		catch( NoSuchEntityException e ) {
			res.put( "success", false );
			res.put( "message", e.getMessage() );
		}
		catch( Exception e ) {
			res.put( "success", false );
			res.put( "message", e.getMessage() );

			System.err.println( "===> unknown exception" );
			e.printStackTrace();
		}

		return res;
	}

	@RequestMapping( value = "/update", method = RequestMethod.POST )
	public Map<String, Object> update( @RequestBody CatalogCategory category ) {
		Map<String, Object> res = new HashMap<>();

		try {
			CatalogCategory old = ccService.getById( category.getId() );
			if( old == null ) throw new NoSuchEntityException( "Category id = " + category.getId() + " does not exists" );

			BeanUtils.copyProperties( category, old, new String[] { "parentId", "idxLeft", "idxRight" } );

			ccService.update( old );

			old = ccService.getById( category.getId() );

			res.put( "category", old );
			res.put( "success", true );
		}
		catch( NoSuchEntityException e ) {
			res.put( "success", false );
			res.put( "message", e.getMessage() );
		}
		catch( Exception e ) {
			res.put( "success", false );
			res.put( "message", e.getMessage() );

			System.err.println( "===> unknown exception" );
			e.printStackTrace();
		}

		return res;
	}

	@RequestMapping( value = "/new/{parentId}", method = RequestMethod.GET )
	public Map<String, Object> addNew( @PathVariable int parentId ) {
		Map<String, Object> res = new HashMap<>();

		try {
			CatalogCategory parent = ccService.getById( parentId );
			if( parent == null ) throw new NoSuchEntityException( "Category id = " + parentId + " does not exists" );

			res.put( "statuses", ccStatusService.list() );
			res.put( "category", new CatalogCategory() );
			res.put( "parent", parent );
			res.put( "success", true );
		}
		catch( NoSuchEntityException e ) {
			res.put( "success", false );
			res.put( "message", e.getMessage() );
		}
		catch( Exception e ) {
			res.put( "success", false );
			res.put( "message", e.getMessage() );

			System.err.println( "===> unknown exception" );
			e.printStackTrace();
		}

		return res;
	}

	@RequestMapping( value = "/create", method = RequestMethod.POST )
	public Map<String, Object> createCategory( @RequestBody CatalogCategory category ) {
		Map<String, Object> res = new HashMap<>();

		try {
			CatalogCategory newCat = ccService.prependCategory( category );

			res.put( "category", newCat );
			res.put( "success", true );
		}
		catch( Exception e ) {
			res.put( "success", false );
			res.put( "message", e.getMessage() );

			System.err.println( "===> unknown exception" );
			e.printStackTrace();
		}

		return res;
	}

	@RequestMapping( value = "/delete/{id}" )
	public Map<String, Object> deleteCategory( @PathVariable int id ) {
		Map<String, Object> res = new HashMap<>();

		try {
			ccService.delete( id );
			res.put( "success", true );
		}
		catch( Exception e ) {
			res.put( "success", false );
			res.put( "message", e.getMessage() );

			System.err.println( "===> unknown exception" );
			e.printStackTrace();
		}

		return res;
	}

}
