package com.axisdesktop.priceaggregator.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.axisdesktop.priceaggregator.entity.CatalogCategory;
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

	@RequestMapping( value = { "/{id}", "/create" }, method = RequestMethod.GET )
	public CatalogCategory view( @PathVariable int id ) {
		return catalogCategoryService.getById( id );
	}
}
