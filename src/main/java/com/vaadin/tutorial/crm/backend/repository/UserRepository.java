package com.vaadin.tutorial.crm.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.vaadin.tutorial.crm.backend.entity.Livre;
import com.vaadin.tutorial.crm.backend.entity.UserInfo;


@Repository 
public interface UserRepository extends JpaRepository<UserInfo, Long> {
    
	UserInfo getUserByUsername(String username);

}
