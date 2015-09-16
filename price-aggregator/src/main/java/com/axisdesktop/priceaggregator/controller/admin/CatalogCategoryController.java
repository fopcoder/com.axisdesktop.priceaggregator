package com.axisdesktop.priceaggregator.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public List<CatalogCategory> index() {
		return ccService.list();
	}

	@RequestMapping( value = "/{id}", method = RequestMethod.GET )
	public Map<String, Object> view( @PathVariable int id ) {
		Map<String, Object> res = new HashMap<>();

		try {
			res.put( "statuses", ccStatusService.list() );
			res.put( "category", ccService.getById( id ) );
		}
		catch( NoSuchEntityException e ) {
			System.err.println( "===> no such category " + id );
			res = null;
		}
		catch( Exception e ) {
			System.err.println( "===> unknown exception" );
			e.printStackTrace();
			res = null;
		}

		return res;
	}

	@RequestMapping( value = "/update", method = RequestMethod.POST )
	public CatalogCategory update( @RequestBody CatalogCategory category ) {
		CatalogCategory old = null;

		try {
			old = ccService.getById( category.getId() );
			BeanUtils.copyProperties( category, old, new String[] { "parentId" } );

			ccService.update( old );
		}
		catch( NoSuchEntityException e2 ) {
			System.err.println( "===> input category does not exist " );
		}
		catch( Exception e ) {
			System.err.println( "===> unknown exception" );
			e.printStackTrace();
		}

		return old;
	}

	@RequestMapping( value = "/category/create", method = RequestMethod.POST )
	public CatalogCategory createCategory( @ModelAttribute CatalogCategory category ) {
		CatalogCategory newCat = ccService.create( category );

		return newCat;
	}

	@RequestMapping( value = "/category/delete/{categoryId}" )
	public String deleteCategory( @PathVariable int categoryId ) {
		try {
			ccService.delete( categoryId );
		}
		catch( NoSuchEntityException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/admin";
	}

}
