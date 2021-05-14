package com.vaadin.tutorial.crm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.vaadin.tutorial.crm.backend.service.UserService;
import com.vaadin.tutorial.crm.ldap.LdapClient;

import org.springframework.security.authentication.AuthenticationManager;

 
@Configuration
@PropertySource("classpath:application.properties")
@EnableWebSecurity 
@EnableLdapRepositories(basePackages = "com.vaadin.tutorial.crm.backend.**")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private static final String DEFAULT_PAGE = "/";
	private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";
    private static final String REGISTER = "/user";
    private static final String RESERVE = "/reservation";
    private static final String RESERVATION ="/resview";
    private static final String ADMIN = "/admin";
    
    
    
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable()

		// Register our CustomRequestCache, that saves unauthorized access attempts, so
		// the user is redirected after login.
		.requestCache().requestCache(new CustomRequestCache())

		// Restrict access to our application.
		.and().authorizeRequests().antMatchers("/", "/css/*", "/health", 
				DEFAULT_PAGE, REGISTER, RESERVE, RESERVATION, ADMIN)
		          .permitAll()

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
    
    
  //Getting values from properties file
    @Value("${ldap.urls}")
    private String ldapUrls;
    @Value("${ldap.base.dn}")
    private String ldapBaseDn;
    @Value("${ldap.username}")
    private String ldapSecurityPrincipal;
    @Value("${ldap.password}")
    private String ldapPrincipalPassword;
    @Value("${ldap.user.dn.pattern}")
    private String ldapUserDnPattern;
    @Value("${ldap.enabled}")
    private String ldapEnabled;
    
    @Autowired
    UserService userDetailsService;
    
    @Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.ldapAuthentication()
		.contextSource()
		.url(ldapUrls + ldapBaseDn)
		.managerDn(ldapSecurityPrincipal)
		.managerPassword(ldapPrincipalPassword)
		.and()
		.userDnPatterns(ldapUserDnPattern)
		.and()
		.userDetailsService(userDetailsService);
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		@SuppressWarnings("deprecation")
		LdapShaPasswordEncoder passwordEncoder = new LdapShaPasswordEncoder();
		return passwordEncoder;
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
