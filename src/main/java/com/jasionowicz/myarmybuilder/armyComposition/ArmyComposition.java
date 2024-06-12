package com.jasionowicz.myarmybuilder.armyComposition;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component
public class ArmyComposition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    private String armyName;
    private double totalPoints;
    private double totalLords;
    private double totalHeroes;
    private double totalCore;
    private double totalSpecial;
    private double totalRare;
}
