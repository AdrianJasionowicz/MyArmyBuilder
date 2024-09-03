package com.jasionowicz.myarmybuilder.security.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuilderUserRepository extends JpaRepository<BuilderUser, Integer> {

    Optional<BuilderUser> findByUsername(String username);
}
