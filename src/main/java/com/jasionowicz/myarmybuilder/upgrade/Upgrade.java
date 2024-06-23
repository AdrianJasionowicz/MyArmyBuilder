package com.jasionowicz.myarmybuilder.upgrade;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jasionowicz.myarmybuilder.unit.Unit;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@ToString
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
    private int quantity;
    private boolean selected;
    private double pointsCost;
    private Integer unitUpgradesId;
    private Double totalCost;
    @ManyToOne
    @JoinColumn(name = "unit_id")
    @JsonBackReference
    private Unit unit;

    public Upgrade(String name) {
        this.name = name;
    }

    public Upgrade(String name, int quantity, double pointsCost, Integer unitUpgradesId) {
        this.name = name;
        this.quantity = quantity;
        this.pointsCost = pointsCost;
        this.unitUpgradesId = unitUpgradesId;
    }
}