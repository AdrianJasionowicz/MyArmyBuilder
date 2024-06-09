package com.jasionowicz.myarmybuilder.unit;

import com.jasionowicz.myarmybuilder.armyComposition.ArmyCompositionRepository;
import com.jasionowicz.myarmybuilder.upgrade.Upgrade;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UnitService {
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private ArmyCompositionRepository armyCompositionRepository;

    private List<Unit> unitList = new ArrayList<>();


    public void saveUnitToList(Unit unit) {
        unitList.add(unit);
        System.out.println("Adding unit to list" + unit.getName());
    }

    public Optional findById(Integer id) {
        return unitRepository.findById(id);
    }

    public Unit updateUnit(Integer id, Unit updatedUnit) {
        return unitRepository.findById(id).map(unit -> {
            unit.setName(updatedUnit.getName());
            unit.setPointsCostPerUnit(updatedUnit.getPointsCostPerUnit());
            unit.setQuantity(updatedUnit.getQuantity());
            unit.setUnitType(updatedUnit.getUnitType());
            return unitRepository.save(unit);
        }).orElseThrow(() -> new RuntimeException("Unit not found"));
    }

    public void deleteById(Integer id) {
        unitRepository.deleteById(id);
    }

    public Unit save(Unit unit) {
        return unitRepository.save(unit);
    }

    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    @Transactional
    public void getUpgrades(Integer unitId, Integer upgradeId) {
        List<Upgrade> upgrades = unitRepository.getReferenceById(unitId).getUpgradesList();
        for (Upgrade upgrade : upgrades) {
            if (upgrade.getUnitUpgradesId().equals(upgradeId)) {
                upgrade.setSelected(!upgrade.isSelected());
                break;
            }
            unitRepository.save(unitRepository.getReferenceById(unitId));
        }
    }

}
