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
    private Double totalCost;
    private UpgradeRepository upgradeRepository;

    public UpgradeDTO(Upgrade upgrade, UpgradeRepository upgradeRepository) {
        this.upgradeRepository = upgradeRepository;
        this.id = upgrade.getId();
        this.name = upgrade.getName();
        this.quantity = upgrade.getQuantity();
        this.selected = upgrade.isSelected();
        this.pointsCost = upgrade.getPointsCost();
        this.unitUpgradesId = upgrade.getUnitUpgradesId();
        this.totalCost = upgrade.getTotalCost();
    }

    public UpgradeDTO(Upgrade upgrade) {
    }

    public Upgrade toEntity() {
        Upgrade upgrade = new Upgrade();
        upgrade.setId(this.id);
        upgrade.setName(this.name);
        upgrade.setQuantity(this.quantity);
        upgrade.setSelected(this.selected);
        upgrade.setPointsCost(this.pointsCost);
        upgrade.setUnitUpgradesId(this.unitUpgradesId);
        return upgrade;
    }

    public UpgradeDTO(UpgradeRepository upgradeRepository) {
        this.upgradeRepository = upgradeRepository;
    }
}
