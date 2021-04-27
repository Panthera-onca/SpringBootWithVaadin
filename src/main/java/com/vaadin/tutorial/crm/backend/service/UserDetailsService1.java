package com.vaadin.tutorial.crm.backend.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

import com.vaadin.flow.server.ServiceException;
import com.vaadin.tutorial.crm.backend.entity.Livre;
import com.vaadin.tutorial.crm.backend.entity.UserDetails;
import com.vaadin.tutorial.crm.backend.repository.LivreRepository;
import com.vaadin.tutorial.crm.backend.repository.UserDetailsRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserDetailsService1 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3551429554854595392L;
	private static final Logger LOGGER = Logger.getLogger(UserDetailsService1.class.getName());
	
	private UserDetailsRepository userDetailsRepository;
	private String previousHandle;
	
	public UserDetailsService1(UserDetailsRepository userDetailsRepository) {
		this.userDetailsRepository = userDetailsRepository;
	}
	
	public List<UserDetails> findAll() {
		return userDetailsRepository.findAll();
	}
	
	public void save(UserDetails userDetails) throws ServiceException {

        // Here you can store the object into the DB, call REST services, etc.

        // for demo purposes, always fail first try
        if (previousHandle == null || !previousHandle.equals(userDetails.getHandle())) {
            previousHandle = userDetails.getHandle();
            throw new ServiceException("This exception simulates an error in the backend, and is intentional. Please try to submit the form again.");
        }else if (userDetails == null){ 
			LOGGER.log(Level.SEVERE,
					"userDetails is null. Are you sure you have connected your form to the application?");
			return;
		}
        userDetailsRepository.save(userDetails);
    }
	
	public long count() {
		return userDetailsRepository.count();
	}
	
	public void delete(UserDetails userDetails) {
		userDetailsRepository.delete(userDetails);
	}
	
	public String validateHandle(String handle) {

        if (handle == null) {
            return "Handle can't be empty";
        }
        if (handle.length() < 4) {
            return "Handle can't be shorter than 4 characters";
        }
        List<String> reservedHandles = Arrays.asList("admin", "test", "null", "void");
        if (reservedHandles.contains(handle)) {
            return String.format("'%s' is not available as a handle", handle);
        }

        return null;
    }

    /**
     * Utility Exception class that we can use in the frontend to show that
     * something went wrong during save.
     */
    public static class ServiceException extends Exception {
        /**
		 * 
		 */
		private static final long serialVersionUID = -4027312046639104192L;

		public ServiceException(String msg) {
            super(msg);
        }
    }

}
