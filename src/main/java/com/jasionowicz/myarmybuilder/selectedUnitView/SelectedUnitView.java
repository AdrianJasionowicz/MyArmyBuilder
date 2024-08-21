package com.jasionowicz.myarmybuilder.selectedUnitView;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeDTO;
import lombok.*;

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

    public SelectedUnitView (SelectedUnit selectedUnit){
        this.id = selectedUnit.getId();
        this.quantity = selectedUnit.getQuantity();
        this.upgrades = selectedUnit.getSelectedUpgrades().stream()
                .map(upgrade -> new SelectedUpgradeDTO(upgrade))
                .toList();
    }

}
