package com.axisdesktop.priceaggregator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.axisdesktop.priceaggregator.entity.CatalogCategory;
import com.axisdesktop.priceaggregator.entity.Provider;
import com.axisdesktop.priceaggregator.service.CatalogCategoryService;
import com.axisdesktop.priceaggregator.service.ProviderService;

@Controller
@RequestMapping( "/admin" )
public class AdminController {
	@Autowired
	ProviderService providerService;

	@Autowired
	CatalogCategoryService catalogCategoryService;

	@RequestMapping
	public String index( Model model ) {

		model.addAttribute( "providers", providerService.list() );
		model.addAttribute( "categories", catalogCategoryService.list() );

		return "admin/index";
	}

	// providers

	@RequestMapping( value = "/provider/create", method = RequestMethod.POST )
	public String createProvider( @ModelAttribute Provider provider ) {
		providerService.create( provider );

		return "redirect:/admin";
	}

	@RequestMapping( value = "/provider/update/{providerId}", method = RequestMethod.GET )
	public String editProvider( @PathVariable int providerId, Model model ) {
		Provider provider = providerService.read( providerId );
		model.addAttribute( "provider", provider );

		return "admin/provider/edit";
	}

	@RequestMapping( value = "/provider/update", method = RequestMethod.POST )
	public String updateProvider( @ModelAttribute Provider provider ) {
		providerService.update( provider );

		return "redirect:/admin";
	}

	@RequestMapping( value = "/provider/delete/{providerId}" )
	public String deleteProvider( @PathVariable int providerId, Model model ) {
		providerService.delete( providerId );

		return "redirect:/admin";
	}

	// categories

	@RequestMapping( value = "/category/create", method = RequestMethod.POST )
	public String createCategory( @ModelAttribute CatalogCategory category ) {
		catalogCategoryService.create( category );

		return "redirect:/admin";
	}

	@RequestMapping( value = "/category/update/{categoryId}", method = RequestMethod.GET )
	public String editCategory( @PathVariable int categoryId, Model model ) {
		CatalogCategory category = catalogCategoryService.read( categoryId );
		model.addAttribute( "category", category );

		return "admin/category/edit";
	}

	@RequestMapping( value = "/category/update", method = RequestMethod.POST )
	public String updateCategory( @ModelAttribute CatalogCategory category ) {
		catalogCategoryService.update( category );

		return "redirect:/admin";
	}

	@RequestMapping( value = "/category/delete/{categoryId}" )
	public String deleteCategory( @PathVariable int categoryId ) {
		catalogCategoryService.delete( categoryId );

		return "redirect:/admin";
	}

}