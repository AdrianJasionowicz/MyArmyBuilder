package com.jasionowicz.myarmybuilder.selectedUpgrades;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnitRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SelectedUpgradeService {
    private SelectedUpgradeRepository selectedUpgradeRepository;
    private SelectedUpgrade selectedUpgrade;
    @Autowired
    private SelectedUpgradeDTO selectedUpgradeDTO;
    @Autowired
    private SelectedUnitRepository selectedUnitRepository;

    @PostConstruct
    public void clear() {
        selectedUpgradeRepository.deleteAll();
    }

    public SelectedUpgradeService(SelectedUpgradeRepository selectedUpgradeRepository, SelectedUpgrade selectedUpgrade) {
        this.selectedUpgradeRepository = selectedUpgradeRepository;
        this.selectedUpgrade = selectedUpgrade;
    }


    public List<SelectedUpgrade> getUnitUpgradesById(Integer unitId) {
        return selectedUpgradeRepository.findAllBySelectedUnitId(unitId);
    }


    public void setSelectedUpgrades(int selectedUnitId, int selectedUpgradeId) {
        List<SelectedUpgrade> upgrades = selectedUpgradeRepository.findAllBySelectedUnitId(selectedUnitId);
        for (SelectedUpgrade upgrade : upgrades) {
            if (upgrade.getUpgrade().getId().equals(selectedUpgradeId)) {
                upgrade.setSelected(true);
                if (upgrade.getUpgrade().getUpgradeType().equals("SingleBuy")) {
                    upgrade.setQuantity(1);

                }
            }
            selectedUpgradeRepository.save(upgrade);
        }

    }

    public boolean checkChieftainStandardBanner(Integer selectedId) {
        List<SelectedUpgrade> checkUpgrades = selectedUpgradeRepository.findAllBySelectedUnitId(selectedId);
        for (SelectedUpgrade checkUpgrade : checkUpgrades) {
            if (checkUpgrade.isSelected() && "Standard Banner".equals(checkUpgrade.getUpgrade().getUpgradeType())) {
                return true;
            }
        }
        return false;
    }

    public ResponseEntity<String> checkHeroUpgrades(Integer selectedId) {
        List<SelectedUpgrade> checkUpgrades = selectedUpgradeRepository.findAllBySelectedUnitId(selectedId);
        double upgradeLimit = 0;
        for (SelectedUpgrade checkUpgrade : checkUpgrades) {
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
        List<SelectedUpgrade> selectedUpgrades = selectedUpgradeRepository.findAllBySelectedUnitId(selectedId);

        for (SelectedUpgrade selectedUpgrade : selectedUpgrades) {
            if (selectedUpgrade.isSelected()) {

                if (selectedUpgrade.getUpgrade().getUpgradeType().equals("Weapon Team")) {
                    return true;
                }

            }

        }
        return false;
    }

    public ResponseEntity<String> checkLordsUpgrades(Integer selectedId) {
        Optional<SelectedUnit> optionalSelectedUnit = selectedUnitRepository.findById(selectedId);
        if (optionalSelectedUnit.isPresent()) {
            SelectedUnit selectedUnit = optionalSelectedUnit.get();
            String isSelectedUnitALord = selectedUnit.getUnitType();

            if (isSelectedUnitALord.equals("Lords")) {
                List<SelectedUpgrade> upgradeList = selectedUpgradeRepository.findAllBySelectedUnitId(selectedId);
                double upgradeLimit = 0;
                for (SelectedUpgrade upgrade : upgradeList) {
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

        return ResponseEntity.ok().body("Done");
    }

    public void checkAmmountOfStandardBannersInArmy() {
        int bsbsInArmy = 0;
        List<SelectedUpgrade> selectedUpgradeList = selectedUpgradeRepository.findAll();
        for (SelectedUpgrade upgrade : selectedUpgradeList) {
            if (upgrade.isSelected() && upgrade.getUpgrade().getUpgradeType().equals("Standard Banner")) {
                bsbsInArmy++;
            }
        }
        if (bsbsInArmy > 1) {
            throw new RuntimeException("Army can hold only one bsb");
        }
    }


    public void checkUpgradesQuantities(Integer id, int quantity) {
       List<SelectedUpgrade> selectedUpgradeList =  selectedUpgradeRepository.findAllBySelectedUnitId(id);
        for (SelectedUpgrade upgrade : selectedUpgradeList) {
            if (upgrade.isSelected() && upgrade.getUpgrade().getUpgradeType().equals("Weapon")) {
                upgrade.setQuantity(quantity);
                selectedUpgradeRepository.save(upgrade);
            }
        }
    }
}