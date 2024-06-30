package com.jasionowicz.myarmybuilder.selectedUnits;

import com.jasionowicz.myarmybuilder.unit.Unit;
import com.jasionowicz.myarmybuilder.unit.UnitDTO;
import com.jasionowicz.myarmybuilder.upgrade.Upgrade;
import com.jasionowicz.myarmybuilder.upgrade.UpgradeDTO;
import com.jasionowicz.myarmybuilder.upgrade.UpgradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SelectedService {

    @Autowired
    private UpgradesService upgradeService;

    @Autowired
    private SelectedUnits selectedUnits;

    @Autowired
    private UpgradeDTO upgradeDTO;

    public SelectedService() {
    }

    public Integer generateSelectedId() {
        return selectedUnits.getSelectedUnits().size() + 1;
    }


    public ResponseEntity<UnitDTO> addUnit(UnitDTO unitDTO) {
        int selectedId = generateSelectedId();
        unitDTO.setSelectedId(selectedId);
        unitDTO.getUnit().setSelectedId(selectedId);
        selectedUnits.addUnit(unitDTO.getUnit());
        return ResponseEntity.ok(unitDTO);
    }

    public ResponseEntity<Unit> removeUnitById(int selectedId) {
        Unit unitToRemove = getBySelectedId(selectedId);
        if (unitToRemove != null) {
            selectedUnits.removeUnitBySelectedId(unitToRemove.getSelectedId());
            return ResponseEntity.ok(unitToRemove);
        }
        return ResponseEntity.badRequest().body(null);
    }

    public Unit getBySelectedId(int selectedId) {
        return selectedUnits.getSelectedUnits().stream()
                .filter(unit -> unit.getSelectedId() != null && unit.getSelectedId().equals(selectedId))
                .findFirst()
                .orElse(null);
    }

    public int getIdBySelectedId(int selectedId) {
        Optional<Unit> unit = selectedUnits.findBySelectedId(selectedId);
        return unit.map(Unit::getId).orElseThrow(() -> new IllegalArgumentException("No unit found for selectedId: " + selectedId));
    }

    public ResponseEntity<String> increaseUnitQuantity(Integer selectedId) {
        if (selectedId == null) {
            return ResponseEntity.badRequest().body("Selected ID is null");
        }
        Unit selectedUnit = getBySelectedId(selectedId);
        if (selectedUnit != null) {
            selectedUnit.setQuantity(selectedUnit.getQuantity() + 1);
            return ResponseEntity.ok("Quantity increased");
        }
        return ResponseEntity.badRequest().body("Unit not found");
    }

    public ResponseEntity<String> decreaseUnitQuantity(Integer selectedId) {
        if (selectedId == null) {
            return ResponseEntity.badRequest().body("Selected ID is null");
        }
        Unit selectedUnit = getBySelectedId(selectedId);
        if (selectedUnit != null && selectedUnit.getQuantity() > 0) {
            selectedUnit.setQuantity(selectedUnit.getQuantity() - 1);
            return ResponseEntity.ok("Quantity decreased");
        }
        return ResponseEntity.badRequest().body("Unit not found or quantity is zero");
    }

    public ResponseEntity<?> addUpgrade(Integer selectedId, Upgrade upgrade) {
        Optional<Unit> optionalUnit = selectedUnits.findBySelectedId(selectedId);
        if (optionalUnit.isPresent()) {
            Unit unit = optionalUnit.get();
            List<Upgrade> upgrades = unit.getUpgradesList();


            boolean isWeaponTeamThere = false;
            if (checkWeaponTeams(selectedId)) {
                isWeaponTeamThere = true;
                return ResponseEntity.badRequest().body("Cannot take more than one weapon team");
            }

            boolean upgradeExists = false;
            for (Upgrade existingUpgrade : upgrades) {
                if (existingUpgrade.getId().equals(upgrade.getId())) {
                    existingUpgrade.setSelected(true);
                    upgradeExists = true;
                    break;
                }
            }
            if (!upgradeExists) {
                upgrade.setSelected(true);
                upgrades.add(upgrade);
            }

            return ResponseEntity.ok("Upgrade added successfully");
        } else {
            System.out.println("Unit not found for selectedId: " + selectedId);
            return ResponseEntity.notFound().build();
        }
    }


    public void removeUpgrade(Integer selectedId, Integer upgradeId) {
        Optional<Unit> optionalUnit = selectedUnits.findBySelectedId(selectedId);
        if (optionalUnit.isPresent()) {
            Unit unit = optionalUnit.get();
            List<Upgrade> upgrades = unit.getUpgradesList();
            for (Upgrade existingUpgrade : upgrades) {
                if (existingUpgrade.getId().equals(upgradeId)) {
                    existingUpgrade.setSelected(false);
                    break;
                }
            }
        } else {
            System.out.println("Unit not found for selectedId: " + selectedId);
        }
    }

    public List<Unit> getSelectedUnits() {
        return selectedUnits.getSelectedUnits();
    }

    public boolean checkWeaponTeams(Integer selectedId) {
        List<Upgrade> getUpgrades = selectedUnits.upgradesList(selectedId);
        for (Upgrade getUpgrade : getUpgrades) {
            if (getUpgrade.isSelected() && getUpgrade.getUpgradeType().equals("Weapon team")) {
                return true;
            }
        }
        return false;
    }

    public ResponseEntity checkLordsUpgrades(Integer selectedId) {
        List<Upgrade> upgradeList = selectedUnits.upgradesList(selectedId);
        double upgradeLimit = 0;
        for (Upgrade upgrade : upgradeList) {
            if (upgrade.isSelected() && upgrade.getUpgradeType().equals("Magic weapon")) {
                upgradeLimit += upgrade.getPointsCost();
            }

        }
        if (upgradeLimit > 100) {
            return ResponseEntity.badRequest().body("Upgrade limit exceeded");
        }
        return ResponseEntity.ok("Done");
    }

    public boolean checkChieftainStandardBanner(Integer selectedId) {
        List<Upgrade> checkUpgrades = selectedUnits.upgradesList(selectedId);
        boolean standardBannerfound = false;
        for (Upgrade checkUpgrade : checkUpgrades) {
            if (checkUpgrade.isSelected() && checkUpgrade.getUpgradeType().equals("Standard Banner")) {
                standardBannerfound = true;
                return standardBannerfound;
            }
        }
        return standardBannerfound;
    }

    public ResponseEntity checkHeroUpgrades(Integer selectedId) {
        List<Upgrade> checkUpgrades = selectedUnits.upgradesList(selectedId);
        double upgradeLimit = 0;
        for (Upgrade checkUpgrade : checkUpgrades) {
            if (checkUpgrade.isSelected() && checkUpgrade.getUpgradeType().equals("Magic Weapon")) {
                upgradeLimit += checkUpgrade.getPointsCost();
            }
        }
        if (upgradeLimit > 100) {
            return ResponseEntity.badRequest().body("Upgrade limit exceeded");
        }
        return ResponseEntity.ok("Done");
    }


}
