package com.vaadin.tutorial.crm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
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
import org.springframework.security.authentication.AuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.InetOrgPersonContextMapper;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import java.util.Arrays;

import com.vaadin.tutorial.crm.UI.views.data.ActiveDirectoryLdapAuthoritiesPopulator;
import com.vaadin.tutorial.crm.backend.service.UserService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.LoggerListener;

 
@Configuration
@Component
@PropertySource("classpath:application.properties")
@EnableWebSecurity 
@EnableLdapRepositories
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
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
    private AuthenticationProvider authenticationProvider;
    
    @Autowired
    UserService userDetailsService;
    @Autowired
    private Environment env;
    
    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
    
    @Bean
    public DefaultSpringSecurityContextSource contextSource() {
        return new DefaultSpringSecurityContextSource(
                Arrays.asList("ldap://ldap.forumsys.com:389/"), "dc=example,dc=com"){{

            setUserDn("cn=read-only-admin,dc=example,dc=com");
            setPassword("password");
        }};
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider(){
        ActiveDirectoryLdapAuthenticationProvider ap = new ActiveDirectoryLdapAuthenticationProvider(
                                                                    "forumsys.com",
                                                                       "ldap://ldap.forumsys.com:389/");
        ap.setConvertSubErrorCodesToExceptions(true);
        return ap;
    }
   
    @Bean
    public BindAuthenticator bindAuthenticator(FilterBasedLdapUserSearch userSearch){
        return new BindAuthenticator(contextSource()){{
            setUserSearch(userSearch);

        }};
    }
    
    @Bean
    public FilterBasedLdapUserSearch filterBasedLdapUserSearch(){
        return new FilterBasedLdapUserSearch("cn=read-only-admin", //user-search-base
                "(uid={0})", //user-search-filter
                contextSource()); //ldapServer
    }
    
    @Bean
    public LdapAuthoritiesPopulator authoritiesPopulator(){
        return new ActiveDirectoryLdapAuthoritiesPopulator();
    }
    @Bean
    public UserDetailsContextMapper userDetailsContextMapper(){
        return new InetOrgPersonContextMapper();
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
