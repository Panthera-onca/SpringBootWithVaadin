package com.vaadin.tutorial.crm.UI.views.data;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapRdn;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

@Component
public class ActiveDirectoryLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {

	@Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {
        String[] groups = userData.getStringAttributes("memberOf");
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (String group : groups) {
            LdapRdn authority = new DistinguishedName(group).removeLast();
            authorities.add(new SimpleGrantedAuthority(authority.getValue()));
        }
        return authorities;
    }


}
