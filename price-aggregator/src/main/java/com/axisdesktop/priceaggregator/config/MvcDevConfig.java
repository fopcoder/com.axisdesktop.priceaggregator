package com.axisdesktop.priceaggregator.config;

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

@Profile( "development" )
@Configuration
@EnableWebMvc
@ComponentScan( "com.axisdesktop.priceaggregator.controller" )
public class MvcDevConfig extends WebMvcConfigurerAdapter {
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

	@Override
	public void addResourceHandlers( ResourceHandlerRegistry registry ) {

		System.out.println( "============================" );
		System.out.println( System.getProperty( "jboss.server.temp.dir" ) );
		System.out.println( System.getProperty( "java.io.tmpdir" ) );

		// <mvc:resources mapping="/images/**"
		// location="file:/absolute/path/to/image/dir/"/>

		registry.addResourceHandler( "/resources/**" ).addResourceLocations(
				"/resources/" );
	}

}
