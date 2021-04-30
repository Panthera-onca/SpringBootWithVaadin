package com.vaadin.tutorial.crm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity 
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable()

		// Register our CustomRequestCache, that saves unauthorized access attempts, so
		// the user is redirected after login.
		.requestCache().requestCache(new CustomRequestCache())

		// Restrict access to our application.
		.and().authorizeRequests()

		// Allow all flow internal requests.
		.requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

		// Allow all requests by logged in users.
		.anyRequest().authenticated()

		// Configure the login page.
		.and().formLogin().loginPage(LOGIN_URL).permitAll().loginProcessingUrl(LOGIN_PROCESSING_URL)
		.failureUrl(LOGIN_FAILURE_URL)
		
		.and().rememberMe().key("pssssst").alwaysRemember(true)

		// Configure logout
		.and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }
    
    
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        // typical logged in user with some privileges
        UserDetails normalUser =
            User.withUsername("user")
                .password("{noop}password")
                .roles("User")
                .build();

        // admin user with all privileges
        UserDetails adminUser =
            User.withUsername("admin")
                .password("{noop}pa$$w0rd")
                .roles("User", "Admin")
                .build();

        return new InMemoryUserDetailsManager(normalUser, adminUser);
    }
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
            "/VAADIN/**",
            "/favicon.ico",
            "/robots.txt",
            "/manifest.webmanifest",
            "/sw.js",
            "/offline.html",
            "/frontend/**",
            "/webjars/**",
            "/icons/**",
            "/images/**",
            "/styles/**",
            "/frontend-es5/**", 
            "/frontend-es6/**",
            "/h2-console/**");
    }
    
    
	
	

}
