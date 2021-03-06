package io.vendapro.app.springsecurity.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//		.withUser("user1@example.com").password("user1").roles("USER")
//		.and().withUser("admin1@example.com").password("admin1").roles("USER", "ADMIN");	
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/resources/**").permitAll()
		.antMatchers("/").hasAnyRole("ANONYMOUS", "USER")
		.antMatchers("/login/*").hasAnyRole("ANONYMOUS", "USER")
		.antMatchers("/logout/*").hasAnyRole("ANONYMOUS", "USER")
		.antMatchers("/admin/*").hasRole("ADMIN")
		.antMatchers("/events/").hasRole("ADMIN")		
			.antMatchers("/**").access("hasRole('USER')")
			.and().formLogin()
					.loginPage("/login/form")
					.loginProcessingUrl("/login")
					.failureUrl("/login/form?error")
					.usernameParameter("username")
					.passwordParameter("password")
					.defaultSuccessUrl("/default", true)
			.and().httpBasic()
			.and().logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
			.and().csrf().disable();
	}

// Replaced by customizing CalendarUserDetailsService	
//	@Override
//	public UserDetailsManager userDetailsService() {
//		InMemoryUserDetailsManager manager = new
//				InMemoryUserDetailsManager();
//				manager.createUser(
//				User.withUsername("user1@example.com")
//				.password("user1").roles("USER").build());
//				manager.createUser(
//				User.withUsername("admin1@example.com")
//				.password("admin1").roles("USER", "ADMIN").build());
//				return manager;
//	}
//	
//	
	

}
