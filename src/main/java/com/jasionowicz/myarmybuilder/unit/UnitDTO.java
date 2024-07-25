package com.jasionowicz.myarmybuilder.unit;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import com.jasionowicz.myarmybuilder.upgrade.Upgrade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UnitDTO {
    private Integer id;
    private String name;
    private double pointsCostPerUnit;
    private int minQuantity;
    private String unitType;
    private String nation;
    private UnitStats unitStats;
    List<Upgrade> upgradeList = new ArrayList<>();

    private Unit unit;

    public UnitDTO(Unit unit) {
        this.unit = unit;
        getEntityToDTO();
    }

    public UnitDTO(String name, Double pointsCostPerUnit, int minQuantity, String unitType) {

    }

    public void getEntityToDTO() {
        id = unit.getId();
        name = unit.getName();
        pointsCostPerUnit = unit.getPointsCostPerUnit();
        minQuantity = unit.getMinQuantity();
        unitType = unit.getUnitType();
        unitStats = unit.getUnitStats();
        upgradeList = unit.getUpgradesList();
    }

    public void changeDTOtoEntity() {
        unit.setName(name);
        unit.setPointsCostPerUnit(pointsCostPerUnit);
        unit.setMinQuantity(minQuantity);
        unit.setUnitType(unitType);
        unit.setUnitStats(unitStats);
    }



}