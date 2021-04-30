package com.vaadin.tutorial.crm.backend.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.vaadin.tutorial.crm.backend.entity.UserInfo;
import com.vaadin.tutorial.crm.backend.repository.UserRepository;

@Component("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
	private UserInfo userInfo;
	@Autowired
	public UserRepository userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user = null;
		try {
			user = userMapper.getUserByUsername(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(user == null)
			throw new UsernameNotFoundException("user not found!");
		
		List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles());
		userInfo = new UserInfo(username,user.getPassword(),user.isEnabled(),true,true,true,authorities);
		userInfo.setId(user.getId());
		userInfo.setFirstname(user.getFirstname());
		userInfo.setLastname(user.getLastname());
		userInfo.setEmail(user.getEmail());
		userInfo.setUsername(user.getUsername());
		userInfo.setRoles(user.getRoles());
		return userInfo;
	}
	
	/**
     * A validator method for User handles.
     *
     * @return <code>null</code> if the handle is OK to use or an error message if
     *         it is not OK.
     */
    public String validateUsername(String username) {

        if (username == null) {
            return "Username ne peut pas etre vide";
        }
        if (username.length() < 4) {
            return "Handle can't be shorter than 4 characters";
        }
        List<String> reservedHandles = Arrays.asList("admin", "test", "null", "void");
        if (reservedHandles.contains(username)) {
            return String.format("'%s' is not available as a handle", username);
        }

        return null;
    }

    /**
     * Utility Exception class that we can use in the frontend to show that
     * something went wrong during save.
     */
    public static class ServiceException extends Exception {
        public ServiceException(String msg) {
            super(msg);
        }
    }

}
