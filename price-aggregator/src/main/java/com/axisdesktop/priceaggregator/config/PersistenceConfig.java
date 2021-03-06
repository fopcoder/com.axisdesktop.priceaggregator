package com.axisdesktop.priceaggregator.config;

import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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

@Profile( "production" )
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( "com.axisdesktop.priceaggregator.repository" )
public class PersistenceConfig {
	@Autowired
	private Environment environment;

	@Bean
	public DataSource dataSource() {
		DataSource dataSource = null;

		if( environment.getRequiredProperty( "db.source.type" ).equals( "jndi" ) ) {

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
			ds.setDriverClassName( environment
					.getRequiredProperty( "db.driver" ) );
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
		vendorAdapter
				.setDatabasePlatform( "org.hibernate.dialect.MySQLDialect" );
		vendorAdapter.setDatabase( Database.MYSQL );

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter( vendorAdapter );
		factory.setPackagesToScan( "com.axisdesktop.priceaggregator.entity" );
		factory.setDataSource( dataSource() );

		Properties jpaProperties = new Properties();
		// jpaProperties.put( "hibernate.connection.zeroDateTimeBehavior",
		// environment.getProperty( "hibernate.connection.zeroDateTimeBehavior"
		// ) );
		jpaProperties.put( "hibernate.format_sql",
				environment.getProperty( "hibernate.format_sql" ) );
		// jpaProperties.put( "hibernate.connection.charSet",
		// environment.getProperty( "hibernate.connection.charSet" ) );

		// jpaProperties.put( "hibernate.hbm2ddl.auto", environment.getProperty(
		// "hibernate.hbm2ddl.auto" ) );
		// jpaProperties.put( "hibernate.dialect", environment.getProperty(
		// "hibernate.dialect" ) );
		factory.setJpaProperties( jpaProperties );
		factory.afterPropertiesSet();
		// factory.setLoadTimeWeaver( new InstrumentationLoadTimeWeaver() );

		return factory;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory( entityManagerFactory()
				.getObject() );
		transactionManager.setJpaDialect( new HibernateJpaDialect() );
		return transactionManager;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}
}
