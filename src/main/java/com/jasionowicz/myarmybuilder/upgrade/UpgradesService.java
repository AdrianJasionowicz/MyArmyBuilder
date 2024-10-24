package com.jasionowicz.myarmybuilder.upgrade;

import org.springframework.stereotype.Service;

@Service
public class UpgradesService {

    private final UpgradeRepository upgradeRepository;

    public UpgradesService(UpgradeRepository upgradeRepository) {
        this.upgradeRepository = upgradeRepository;
    }

    public void addUpgrade(UpgradeDTO upgradeDTO) {
       Upgrade upgrade = convertDtoToEntity(upgradeDTO);
        upgradeRepository.save(upgrade);
    }

    public void deleteUpgradeById(Integer id) {
        upgradeRepository.deleteById(id);
    }

    public void updateUpgrade(Integer id, UpgradeDTO upgradeDTO) {
        Upgrade upgrade = convertDtoToEntity(upgradeDTO);
        Upgrade existingUpgrade = upgradeRepository.findById(id).orElseThrow(() -> new RuntimeException("Upgrade not found"));
        existingUpgrade.setName(upgrade.getName());
        existingUpgrade.setPointsCost(upgrade.getPointsCost());
        existingUpgrade.setDescription(upgrade.getDescription());
        existingUpgrade.setUpgradeType(upgrade.getUpgradeType());
        upgradeRepository.save(existingUpgrade);
    }

    public Upgrade convertDtoToEntity(UpgradeDTO upgrade) {
        Upgrade newUpgrade = new Upgrade();
        newUpgrade.setName(upgrade.getName());
        newUpgrade.setPointsCost(upgrade.getPointsCost());
        newUpgrade.setUpgradeType(upgrade.getUpgradeType());
        newUpgrade.setPointsCost(upgrade.getPointsCost());
        return newUpgrade;
    }
}
