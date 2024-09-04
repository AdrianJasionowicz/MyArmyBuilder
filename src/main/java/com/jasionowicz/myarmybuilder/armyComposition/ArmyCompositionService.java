package com.jasionowicz.myarmybuilder.armyComposition;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedService;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnitRepository;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgrade;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SelectedService selectedService;

    @Autowired
    public ArmyCompositionService(SelectedUnit selectedUnit, SelectedService selectedService, SelectedUnitRepository selectedUnitRepository, SelectedUpgradeRepository selectedUpgradeRepository, ArmyCompositionRepository armyCompositionRepository) {
        this.selectedUnit = selectedUnit;
        this.selectedService = selectedService;
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

        for (SelectedUpgrade selectedUpgrade : selectedUpgradeList) {
            if (selectedUpgrade.isSelected()) {
                String unitType = selectedUpgrade.getSelectedUnit().getUnitType();
                double upgradePoints = selectedUpgrade.getUpgrade().getPointsCost() * selectedUpgrade.getQuantity();
                pointsByType.put(unitType, pointsByType.getOrDefault(unitType, 0.0) + upgradePoints);
            }
        }
        return pointsByType;
    }

    @Transactional
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

}
