package com.axisdesktop.priceaggregator.config;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Profile( "production" )
@Configuration
@EnableWebMvc
@ComponentScan( "com.axisdesktop.priceaggregator.controller" )
public class MvcConfig extends WebMvcConfigurerAdapter {
	@Bean
	public TemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix( "/WEB-INF/views/" );
		templateResolver.setSuffix( ".html" );
		templateResolver.setCharacterEncoding( "UTF-8" );
		templateResolver.setTemplateMode( "HTML5" );

		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver( templateResolver() );

		return templateEngine;
	}

	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setCharacterEncoding( "UTF-8" );
		viewResolver.setTemplateEngine( templateEngine() );

		return viewResolver;
	}

	@Bean
	public Path staticDir() {
		// TODO set path in application.properties or somehow else
		return Paths.get( "/var/www/virtual/price_aggregator/static" );
	}

	@Bean
	public Path imgDir() {
		return staticDir().resolve( "img" );
	}

	@Bean
	public Path fileDir() {
		return staticDir().resolve( "file" );
	}

	@Override
	public void addResourceHandlers( ResourceHandlerRegistry registry ) {
		Path imgDir = imgDir();
		Path fileDir = fileDir();

		try {
			Files.createDirectories( imgDir );
			Files.createDirectories( fileDir );

			registry.addResourceHandler( "/img/**" ).addResourceLocations(
					"file:/" + imgDir.toString()
							+ FileSystems.getDefault().getSeparator() );
			registry.addResourceHandler( "/file/**" ).addResourceLocations(
					"file:/" + fileDir.toString()
							+ FileSystems.getDefault().getSeparator() );
		}
		catch( IOException e ) {
			e.printStackTrace();
		}

		registry.addResourceHandler( "/resources/**" ).addResourceLocations(
				"/resources/" );
	}

}
