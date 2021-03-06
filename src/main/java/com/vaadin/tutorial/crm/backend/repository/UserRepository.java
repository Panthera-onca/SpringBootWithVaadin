package com.vaadin.tutorial.crm.backend.repository;

import java.util.List;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import com.vaadin.tutorial.crm.backend.entity.User;

@Repository
public interface UserRepository extends LdapRepository<User> {

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    List<User> findByUsernameLikeIgnoreCase(String username);

}