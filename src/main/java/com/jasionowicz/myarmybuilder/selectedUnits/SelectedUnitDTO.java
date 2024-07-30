package com.jasionowicz.myarmybuilder.selectedUnits;

import com.jasionowicz.myarmybuilder.armyComposition.selectedStats.SelectedStatsDTO;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeDTO;
import com.jasionowicz.myarmybuilder.unit.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelectedUnitDTO {
    private Integer id;
    private int quantity;
    @Setter
    private SelectedStatsDTO selectedStatsDTO;
    @Setter
    private List<SelectedUpgradeDTO> selectedUpgrades = new ArrayList<>();
    private Unit unit;

}
