package com.jasionowicz.myarmybuilder.unit;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jasionowicz.myarmybuilder.unitStats.UnitStats;
import com.jasionowicz.myarmybuilder.upgrade.Upgrade;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double pointsCostPerUnit;
    private int minQuantity;
    private String unitType;
    private String nation;
    @OneToOne
    @JoinColumn(name = "unitStats_id")
    private UnitStats unitStats;
    @OneToMany(mappedBy = "unit", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Upgrade> upgradesList = new ArrayList<>();


    public Unit(String name, double pointsCostPerUnit, String unitType) {
        this.name = name;
        this.pointsCostPerUnit = pointsCostPerUnit;
        this.unitType = unitType;
    }

    public Unit(String name, int minQuantityuantity) {
        this.name = name;
        this.minQuantity = minQuantityuantity;
    }


    public Unit(String name, String unitType) {
        this.name = name;
        this.unitType = unitType;
    }

    public Unit(String name) {
        this.name = name;
    }

    public Unit(String name, double pointsCostPerUnit, int minQuantity, String unitType) {
        this.name = name;
        this.pointsCostPerUnit = pointsCostPerUnit;
        this.minQuantity = minQuantity;
        this.unitType = unitType;
    }

}