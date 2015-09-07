package com.axisdesktop.priceaggregator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.axisdesktop.priceaggregator.service.CatalogCategoryService;

@Controller
public class IndexController {

	@Autowired
	private CatalogCategoryService catalogCategoryService;

	@RequestMapping( "/" )
	public String index( Model model ) {

		model.addAttribute( "categories", catalogCategoryService.list() );
		return "index";
	}
}
