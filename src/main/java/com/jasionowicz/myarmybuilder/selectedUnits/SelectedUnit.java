package com.jasionowicz.myarmybuilder.selectedUnits;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jasionowicz.myarmybuilder.armyComposition.selectedStats.SelectedStats;
import com.jasionowicz.myarmybuilder.armyComposition.selectedStats.SelectedStatsDTO;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgrades;
import com.jasionowicz.myarmybuilder.unit.Unit;
import com.jasionowicz.myarmybuilder.unit.UnitStats;
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
    @Column(name = "id")
    private Integer id;
    private int quantity;
    @ManyToOne
    private Unit unit;
    @OneToOne
    @JoinColumn(name = "selectedStats_id")
    private SelectedStats selectedStats;
    @OneToMany(mappedBy = "selectedUnit", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SelectedUpgrades> selectedUpgrades = new ArrayList<>();


    public String getUnitType() {
        if (unit == null) {
            throw new IllegalStateException("Unit is not initialized");
        }
        return unit.getUnitType();
    }

    public Integer getUnitId() {
        if (unit == null) {
            return null;
        }
        return unit.getId();
    }

    public SelectedUnit(Unit unit) {
        this.unit = unit;
        this.quantity = unit.getMinQuantity();
        this.selectedStats = new SelectedStats(unit.getUnitStats());
        this.selectedUpgrades = unit.getUpgradesList().stream()
                .map(upgrade -> new SelectedUpgrades(upgrade, this))
                .collect(Collectors.toList());
    }

    private SelectedStats convertToSelectedStats(SelectedStatsDTO dto) {
        UnitStats unitStats = new UnitStats();
        unitStats.setM(dto.getM());
        unitStats.setWs(dto.getWs());
        unitStats.setBs(dto.getBs());
        unitStats.setS(dto.getS());
        unitStats.setT(dto.getT());
        unitStats.setW(dto.getW());
        unitStats.setI(dto.getI());
        unitStats.setA(dto.getA());
        unitStats.setLd(dto.getLd());
        unitStats.setBasicSave(dto.getBasicSave());
        unitStats.setWardSave(dto.getWardSave());
        return new SelectedStats(unitStats);
    }

    public void addUpgrade(SelectedUpgrades upgrade) {
        if (this.selectedUpgrades == null) {
            this.selectedUpgrades = new ArrayList<>();
        }
        this.selectedUpgrades.add(upgrade);
    }



    public String getName() {
        return unit.getName();
    }

    public void setName(String name) {
        unit.setName(name);
    }

    @Override
    public String toString() {
        return "SelectedUnit{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", unit=" + unit +
                ", selectedStats=" + selectedStats +
                ", selectedUpgrades=" + selectedUpgrades +
                '}';
    }
}

//POST: armies
//POST: armies/id/units
//GET: armies/id
//GET: armies/id/stats
// wzorzec projektowy pylek
// czysta architektura