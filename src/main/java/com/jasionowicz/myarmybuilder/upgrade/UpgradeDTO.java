package com.jasionowicz.myarmybuilder.upgrade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Component
public class UpgradeDTO {
    private int id;
    private String name;
    private int quantity;
    private boolean selected;
    private double pointsCost;
    private Integer unitUpgradesId;

    private Upgrade upgrade;

    public UpgradeDTO(Upgrade upgrade) {
        this.upgrade = upgrade;
        getDTOFromEntity();
    }


    public void getDTOFromEntity() {
        id = upgrade.getId();
        name = upgrade.getName();
        quantity = upgrade.getQuantity();
        selected = upgrade.isSelected();
        pointsCost = upgrade.getPointsCost();
        unitUpgradesId = upgrade.getUnitUpgradesId();
    }
}
