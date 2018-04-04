package caca.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity(debug = true)
public class SecuCacaConfig extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(SecuCacaConfig.class);

	@Autowired
	DataSource dataSource;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select login,password, enabled from users where username=?")
				.authoritiesByUsernameQuery("select u.login, a.name from users u, authority a, users_authority ua"
						+ " where u.id=ua.id_user and a.id=ua.id_authority and u.login=?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.error("pero la puta madre");
		http.authorizeRequests().antMatchers("/login_form").anonymous().antMatchers("/caca/admin/**")
				.access("hasRole('admin')").and().formLogin().loginPage("/login_form").permitAll()
				.loginProcessingUrl("/login").failureUrl("/login?error").usernameParameter("login")
				.passwordParameter("password").and().logout().logoutSuccessUrl("/hello").and().exceptionHandling()
				.accessDeniedPage("/caca/403").and().csrf();
	}
}
