package com.vaadin.tutorial.crm.ldap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"com.vaadin.tutorial.crm.*"})
@Profile("default")
@EnableLdapRepositories
public class AppConfig {

	@Autowired
    private Environment env;

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(env.getProperty("ldap.urls"));
        contextSource.setBase(env.getRequiredProperty("ldap.base.dn"));
        contextSource.setUserDn(env.getRequiredProperty("ldap.username"));
        contextSource.setPassword(env.getRequiredProperty("ldap.password"));
        contextSource.afterPropertiesSet();
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }

    @Bean
    public LdapClient ldapClient() {
        return new LdapClient();
    }

}