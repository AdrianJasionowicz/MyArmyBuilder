package com.jasionowicz.myarmybuilder.armyComposition;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnitService;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnitRepository;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgrade;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ArmyCompositionService {

    private final SelectedUnit selectedUnit;
    @Getter
    private final ArmyComposition armyComposition = new ArmyComposition();
    private final SelectedUnitRepository selectedUnitRepository;
    private final SelectedUpgradeRepository selectedUpgradeRepository;
    private final ArmyCompositionRepository armyCompositionRepository;
    private SelectedUnitService selectedUnitService;

    @Autowired
    public ArmyCompositionService(SelectedUnit selectedUnit, SelectedUnitService selectedUnitService, SelectedUnitRepository selectedUnitRepository, SelectedUpgradeRepository selectedUpgradeRepository, ArmyCompositionRepository armyCompositionRepository) {
        this.selectedUnit = selectedUnit;
        this.selectedUnitService = selectedUnitService;
        this.selectedUnitRepository = selectedUnitRepository;
        this.selectedUpgradeRepository = selectedUpgradeRepository;
        this.armyCompositionRepository = armyCompositionRepository;
    }


    @Transactional(readOnly = true)
    public double calculateTotalPoints() {
        List<SelectedUnit> selectedUnitsList = selectedUnitRepository.findAll();
        List<SelectedUpgrade> selectedUpgrades = selectedUpgradeRepository.findAll();

        double totalPoints = 0.0;
        for (SelectedUpgrade selectedUpgrade : selectedUpgrades) {
            if (selectedUpgrade.isSelected()) {
                totalPoints += selectedUpgrade.getUpgrade().getPointsCost() * selectedUpgrade.getQuantity();
            }
        }
        for (SelectedUnit units : selectedUnitsList) {
            totalPoints += units.getQuantity() * units.getUnit().getPointsCostPerUnit();
        }


        return totalPoints;
    }


    public Map<String, Double> calculateDedicatedPoints() {
        List<SelectedUnit> selectedUnitsList = selectedUnitRepository.findAll();
        List<SelectedUpgrade> selectedUpgradeList = selectedUpgradeRepository.findAll();

        Map<String, Double> pointsByType = new HashMap<>();


        for (SelectedUnit selectedUnit : selectedUnitsList) {
            String unitType = selectedUnit.getUnit().getUnitType();
            double unitPoints = selectedUnit.getUnit().getPointsCostPerUnit() * selectedUnit.getQuantity();

            pointsByType.put(unitType, pointsByType.getOrDefault(unitType, 0.0) + unitPoints);
        }
        System.out.println(pointsByType);
        for (SelectedUpgrade selectedUpgrade : selectedUpgradeList) {
            if (selectedUpgrade.isSelected()) {
                String unitType = selectedUpgrade.getSelectedUnit().getUnitType();
                double upgradePoints = selectedUpgrade.getUpgrade().getPointsCost() * selectedUpgrade.getQuantity();
                pointsByType.put(unitType, pointsByType.getOrDefault(unitType, 0.0) + upgradePoints);
            }
        }
        System.out.println(pointsByType);
        return pointsByType;
    }
    public Map<String, Double> calculatePointsLimitsByType(double pointsRestriction) {
        Map<String, Double> pointsLimitsByType = new HashMap<>();
        pointsLimitsByType.put("Lords", pointsRestriction * 0.5);
        pointsLimitsByType.put("Hero", pointsRestriction * 0.5);
        pointsLimitsByType.put("Core", pointsRestriction * 0.25);
        pointsLimitsByType.put("Special", pointsRestriction * 0.5);
        pointsLimitsByType.put("Rare", pointsRestriction * 0.25);
        return pointsLimitsByType;
    }

    public Map<String, Double> calculateUtilizedPointsByType(Map<String, List<SelectedUnit>> unitsByType) {
        Map<String, Double> utilizedPointsByType = new HashMap<>();
        utilizedPointsByType = calculateDedicatedPoints();
        return utilizedPointsByType;
    }

    public void addNewArmyTemplate(List<SelectedUnit> selectedUnits) {
        ArmyComposition armyComposition = new ArmyComposition();
        armyComposition.setSelectedUnitList(selectedUnits);
        armyCompositionRepository.save(armyComposition);
    }

    public void loadExistingArmyTemplate(int armyId) {
        List<SelectedUnit> selectedUnitList = new ArrayList<>();

        ArmyComposition armyComposition = armyCompositionRepository.getReferenceById(armyId);
        for (SelectedUnit unit : selectedUnitList) {
            selectedUnitList.add(unit);
        }



    }

    public void removeTemplate(int id) {
        armyCompositionRepository.deleteById(id);
    }

    public void updateArmyTemplate(ArmyComposition armyComposition) {
       int id = armyComposition.getId();
       armyCompositionRepository.getReferenceById(id);
    }

    public ResponseEntity<String> saveSelectedUnitsList() {
     List<SelectedUnit> selectedUnitList = selectedUnitRepository.findAll();
        addNewArmyTemplate(selectedUnitList);
        if (selectedUnitList.isEmpty()) {
            return ResponseEntity.badRequest().body("Army composition is empty");
        }
        return ResponseEntity.ok().body("Army Composition saved");
    }
}
