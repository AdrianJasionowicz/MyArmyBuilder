package com.jasionowicz.myarmybuilder.upgrade;

import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgrade;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpgradesService {

    private final UpgradeRepository upgradeRepository;
    private SelectedUpgradeRepository selectedUpgradeRepository;

    public UpgradesService(UpgradeRepository upgradeRepository) {
        this.upgradeRepository = upgradeRepository;
    }

    public void addUpgrade(Upgrade upgrade) {
        upgradeRepository.save(upgrade);
    }

    public void deleteUpgradeById(Integer id) {
        upgradeRepository.deleteById(id);
    }

    public Upgrade getUpgradeById(Integer id) {
        return upgradeRepository.findById(id).orElse(null);
    }

    public void updateUpgrade(Integer id, Upgrade upgrade) {
        Upgrade existingUpgrade = upgradeRepository.findById(id).orElseThrow(() -> new RuntimeException("Upgrade not found"));
        existingUpgrade.setName(upgrade.getName());
        existingUpgrade.setPointsCost(upgrade.getPointsCost());
        upgradeRepository.save(existingUpgrade);
    }

}
