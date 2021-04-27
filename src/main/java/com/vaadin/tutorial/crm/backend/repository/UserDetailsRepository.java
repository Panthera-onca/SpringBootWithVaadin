package com.vaadin.tutorial.crm.backend.repository;

import java.util.Collection;
import java.util.List;
import com.vaadin.tutorial.crm.backend.entity.UserDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

}
