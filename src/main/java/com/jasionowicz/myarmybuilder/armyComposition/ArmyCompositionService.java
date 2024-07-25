package com.jasionowicz.myarmybuilder.armyComposition;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedService;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnitRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Service
public class ArmyCompositionService {
    private final SelectedUnit selectedUnit;
    @Getter
    private final ArmyComposition armyComposition = new ArmyComposition();
    private final SelectedUnitRepository selectedUnitRepository;
    private SelectedService selectedService;

    @Autowired
    public ArmyCompositionService(SelectedUnit selectedUnit, SelectedService selectedService, SelectedUnitRepository selectedUnitRepository) {
        this.selectedUnit = selectedUnit;
        this.selectedService = selectedService;
        this.selectedUnitRepository = selectedUnitRepository;
    }


    @Transactional(readOnly = true)
    public double calculateTotalPoints() {
        List<SelectedUnit> selectedUnitsList = selectedUnitRepository.findAll();
        double totalPoints = 0.0;
        for (SelectedUnit units : selectedUnitsList) {
          totalPoints +=   units.getQuantity() * units.getUnit().getPointsCostPerUnit();
        }



        return totalPoints;
    }



//    private double calculateUpgradePoints(Integer id) {
//        double upgradePoints = 0;
//        Optional<SelectedUnit> selectedUnit = selectedUnitRepository.findById(id);
//        if (selectedUnit.isPresent()) {
//            SelectedUnit selectedUnitz = selectedUnit.get();
//            for (SelectedUpgrades selectedUpgrades : selectedUnit.get().getSelectedUpgrades()) {
//                if (selectedUpgrades.isSelected()) {
//                    if (selectedUpgrades.getQuantity() == 1) {
//                        upgradePoints += selectedUpgrades.getPointsCost();
//                    } else {
//                        upgradePoints += selectedUpgrades.getPointsCost() * selectedUnitz.getQuantity();
//                    }
//                }
//            }
//        }
//        return upgradePoints;
//    }



    public void resetPoints() {
        armyComposition.setTotalPoints(0);
        armyComposition.setTotalSpecial(0);
        armyComposition.setTotalLords(0);
        armyComposition.setTotalCore(0);
        armyComposition.setTotalHeroes(0);
        armyComposition.setTotalRare(0);
    }

    public Map<String, Double> calculateDedicatedPoints() {
        List<SelectedUnit> selectedUnitsList = selectedUnitRepository.findAll();

        Map<String, Double> pointsByType = new HashMap<>();

        for (SelectedUnit selectedUnit : selectedUnitsList) {
            String unitType = selectedUnit.getUnit().getUnitType();
            double unitPoints = selectedUnit.getUnit().getPointsCostPerUnit() * selectedUnit.getQuantity();

            pointsByType.put(unitType, pointsByType.getOrDefault(unitType, 0.0) + unitPoints);
        }

        return pointsByType;
    }


}
