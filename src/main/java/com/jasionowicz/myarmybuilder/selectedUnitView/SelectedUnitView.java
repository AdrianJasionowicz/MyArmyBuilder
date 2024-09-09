package com.jasionowicz.myarmybuilder.selectedUnitView;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeDTO;
import com.jasionowicz.myarmybuilder.unit.Unit;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SelectedUnitView {

    private int id;
    private int quantity;
    private List<SelectedUpgradeDTO> upgrades;
    private String unitType;

    public SelectedUnitView (SelectedUnit selectedUnit){
        this.id = selectedUnit.getId();
        this.quantity = selectedUnit.getQuantity();
        this.upgrades = selectedUnit.getSelectedUpgrades().stream()
                .map(SelectedUpgradeDTO::new)
                .toList();
        this.unitType = selectedUnit.getUnitType();
    }



}
