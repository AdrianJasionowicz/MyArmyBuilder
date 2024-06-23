package com.jasionowicz.myarmybuilder.selectedUnits;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnits;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUpgrades;
import com.jasionowicz.myarmybuilder.unit.Unit;
import com.jasionowicz.myarmybuilder.unit.UnitDTO;
import com.jasionowicz.myarmybuilder.upgrade.Upgrade;
import com.jasionowicz.myarmybuilder.upgrade.UpgradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;

@Service
public class SelectedService {

    @Autowired
    private UpgradesService upgradeService;

    @Autowired
    private SelectedUnits selectedUnits;

    @Autowired
    private SelectedUpgrades selectedUpgrades;

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

    public void addUpgrade(Integer selectedId, Upgrade upgrade) {
        Unit unit = getBySelectedId(selectedId);
        if (unit != null) {
            selectedUpgrades.addUpgrade(upgrade, selectedId);
        }
    }

    public void removeUpgrade(Integer selectedId, Integer upgradeId) {
        Unit unit = getBySelectedId(selectedId);
        if (unit != null) {
            selectedUpgrades.removeUpgrade(upgradeId);
        }
    }

    public void toggleUpgrade(Integer selectedId, Integer upgradeId) {
        Unit unit = getBySelectedId(selectedId);
        if (unit != null) {
            Upgrade upgrade = findUpgradeById(upgradeId);
            if (upgrade != null) {
                if (selectedUpgrades.getSelectedUpgrades().contains(upgrade)) {
                    removeUpgrade(selectedId, upgradeId);
                } else {
                    addUpgrade(selectedId, upgrade);
                }
            }
        }
    }

    private Upgrade findUpgradeById(Integer upgradeId) {
        return selectedUpgrades.getSelectedUpgrades().stream()
                .filter(upgrade -> upgrade.getId().equals(upgradeId))
                .findFirst()
                .orElse(null);
    }
    public double calculatePointsForUpgrades(Integer selectedId) {
        long totalPoints = 0;
        long unitQuantity = (long) selectedUnits.getUnitQuantityBySelectedId(selectedId);

        for (Upgrade upgrade : selectedUpgrades.getSelectedUpgrades()) {
            long cost = (long) upgrade.getPointsCost();
            long quantity = upgrade.getQuantity();

            if (quantity == 0) {
                totalPoints += cost * unitQuantity; // Mnożenie kosztu przez ilość jednostek
            } else {
                totalPoints += cost;
            }
        }
        return totalPoints;
    }
    public void removeUpgradeFromUnit(Integer selectedUnitId, Integer upgradeId) {
        selectedUpgrades.removeUpgrade(upgradeId);
    }

}
