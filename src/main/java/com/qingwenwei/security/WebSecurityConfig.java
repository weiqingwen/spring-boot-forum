package com.qingwenwei.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService myUserDetailsService() { // register userDetailsService
		return new MyUserDetailsService();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(this.myUserDetailsService());
		authenticationProvider.setPasswordEncoder(this.bCryptPasswordEncoder());
		return authenticationProvider;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// auth.inMemoryAuthentication()
		// .withUser("t").password("t").roles("USER")
		// .and()
		// .withUser("admin").password("admin").roles("ADMIN");

		// auth.userDetailsService(this.myUserDetailsService()).passwordEncoder(this.bCryptPasswordEncoder());

		auth.authenticationProvider(this.authenticationProvider()); // different approach
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/user/settings").authenticated() // order matters
				.antMatchers("/", "/js/**", "/css/**", "/images/**", "/fonts/**", "/bootstrap-select/**",
						"/bootstrap-datetimepicker/**", "/custom/**", "/daterangepicker/**", "/chartjs/**")
				.permitAll() // these paths are configure not to require any authentication
				.antMatchers("/post/**").permitAll() // all posts are allowed to be viewed without authentication
				.antMatchers("/user/**").permitAll() // all user profiles are allowed to be viewed without
														// authentication
				.antMatchers("/category/**").permitAll() // all categories are allowed to be viewed without
															// authentication
				.antMatchers("/avatar/**").permitAll() // temp
				.anyRequest().authenticated() // every request requires the user to be authenticated
				.and().formLogin().loginPage("/user/login").permitAll() // login URL can be accessed by anyone
				.and().logout().invalidateHttpSession(true).clearAuthentication(true).logoutSuccessUrl("/?logout")
				.permitAll();
	}
}