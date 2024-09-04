package com.jasionowicz.myarmybuilder.unit;

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


}