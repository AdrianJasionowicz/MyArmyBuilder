package com.jasionowicz.myarmybuilder.armyComposition;

import com.jasionowicz.myarmybuilder.unit.SelectedUnits;
import com.jasionowicz.myarmybuilder.unit.Unit;
import com.jasionowicz.myarmybuilder.upgrade.Upgrade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Service
public class ArmyCompositionService {
    private final SelectedUnits selectedUnits;
    @Getter
    private final ArmyComposition armyComposition = new ArmyComposition();

    public ArmyCompositionService(SelectedUnits selectedUnits) {
        this.selectedUnits = selectedUnits;
    }

    public void addToCalculate(double points, int quantity, String unitType) {
        double totalPointsForUnit = points * quantity;

        switch (unitType) {
            case "Lord":
                armyComposition.setTotalLords(armyComposition.getTotalLords() + totalPointsForUnit);
                break;
            case "Hero":
                armyComposition.setTotalHeroes(armyComposition.getTotalHeroes() + totalPointsForUnit);
                break;
            case "Core":
                armyComposition.setTotalCore(armyComposition.getTotalCore() + totalPointsForUnit);
                break;
            case "Special":
                armyComposition.setTotalSpecial(armyComposition.getTotalSpecial() + totalPointsForUnit);
                break;
            case "Rare":
                armyComposition.setTotalRare(armyComposition.getTotalRare() + totalPointsForUnit);
                break;
        }

        armyComposition.setTotalPoints(armyComposition.getTotalPoints() + totalPointsForUnit);
    }


    public double calculateTotalPoints() {
        double totalPoints = 0;
        for (Unit unit : selectedUnits.getSelectedUnits()) {
            totalPoints += unit.getPointsCostPerUnit() * unit.getQuantity();
        }
        return totalPoints;
    }

    private double calculateUpgradePoints(Unit unit) {
        double upgradePoints = 0;
        for (Upgrade upgrade : unit.getUpgradesList()) {
            if (upgrade.isSelected()) {
                if (upgrade.getQuantity() == 1) {
                    upgradePoints += upgrade.getPointsCost();
                } else {
                    upgradePoints += upgrade.getPointsCost() * unit.getQuantity();
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

        // Obliczanie sumy punktów dla każdego typu jednostki
        for (Unit unit : selectedUnitsList) {
            String unitType = unit.getUnitType();
            double unitPoints = unit.getPointsCostPerUnit() * unit.getQuantity();
            pointsByType.put(unitType, pointsByType.getOrDefault(unitType, 0.0) + unitPoints);
        }

        return pointsByType;
    }
}
