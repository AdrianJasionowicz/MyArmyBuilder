package com.jasionowicz.myarmybuilder.security.model;


import com.jasionowicz.myarmybuilder.armyComposition.ArmyComposition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "builderUser")
    private List<ArmyComposition> armyCompositions = new ArrayList<>();


}
