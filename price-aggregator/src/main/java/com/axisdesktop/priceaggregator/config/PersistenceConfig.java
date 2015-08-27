package com.axisdesktop.priceaggregator.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( "com.axisdesktop.priceaggregator.repository" )
public class PersistenceConfig {
	@Autowired
	private Environment environment;

	@Value( "${db.init:false}" )
	private String initDatabase;

	@Bean
	public DataSource dataSource() {
		DataSource dataSource = null;

		if( environment.getRequiredProperty( "db.source.type" ) == "jndi" ) {
			String dsn = environment.getRequiredProperty( "db.jndi" );
			JndiTemplate jndi = new JndiTemplate();

			try {
				dataSource = (DataSource)jndi.lookup( dsn );
			}
			catch( NamingException e ) {
				System.out.println( "NamingException " + dsn );
			}
		}
		else {
			DriverManagerDataSource ds = new DriverManagerDataSource();
			ds.setDriverClassName( environment.getRequiredProperty( "db.driver" ) );
			ds.setUrl( environment.getRequiredProperty( "db.url" ) );
			ds.setUsername( environment.getRequiredProperty( "db.username" ) );
			ds.setPassword( environment.getRequiredProperty( "db.password" ) );

			dataSource = ds;
		}

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl( Boolean.FALSE );
		vendorAdapter.setShowSql( Boolean.TRUE );
		vendorAdapter.setDatabasePlatform( "org.hibernate.dialect.MySQLDialect" );
		vendorAdapter.setDatabase( Database.MYSQL );

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter( vendorAdapter );
		factory.setPackagesToScan( "com.axisdesktop.priceaggregator.entity" );
		factory.setDataSource( dataSource() );

		// Properties jpaProperties = new Properties();
		// jpaProperties.put( "hibernate.hbm2ddl.auto", environment.getProperty(
		// "hibernate.hbm2ddl.auto" ) );
		// factory.setJpaProperties( jpaProperties );
		// factory.afterPropertiesSet();
		// factory.setLoadTimeWeaver( new InstrumentationLoadTimeWeaver() );

		return factory;
	}

	// @Bean
	// public PlatformTransactionManager transactionManager() {
	// EntityManagerFactory factory = entityManagerFactory().getObject();
	// return new JpaTransactionManager( factory );
	// }

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory( entityManagerFactory().getObject() );
		transactionManager.setJpaDialect( new HibernateJpaDialect() );
		return transactionManager;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	// @Bean
	// public DataSourceInitializer dataSourceInitializer( DataSource dataSource
	// ) {
	// DataSourceInitializer dataSourceInitializer = new
	// DataSourceInitializer();
	// dataSourceInitializer.setDataSource( dataSource );
	// dataSourceInitializer.setEnabled( Boolean.parseBoolean( initDatabase ) );
	//
	// ResourceDatabasePopulator databasePopulator = new
	// ResourceDatabasePopulator();
	// databasePopulator.addScript( new ClassPathResource( "db-dump.sql" ) );
	//
	// dataSourceInitializer.setDatabasePopulator( databasePopulator );
	//
	// return dataSourceInitializer;
	// }

}
