package com.axisdesktop.priceaggregator.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Profile( "development-h2" )
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( "com.axisdesktop.priceaggregator.repository" )
public class PersistenceDevConfig {
	@Autowired
	private Environment environment;

	@Bean
	public DataSource dataSource() {
		// localhost:8082 jdbc:h2:mem:testdb
		System.out.println( "============================" );
		System.out.println( System.getProperty( "jboss.server.temp.dir" ) );

		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType( EmbeddedDatabaseType.H2 ).addScript( "sql/schema.sql" )
				.addScript( "sql/data.sql" ).setName( "testdb" ).build();
		return db;
	}

	@Bean( initMethod = "start", destroyMethod = "stop" )
	public Server startDBManager() throws SQLException {
		return Server.createWebServer();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl( Boolean.FALSE );
		vendorAdapter.setShowSql( Boolean.TRUE );
		vendorAdapter.setDatabasePlatform( "org.hibernate.dialect.MySQL5Dialect" );
		vendorAdapter.setDatabase( Database.MYSQL );

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter( vendorAdapter );
		factory.setPackagesToScan( "com.axisdesktop.priceaggregator.entity" );
		factory.setDataSource( dataSource() );

		Properties jpaProperties = new Properties();
		jpaProperties.put( "hibernate.format_sql", environment.getProperty( "hibernate.format_sql" ) );
		factory.setJpaProperties( jpaProperties );
		factory.afterPropertiesSet();

		return factory;
	}

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
}
