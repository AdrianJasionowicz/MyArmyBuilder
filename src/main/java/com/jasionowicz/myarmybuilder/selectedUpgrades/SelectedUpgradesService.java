package com.jasionowicz.myarmybuilder.selectedUpgrades;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectedUpgradesService {
    private SelectedUpgradesRepository selectedUpgradesRepository;
    private SelectedUpgrades selectedUpgrades;
    @Autowired
    private SelectedUpgradesDTO selectedUpgradesDTO;

    @PostConstruct
    public void clear() {
        selectedUpgradesRepository.deleteAll();
    }

    public SelectedUpgradesService(SelectedUpgradesRepository selectedUpgradesRepository, SelectedUpgrades selectedUpgrades) {
        this.selectedUpgradesRepository = selectedUpgradesRepository;
        this.selectedUpgrades = selectedUpgrades;
    }


    public List<SelectedUpgrades> getUnitUpgradesById(Integer unitId) {
        return selectedUpgradesRepository.findAllBySelectedUnitId(unitId);
    }


    public void setSelectedUpgrades(int selectedUnitId, int selectedUpgradeId) {
        List<SelectedUpgrades> upgrades = selectedUpgradesRepository.findAllBySelectedUnitId(selectedUnitId);
        for (SelectedUpgrades upgrade : upgrades) {
            if (upgrade.getUpgrade().getId().equals(selectedUpgradeId)) {
                upgrade.setSelected(true);
                if (upgrade.getUpgrade().getUpgradeType().equals("SingleBuy")) {
                    upgrade.setQuantity(1);

                }
            }
            selectedUpgradesRepository.save(upgrade);
        }

    }

    public boolean checkChieftainStandardBanner(Integer selectedId) {
        List<SelectedUpgrades> checkUpgrades = selectedUpgradesRepository.findAllBySelectedUnitId(selectedId);
        for (SelectedUpgrades checkUpgrade : checkUpgrades) {
            if (checkUpgrade.isSelected() && "Standard Banner".equals(checkUpgrade.getUpgrade().getUpgradeType())) {
                return true;
            }
        }
        return false;
    }

    public ResponseEntity<String> checkHeroUpgrades(Integer selectedId) {
        List<SelectedUpgrades> checkUpgrades = selectedUpgradesRepository.findAllBySelectedUnitId(selectedId);
        double upgradeLimit = 0;
        for (SelectedUpgrades checkUpgrade : checkUpgrades) {
            if (checkUpgrade.isSelected() && "Magic Weapon".equals(checkUpgrade.getUpgrade().getUpgradeType())) {
                upgradeLimit += checkUpgrade.getUpgrade().getPointsCost();
            }
        }
        if (upgradeLimit > 100) {
            return ResponseEntity.badRequest().body("Upgrade limit exceeded");
        }
        return ResponseEntity.ok("Done");
    }

    public boolean checkWeaponTeams(Integer selectedId) {
        List<SelectedUpgrades> selectedUpgrades = selectedUpgradesRepository.findAllBySelectedUnitId(selectedId);
        for (SelectedUpgrades selectedUpgrade : selectedUpgrades) {
            if (selectedUpgrade.isSelected() && "Weapon team".equals(selectedUpgrade.getUpgrade().getUpgradeType())) {
                return true;
            }
        }
        return false;
    }

    public ResponseEntity<String> checkLordsUpgrades(Integer selectedId) {
        List<SelectedUpgrades> upgradeList = selectedUpgradesRepository.findAllBySelectedUnitId(selectedId);
        double upgradeLimit = 0;
        for (SelectedUpgrades upgrade : upgradeList) {
            if (upgrade.isSelected() && "Magic weapon".equals(upgrade.getUpgrade().getUpgradeType())) {
                upgradeLimit += upgrade.getUpgrade().getPointsCost();
            }
        }
        if (upgradeLimit > 100) {
            return ResponseEntity.badRequest().body("Upgrade limit exceeded");
        }
        return ResponseEntity.ok("Done");
    }

}
