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
public class SelectedUpgradeDTO {
    private Integer id;
    private int quantity;
    private boolean selected;
    private Double totalCost;
    private String name;
    private double pointsCost;


    public SelectedUpgradeDTO(UpgradeDTO upgradeDTO) {
        this.id = upgradeDTO.getId();
        this.name = upgradeDTO.getName();
        this.quantity = upgradeDTO.getQuantity();
    }

    public SelectedUpgradeDTO(SelectedUpgrade selectedUpgrade) {
        this.id = selectedUpgrade.getId();
        this.quantity = selectedUpgrade.getQuantity();
        this.selected = selectedUpgrade.isSelected();
        this.totalCost = selectedUpgrade.getTotalCost();
        this.name = selectedUpgrade.getUpgrade().getName();
        this.pointsCost = selectedUpgrade.getUpgrade().getPointsCost();
    }

}
