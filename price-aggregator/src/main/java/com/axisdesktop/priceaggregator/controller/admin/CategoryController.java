package com.axisdesktop.priceaggregator.controller.admin;

import java.util.List;

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

@RestController
@RequestMapping( "/admin/category" )
public class CategoryController {

	@Autowired
	CatalogCategoryService catalogCategoryService;

	@RequestMapping( value = { "", "/list" }, method = RequestMethod.GET )
	public List<CatalogCategory> index() {
		return catalogCategoryService.list();
	}

	@RequestMapping( value = "/{id}", method = RequestMethod.GET )
	public CatalogCategory view( @PathVariable int id ) {
		CatalogCategory cat = null;

		try {
			cat = catalogCategoryService.getById( id );
		}
		catch( NoSuchEntityException e ) {
			System.err.println( "====> no such category " + id );
		}

		return cat;
	}

	@RequestMapping( value = "/update", method = RequestMethod.POST )
	public CatalogCategory update( @RequestBody CatalogCategory category ) {
		CatalogCategory cat = null;

		System.out.println( "=============>" + category );

		try {
			catalogCategoryService.update( category );
		}
		catch( NoSuchEntityException e1 ) {
			// TODO Auto-generated catch block
		}

		try {
			cat = catalogCategoryService.getById( category.getId() );
		}
		catch( NoSuchEntityException e ) {
			// TODO Auto-generated catch block
		}

		return cat;
	}

	@RequestMapping( value = "/category/create", method = RequestMethod.POST )
	public CatalogCategory createCategory(
			@ModelAttribute CatalogCategory category ) {
		CatalogCategory newCat = catalogCategoryService.create( category );

		return newCat;
	}

	@RequestMapping( value = "/category/delete/{categoryId}" )
	public String deleteCategory( @PathVariable int categoryId ) {
		try {
			catalogCategoryService.delete( categoryId );
		}
		catch( NoSuchEntityException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/admin";
	}

}
