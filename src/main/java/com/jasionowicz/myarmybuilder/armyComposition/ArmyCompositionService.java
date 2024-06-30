package com.jasionowicz.myarmybuilder.armyComposition;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedService;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnits;
import com.jasionowicz.myarmybuilder.unit.Unit;
import com.jasionowicz.myarmybuilder.upgrade.Upgrade;
import org.springframework.transaction.annotation.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@Service
public class ArmyCompositionService {
    private final SelectedUnits selectedUnits;
    @Getter
    private final ArmyComposition armyComposition = new ArmyComposition();
    private SelectedService selectedService;

    @Autowired
    public ArmyCompositionService(SelectedUnits selectedUnits, SelectedService selectedService) {
        this.selectedUnits = selectedUnits;
        this.selectedService = selectedService;

    }


    @Transactional(readOnly = true)
    public double calculateTotalPoints() {
        List<Unit> selectedUnitsList = selectedService.getSelectedUnits();
        double totalPoints = 0.0;

        for (Unit unit : selectedUnitsList) {
            totalPoints += unit.getPointsCostPerUnit() * unit.getQuantity();

            if (unit.getUpgradesList() != null) {
                totalPoints += unit.getUpgradesList().stream()
                        .filter(Upgrade::isSelected)
                        .mapToDouble(upgrade -> {
                            if (upgrade.getQuantity() == 1) {
                                return upgrade.getPointsCost();
                            } else {
                                return upgrade.getPointsCost() * upgrade.getQuantity();
                            }
                        })
                        .sum();
            }
        }

        for (Unit unit : selectedUnitsList) {
            List<Upgrade> upgradesList = unit.getUpgradesList();
            if (upgradesList != null && unit.getId() < upgradesList.size()) {
                if (upgradesList.get(unit.getId()).isSelected()) {
                    totalPoints += calculateUpgradePoints(unit.getSelectedId());
                }
            }
        }

        return totalPoints;
    }

    private double calculateUpgradePoints(Integer selectedId) {
        double upgradePoints = 0;
        Optional<Unit> selectedUnit = Optional.ofNullable(selectedService.getBySelectedId(selectedId));
        if (selectedUnit.isPresent()) {
            for (Upgrade upgrade : selectedUnit.get().getUpgradesList()) {
                String unitType = selectedUnit.get().getUnitType();
                if (upgrade.isSelected()) {
                    if (upgrade.getQuantity() == 1) {
                        upgradePoints += upgrade.getPointsCost();
                    } else {
                        upgradePoints += upgrade.getPointsCost() * selectedUnit.get().getQuantity();
                    }
                }
            }
        }
        return upgradePoints;
    }


    public void resetPoints() {
        armyComposition.setTotalPoints(0);
        armyComposition.setTotalSpecial(0);
        armyComposition.setTotalLords(0);
        armyComposition.setTotalCore(0);
        armyComposition.setTotalHeroes(0);
        armyComposition.setTotalRare(0);
    }

    public Map<String, Double> calculateDedicatedPoints() {
        List<Unit> selectedUnitsList = selectedUnits.getSelectedUnits();

        Map<String, Double> pointsByType = new HashMap<>();

        for (Unit unit : selectedUnitsList) {
            String unitType = unit.getUnitType();
            double unitPoints = unit.getPointsCostPerUnit() * unit.getQuantity();
            pointsByType.put(unitType, pointsByType.getOrDefault(unitType, 0.0) + unitPoints);
        }


        return pointsByType;
    }
}
