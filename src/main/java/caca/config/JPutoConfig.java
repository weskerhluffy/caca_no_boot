package caca.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//XXX: https://samerabdelkafi.wordpress.com/2014/05/29/spring-data-jpa/
@Configuration
@EnableTransactionManagement
@ComponentScan({ "caca.dao", "caca.servicio" })
@EnableJpaRepositories("caca.dao")
public class JPutoConfig {

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).setName("myDb")
				.addScript("classpath:caca.sql").addScript("classpath:mierda.sql").build();
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan(new String[] { "caca.model" });
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(true);
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		return factoryBean;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	// XXX:
	// https://www.mkyong.com/spring/spring-view-content-of-hsqldb-embedded-database/
	// default username : sa, password : ''
	@PostConstruct
	public void getDbManager() {
		DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:mem:myDb", "--user", "sa", "--password", "" });
	}
}
