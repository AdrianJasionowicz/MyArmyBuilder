package com.jasionowicz.myarmybuilder;

import com.jasionowicz.myarmybuilder.armyComposition.ArmyComposition;
import com.jasionowicz.myarmybuilder.armyComposition.ArmyCompositionRepository;
import com.jasionowicz.myarmybuilder.armyComposition.ArmyCompositionService;
import com.jasionowicz.myarmybuilder.unit.Unit;
import com.jasionowicz.myarmybuilder.unit.UnitRepository;
import com.jasionowicz.myarmybuilder.unit.UnitService;
import com.jasionowicz.myarmybuilder.upgrade.Upgrade;
import com.jasionowicz.myarmybuilder.upgrade.UpgradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MenuController {

    private final UnitRepository unitRepository;
    private final UpgradesService upgradesService;
    private final ArmyCompositionRepository armyCompositionRepository;
    private final List<Unit> availableUnits;
    private final List<Unit> selectedUnits = new ArrayList<>();
    private final ArmyComposition armyComposition;
    private final ArmyCompositionService armyCompositionService;
    private final UnitService unitService;

    @Autowired
    public MenuController(UnitRepository unitRepository, UpgradesService upgradesService, ArmyCompositionRepository armyCompositionRepository, ArmyComposition armyComposition, ArmyCompositionService armyCompositionService, UnitService unitService) {
        this.unitRepository = unitRepository;
        this.upgradesService = upgradesService;
        this.availableUnits = unitRepository.findAll();
        this.armyComposition = armyComposition;
        this.armyCompositionService = armyCompositionService;
        this.armyCompositionRepository = armyCompositionRepository;
        this.unitService = unitService;
    }

    @GetMapping("/menu")
    public String showMenu(Model model) {
        Map<String, List<Unit>> unitsByType = availableUnits.stream()
                .collect(Collectors.groupingBy(Unit::getUnitType));

        model.addAttribute("unitsByType", unitsByType);
        model.addAttribute("availableUnits", availableUnits);
        model.addAttribute("selectedUnits", selectedUnits);
        model.addAttribute("armyCompositions", armyComposition);
        return "menu";
    }


    @PostMapping("/addUnit")
    public String addUnit(@RequestParam("unitId") Integer unitId) {
        Unit selectedUnit = availableUnits.stream()
                .filter(unit -> unit.getId().equals(unitId))
                .findFirst()
                .orElse(null);

        if (selectedUnit != null) {

            Unit unitToAdd = new Unit(selectedUnit.getName(), selectedUnit.getUnitType(), selectedUnit.getQuantity());
            unitToAdd.setId(selectedUnit.getId());
            unitToAdd.setPointsCostPerUnit(selectedUnit.getPointsCostPerUnit());
            selectedUnits.add(unitToAdd);

            armyCompositionService.addToCalculate(selectedUnit.getPointsCostPerUnit(), selectedUnit.getQuantity(), selectedUnit.getUnitType());
        }

        return "redirect:/menu";
    }

    @PostMapping("/removeUnit")
    public String removeUnit(@RequestParam("unitId") Integer unitId) {
        Unit unitToRemove = selectedUnits.stream()
                .filter(unit -> unit.getId().equals(unitId))
                .findFirst()
                .orElse(null);

        if (unitToRemove != null) {
            selectedUnits.remove(unitToRemove);
            armyCompositionService.addToCalculate(-unitToRemove.getPointsCostPerUnit(), -unitToRemove.getQuantity(), unitToRemove.getUnitType());
        }

        return "redirect:/menu";
    }

    @PostMapping("/setUnitQuantity")
    public String setUnitQuantity(@RequestParam("unitId") Integer unitId, @RequestParam("quantity") int quantity) {
        Unit selectedUnit = selectedUnits.stream()
                .filter(unit -> unit.getId().equals(unitId))
                .findFirst()
                .orElse(null);

        if (selectedUnit != null) {
            selectedUnit.setQuantity(quantity);
        }

        return "redirect:/menu";
    }

    @GetMapping("/units/{unitId}/upgrades")
    public ResponseEntity<List<Upgrade>> getUpgradesForUnit(@PathVariable Integer unitId) {
        List<Upgrade> upgrades = upgradesService.findUpgradesByUnitId(unitId);
        if (!upgrades.isEmpty()) {
            return ResponseEntity.ok(upgrades);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/increaseUnitQuantity")
    public String increaseUnitQuantity(@RequestParam("unitId") Integer unitId) {
        Unit selectedUnit = selectedUnits.stream()
                .filter(unit -> unit.getId().equals(unitId))
                .findFirst()
                .orElse(null);

        if (selectedUnit != null) {
            selectedUnit.setQuantity(selectedUnit.getQuantity() + 1);
            armyCompositionService.addToCalculate(selectedUnit.getPointsCostPerUnit(), 1, selectedUnit.getUnitType());
        }

        return "redirect:/menu";
    }

    @PostMapping("/decreaseUnitQuantity")
    public String decreaseUnitQuantity(@RequestParam("unitId") Integer unitId) {
        Unit selectedUnit = selectedUnits.stream()
                .filter(unit -> unit.getId().equals(unitId))
                .findFirst()
                .orElse(null);

        if (selectedUnit != null && selectedUnit.getQuantity() > 0) {
            selectedUnit.setQuantity(selectedUnit.getQuantity() - 1);

            armyCompositionService.addToCalculate(-selectedUnit.getPointsCostPerUnit(), -1, selectedUnit.getUnitType());
        }

        return "redirect:/menu";
    }

    @GetMapping("/calculateTotalPoints")
    @ResponseBody
    public ResponseEntity<Double> calculateTotalPoints() {
        double totalPoints = armyComposition.getTotalPoints();
        return ResponseEntity.ok(totalPoints);
    }

    @GetMapping("/calculateTotalLords")
    public ResponseEntity<Double> calculateTotalLords() {
        double totalLords = armyComposition.getTotalLords();

        return ResponseEntity.ok(totalLords);
    }

    @GetMapping("/showSelectedUnits")
    public String showSelectedUnits(Unit unit) {

        return selectedUnits.toString();
    }

    @PostMapping("/addUpgrade")
    public ResponseEntity<String> addUpgrade(@RequestParam Integer unitId, @RequestParam Integer upgradeId) {
        unitService.getUpgrades(unitId, upgradeId);
        return ResponseEntity.ok("Done");
    }
}
