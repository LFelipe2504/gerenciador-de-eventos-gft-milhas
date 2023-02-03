package com.gft.gerenciador.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {		
		httpSecurity
		.authorizeHttpRequests()
		.antMatchers("/home","/usuario/editar").permitAll()
		.anyRequest().authenticated()
		.and().formLogin(form -> form 
				.loginPage("/login")
				.defaultSuccessUrl("/home", true)
				.permitAll())
		.logout(logout -> logout.logoutUrl("/logout"));
	}
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user = 
				User.withDefaultPasswordEncoder()
				.username("root")
				.password("root")
				.roles("ADM")
				.build();
				return new InMemoryUserDetailsManager(user);
	}

}
