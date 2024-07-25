package com.jasionowicz.myarmybuilder.upgrade;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jasionowicz.myarmybuilder.unit.Unit;
import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Upgrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double pointsCost;
    private String upgradeType;
    private String description;
    @ManyToOne
    @JoinColumn(name = "unit_id")
    @JsonBackReference
    private Unit unit;

    public Upgrade(String name) {
        this.name = name;
    }

    public Upgrade(String name, double pointsCost) {
        this.name = name;
        this.pointsCost = pointsCost;
    }


}