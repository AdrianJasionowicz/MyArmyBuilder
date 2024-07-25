package com.jasionowicz.myarmybuilder.selectedUpgrades;

import com.jasionowicz.myarmybuilder.upgrade.UpgradeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelectedUpgradesDTO {
    private Integer id;
    private int quantity;
    private boolean selected;
    private Double totalCost;
    private String name;
    private double pointsCost;


    public SelectedUpgradesDTO(UpgradeDTO upgradeDTO) {
        this.id = upgradeDTO.getId();
        this.name = upgradeDTO.getName();
        this.quantity = upgradeDTO.getQuantity();
    }

    public SelectedUpgradesDTO(SelectedUpgrades selectedUpgrades) {
        this.id = selectedUpgrades.getId();
        this.quantity = selectedUpgrades.getQuantity();
        this.selected = selectedUpgrades.isSelected();
        this.totalCost = selectedUpgrades.getTotalCost();
        this.name = selectedUpgrades.getUpgrade().getName();
        this.pointsCost = selectedUpgrades.getUpgrade().getPointsCost();
    }
}
