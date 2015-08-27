package com.axisdesktop.priceaggregator.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan( "com.axisdesktop.priceaggregator" )
@PropertySource( "classpath:application.properties" )
public class AppConfig {
	// @Bean
	// public MessageSource messageSource() {
	// ReloadableResourceBundleMessageSource messageSource = new
	// ReloadableResourceBundleMessageSource();
	// messageSource.setBasename( "classpath:messages" );
	// messageSource.setDefaultEncoding( "UTF-8" );
	// return messageSource;
	// }
}
