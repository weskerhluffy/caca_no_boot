package caca.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
		logger.error("pero q mierdas");
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select login,password, enabled from users where login=?")
				.authoritiesByUsernameQuery("select u.login, a.name from users u, authority a, users_authority ua"
						+ " where u.id=ua.id_user and a.id=ua.id_authority and u.login=?")
				.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.error("pero la puta madre");
		http.authorizeRequests().antMatchers("/login_form").anonymous().antMatchers("/caca/admin/**")
				.access("hasAuthority('admin')").and().formLogin().loginPage("/login_form").permitAll()
				.loginProcessingUrl("/login").failureUrl("/login_form?error").usernameParameter("login")
				.passwordParameter("password").and().logout().logoutSuccessUrl("/hello").and().exceptionHandling()
				.accessDeniedPage("/caca/403").and().csrf().and();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
