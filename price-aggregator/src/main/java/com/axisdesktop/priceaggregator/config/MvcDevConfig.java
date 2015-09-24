package com.axisdesktop.priceaggregator.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
	@Autowired
	private ApplicationContext ctx;

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
		String path = System.getProperty( "jboss.server.temp.dir" );
		String imgPath;
		String filePath;

		if( path.isEmpty() ) {
			path = System.getProperty( "java.io.tmpdir" );
		}

		path = path + ctx.getApplicationName() + "/static";
		imgPath = path + "/img/";
		filePath = path + "/file";

		new File( path ).mkdirs();
		new File( imgPath ).mkdirs();
		new File( filePath ).mkdirs();

		System.out.println( "============================ " + ctx.getApplicationName() );
		System.out.println( System.getProperty( "jboss.server.temp.dir" ) );
		System.out.println( System.getProperty( "java.io.tmpdir" ) );

		// <mvc:resources mapping="/images/**"
		// location="file:/absolute/path/to/image/dir/"/>
		System.out.println( "file:/" + imgPath );

		registry.addResourceHandler( "/resources/**" ).addResourceLocations( "/resources/" );
		// registry.addResourceHandler( "/img/**" ).addResourceLocations( imgstr
		// );

		// "file:/C:\\Users\\coder\\Downloads\\dev-wildfly-8.2.0.Final\\standalone\\tmp\\price-aggregator\\static\\img\\"

		registry.addResourceHandler( "/img/**" ).addResourceLocations( "file:/" + imgPath );
	}
}
