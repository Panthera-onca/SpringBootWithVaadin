package com.vaadin.tutorial.crm.backend.entity;

import java.util.Collection;

import javax.naming.Name;
import javax.persistence.GeneratedValue;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entry(
	    objectClasses = {"inetOrgPerson", "organizationalPerson", "person", "top"}
	)
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public final class User implements UserDetails{
	    private @GeneratedValue Long id;
	    

		@Id
	    private Name dn;

	    @DnAttribute(value = "uid", index = 1)
	    private String uid;

	    @DnAttribute(value = "ou", index = 0)
	    @Transient
	    private String group;

	    @Attribute(name = "cn")
	    private String username;

	    @Attribute(name = "sn")
	    private String fullName;

	    @Attribute(name = "userPassword")
	    private String password;
	    
	    public User() {

	    }

	    public User(String uid, String username, String fullName, String group, String password) {
	        this.dn = LdapNameBuilder.newInstance("uid=" + uid + ",ou=" + group).build();
	        this.uid = uid;
	        this.username = username;
	        this.fullName = fullName;
	        this.group = group;
	        this.password = password;
	    }

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public String getGroup() {
			return group;
		}

		public void setGroup(String group) {
			this.group = group;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getFullName() {
			return fullName;
		}

		public void setFullName(String fullName) {
			this.fullName = fullName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return true;
		}
	    
	    
	    
	    
}
