package com.jasionowicz.myarmybuilder;

import com.jasionowicz.myarmybuilder.armyComposition.ArmyComposition;
import com.jasionowicz.myarmybuilder.armyComposition.ArmyCompositionService;
import com.jasionowicz.myarmybuilder.armyComposition.selectedStats.SelectedStatsRepository;
import com.jasionowicz.myarmybuilder.selectedUnits.*;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgrades;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradesDTO;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradesRepository;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradesService;
import com.jasionowicz.myarmybuilder.unit.Unit;
import com.jasionowicz.myarmybuilder.unit.UnitDTO;
import com.jasionowicz.myarmybuilder.unit.UnitRepository;
import com.jasionowicz.myarmybuilder.unit.UnitService;
import com.jasionowicz.myarmybuilder.upgrade.Upgrade;
import com.jasionowicz.myarmybuilder.upgrade.UpgradeDTO;
import com.jasionowicz.myarmybuilder.upgrade.UpgradeRepository;
import com.jasionowicz.myarmybuilder.upgrade.UpgradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.hibernate.Hibernate.map;

@Controller
public class MenuController {


    private final SelectedService selectedService;
    private final UnitRepository unitRepository;
    private final UpgradesService upgradesService;
    private final ArmyComposition armyComposition;
    private final ArmyCompositionService armyCompositionService;
    private final UnitService unitService;
    private final SelectedUnitRepository selectedUnitRepository;
    private final SelectedUpgradesRepository selectedUpgradesRepository;
    private SelectedUnitDTO selectedUnitDTO;
    private double pointsRestriction = 0;
    private final SelectedUnit selectedUnit;
    private SelectedUpgradesDTO selectedUpgradesDTO;
    private final SelectedUpgrades selectedUpgrades;
    @Autowired
    private SelectedStatsRepository selectedStatsRepository;
    @Autowired
    private UpgradeRepository upgradeRepository;
    @Autowired
    private UpgradeDTO upgradeDTO;
    @Autowired
    private SelectedUpgradesService selectedUpgradesService;

    @Autowired
    public MenuController(UnitRepository unitRepository, UpgradesService upgradesService, ArmyComposition armyComposition, ArmyCompositionService armyCompositionService, UnitService unitService, SelectedService selectedService, SelectedUnitRepository selectedUnitRepository, SelectedUpgradesRepository selectedUpgradesRepository, SelectedUnit selectedUnit, SelectedUpgradesDTO selectedUpgradesDTO, SelectedUpgrades selectedUpgrades) {
        this.unitRepository = unitRepository;
        this.upgradesService = upgradesService;
        this.armyComposition = armyComposition;
        this.armyCompositionService = armyCompositionService;
        this.unitService = unitService;
        this.selectedService = selectedService;
        this.selectedUnitRepository = selectedUnitRepository;
        this.selectedUpgradesRepository = selectedUpgradesRepository;
        this.selectedUnit = selectedUnit;
        this.selectedUpgradesDTO = selectedUpgradesDTO;
        this.selectedUpgrades = selectedUpgrades;
    }


    @GetMapping("/menu")
    public String showMenu(Model model) {
        List<Unit> availableUnits = unitRepository.findAll();

        List<SelectedUnit> selectedUnits = selectedService.getSelectedUnits();

        Map<String, List<SelectedUnit>> unitsByType = selectedUnits.stream().collect(Collectors.groupingBy(SelectedUnit::getUnitType));

        model.addAttribute("unitsByType", unitsByType);
        model.addAttribute("availableUnits", availableUnits);
        model.addAttribute("selectedUnits", selectedUnits);
        model.addAttribute("armyComposition", armyComposition);

        double totalPoints = armyCompositionService.calculateTotalPoints();
        model.addAttribute("totalPoints", totalPoints);

        Map<String, Double> dedicatedPoints = armyCompositionService.calculateDedicatedPoints();
        model.addAttribute("dedicatedPoints", dedicatedPoints);

        model.addAttribute("pointsRestriction", pointsRestriction);

        Map<String, Double> pointsLimitsByType = calculatePointsLimitsByType(pointsRestriction);
        model.addAttribute("pointsLimitsByType", pointsLimitsByType);

        Map<String, Double> utilizedPointsByType = calculateUtilizedPointsByType(unitsByType);
        model.addAttribute("utilizedPointsByType", utilizedPointsByType);

        return "menu";
    }


    @PostMapping("/addUnit")
    public String addUnit(@RequestParam("unitId") Integer unitId) {
        Optional<Unit> unitOptional = unitRepository.findById(unitId);

        if (unitOptional.isPresent()) {
            Unit unit = unitOptional.get();

            SelectedUnit selectedUnit = new SelectedUnit(unit);

            if (selectedUnit.getSelectedStats() != null) {
                selectedStatsRepository.save(selectedUnit.getSelectedStats());
            } else {
            }

            selectedUnitRepository.save(selectedUnit);

            List<SelectedUpgrades> selectedUpgradesList = selectedUnit.getSelectedUpgrades().stream().filter(Objects::nonNull).collect(Collectors.toList());
            if (!selectedUpgradesList.isEmpty()) {
                selectedUpgradesRepository.saveAll(selectedUpgradesList);
            }
        }

        return "redirect:/menu";
    }


    @PostMapping("/removeUnit")
    public String removeUnit(@RequestParam("id") Integer selectedId) {
        selectedService.removeUnitById(selectedId);

        return "redirect:/menu";
    }

    @PostMapping("/increaseUnitQuantity")
    public String increaseUnitQuantity(@RequestParam("id") Integer selectedId) {
        selectedService.increaseUnitQuantity(selectedId);

        return "redirect:/menu";
    }

    @PostMapping("/decreaseUnitQuantity")
    public String decreaseUnitQuantity(@RequestParam("id") Integer selectedId) {
        selectedService.decreaseUnitQuantity(selectedId);

        return "redirect:/menu";
    }


    @GetMapping("/calculateTotalPoints")
    @ResponseBody
    public ArmyComposition calculateTotalPoints() {
        return armyComposition;
    }


    @PostMapping("/addUpgrade")
    public ResponseEntity<String> addUpgrade(@RequestParam Integer unitId, @RequestParam Integer upgradeId) {
        try {
            List<SelectedUpgrades> upgrades = selectedUpgradesRepository.findAllBySelectedUnitId(unitId);
            if (upgrades != null && !upgrades.isEmpty()) {
                selectedUpgradesService.setSelectedUpgrades(unitId, upgradeId);
                double totalPoints = armyCompositionService.calculateTotalPoints();
                return ResponseEntity.ok("Upgrade added successfully. Total points: " + totalPoints);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding upgrade: " + e.getMessage());
        }
    }


    @PostMapping("/resetPoints")
    public String resetPoints() {
        armyCompositionService.resetPoints();
        return "redirect:/menu";
    }

    @PostMapping("/setPointsRestriction")
    public String setPoints(@RequestParam("points") double newPoints) {
        pointsRestriction = newPoints;
        return "redirect:/menu";
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
        unitsByType.forEach((type, Selectedunit) -> {
            double utilizedPoints = Selectedunit.stream().mapToDouble(selectedunit -> selectedunit.getUnit().getPointsCostPerUnit() * selectedunit.getQuantity()).sum();
            utilizedPointsByType.put(type, utilizedPoints);
        });
        return utilizedPointsByType;
    }


    @GetMapping("/units/{id}/upgrades")
    public ResponseEntity<List<SelectedUpgradesDTO>> getSelectedUpgrades(@PathVariable Integer id) {

        Optional<SelectedUnit> optionalSelectedUnit = selectedUnitRepository.findById(id);
        if (optionalSelectedUnit.isPresent()) {
            SelectedUnit selectedUnit = optionalSelectedUnit.get();
            List<SelectedUpgradesDTO> selectedUpgradesList = selectedUnit.getSelectedUpgrades().stream()
                    .map(SelectedUpgradesDTO::new)
                    .collect(Collectors.toList());

            System.out.println(selectedUpgradesList);
            return ResponseEntity.ok(selectedUpgradesList);

        } else {
            return ResponseEntity.notFound().build();

        }

    }
}
/*
 * Unit
 *
 * name
 * baseStats
 * type
 * startingUpgrades
 *
 * Armia
 * List<SelectedUnit>

 * SelectedUnit
 * unit
 * selectedUpgrades
 * */
