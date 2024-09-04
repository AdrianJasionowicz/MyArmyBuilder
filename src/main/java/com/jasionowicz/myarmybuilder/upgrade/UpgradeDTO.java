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
    private double pointsCost;


    private UpgradeRepository upgradeRepository;
    private Integer selectedUnitId;
    private String upgradeType;
    private String description;

    public UpgradeDTO(Upgrade upgrade, UpgradeRepository upgradeRepository) {
        this.upgradeRepository = upgradeRepository;
        this.id = upgrade.getId();
        this.name = upgrade.getName();
        this.pointsCost = upgrade.getPointsCost();
    }


    public UpgradeDTO(UpgradeRepository upgradeRepository) {
        this.upgradeRepository = upgradeRepository;
    }
}
