package com.jasionowicz.myarmybuilder.security.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BuilderUser {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Integer id;
private String username;
private String password;
private String role;
private String email;

}
