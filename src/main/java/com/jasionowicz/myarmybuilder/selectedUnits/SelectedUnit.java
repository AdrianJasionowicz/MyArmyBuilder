package com.jasionowicz.myarmybuilder.selectedUnits;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jasionowicz.myarmybuilder.selectedStats.SelectedStats;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgrade;
import com.jasionowicz.myarmybuilder.unit.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelectedUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int quantity;
    @ManyToOne
    private Unit unit;
    @OneToOne
    @JoinColumn(name = "selectedStats_id")
    private SelectedStats selectedStats;
    @OneToMany(mappedBy = "selectedUnit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SelectedUpgrade> selectedUpgrades = new ArrayList<>();


    public String getUnitType() {
        if (unit == null) {
            throw new IllegalStateException("Unit is not initialized");
        }
        return unit.getUnitType();
    }

    public SelectedUnit(Unit unit) {
        this.unit = unit;
        this.quantity = unit.getMinQuantity();
        this.selectedStats = new SelectedStats(unit.getUnitStats());
        this.selectedUpgrades = unit.getUpgradesList().stream()
                .map(upgrade -> new SelectedUpgrade(upgrade, this))
                .collect(Collectors.toList());
    }

    public String getName() {
        return unit.getName();
    }

    public void setName(String name) {
        unit.setName(name);
    }

}

//POST: armies
//POST: armies/id/units
//GET: armies/id
//GET: armies/id/stats
// wzorzec projektowy pylek
// czysta architektura