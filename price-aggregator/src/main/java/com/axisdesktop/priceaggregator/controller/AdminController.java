package com.axisdesktop.priceaggregator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.axisdesktop.priceaggregator.entity.CatalogCategory;
import com.axisdesktop.priceaggregator.entity.Currency;
import com.axisdesktop.priceaggregator.entity.Provider;
import com.axisdesktop.priceaggregator.service.CatalogCategoryService;
import com.axisdesktop.priceaggregator.service.CurrencyService;
import com.axisdesktop.priceaggregator.service.ProviderService;

@Controller
@RequestMapping( "/admin" )
public class AdminController {
	@Autowired
	private ProviderService providerService;

	@Autowired
	private CatalogCategoryService catalogCategoryService;

	@Autowired
	private CurrencyService currencyService;

	@RequestMapping
	public String index( Model model ) {

		model.addAttribute( "providers", providerService.list() );
		model.addAttribute( "categories", catalogCategoryService.list() );
		model.addAttribute( "currencies", currencyService.list() );

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
		Provider provider = providerService.getById( providerId );
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
		CatalogCategory category = catalogCategoryService.getById( categoryId );
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

	// currencies

	@RequestMapping( value = "/currency/create", method = RequestMethod.POST )
	public String createCurrency( @ModelAttribute Currency currency ) {
		currencyService.create( currency );

		return "redirect:/admin";
	}

	@RequestMapping( value = "/currency/update/{currencyId}", method = RequestMethod.GET )
	public String editCurrency( @PathVariable int currencyId, Model model ) {
		Currency currency = currencyService.getById( currencyId );
		model.addAttribute( "currency", currency );

		return "admin/currency/edit";
	}

	@RequestMapping( value = "/currency/update", method = RequestMethod.POST )
	public String updateCurrency( @ModelAttribute Currency currency ) {
		currencyService.update( currency );

		return "redirect:/admin";
	}

	@RequestMapping( value = "/currency/delete/{currencyId}" )
	public String deleteCurrency( @PathVariable int currencyId ) {
		currencyService.delete( currencyId );

		return "redirect:/admin";
	}

}