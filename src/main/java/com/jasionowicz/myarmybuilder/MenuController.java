package com.jasionowicz.myarmybuilder;

import com.jasionowicz.myarmybuilder.armyComposition.ArmyComposition;
import com.jasionowicz.myarmybuilder.armyComposition.ArmyCompositionRepository;
import com.jasionowicz.myarmybuilder.armyComposition.ArmyCompositionService;
import com.jasionowicz.myarmybuilder.unit.SelectedUnits;
import com.jasionowicz.myarmybuilder.unit.Unit;
import com.jasionowicz.myarmybuilder.unit.UnitRepository;
import com.jasionowicz.myarmybuilder.unit.UnitService;
import com.jasionowicz.myarmybuilder.upgrade.Upgrade;
import com.jasionowicz.myarmybuilder.upgrade.UpgradesService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MenuController {

    private final UnitRepository unitRepository;
    private final UpgradesService upgradesService;
    private final ArmyCompositionRepository armyCompositionRepository;
    private final List<Unit> availableUnits;
    private final ArmyComposition armyComposition;
    private final ArmyCompositionService armyCompositionService;
    private final UnitService unitService;
    private final SelectedUnits selectedUnits;
    private double pointsRestriction = 0;
    private double lordsLimit = 0;
    private double heroLimit = 0;
    private double coreLimit = 0;
    private double specialLimit = 0;
    private double rareLimit = 0;


    @Autowired
    public MenuController(UnitRepository unitRepository, UpgradesService upgradesService, ArmyCompositionRepository armyCompositionRepository, ArmyComposition armyComposition, ArmyCompositionService armyCompositionService, UnitService unitService, SelectedUnits selectedUnits) {
        this.unitRepository = unitRepository;
        this.upgradesService = upgradesService;
        this.availableUnits = unitRepository.findAll();
        this.armyComposition = armyComposition;
        this.armyCompositionService = armyCompositionService;
        this.armyCompositionRepository = armyCompositionRepository;
        this.unitService = unitService;
        this.selectedUnits = selectedUnits;
    }

    @GetMapping("/menu")
    public String showMenu(Model model) {
        Map<String, List<Unit>> unitsByType = availableUnits.stream()
                .collect(Collectors.groupingBy(Unit::getUnitType));

        model.addAttribute("unitsByType", unitsByType);
        model.addAttribute("availableUnits", availableUnits);
        model.addAttribute("selectedUnits", selectedUnits.getSelectedUnits());
        model.addAttribute("armyComposition", armyComposition);

        double totalPoints = armyCompositionService.calculateTotalPoints();
        model.addAttribute("totalPoints", totalPoints);

        Map<String, Double> dedicatedPoints = armyCompositionService.calculateDedicatedPoints();
        model.addAttribute("dedicatedPoints", dedicatedPoints);

        model.addAttribute("pointsRestriction", pointsRestriction);


        Map<String, Double> pointsLimitsByType = calculatePointsLimitsByType();
        model.addAttribute("pointsLimitsByType", pointsLimitsByType);

        Map<String, Double> utilizedPointsByType = new HashMap<>();
        for (String type : unitsByType.keySet()) {
            double utilizedPoints = selectedUnits.getSelectedUnits().stream()
                    .filter(unit -> unit.getUnitType().equals(type))
                    .mapToDouble(Unit::getPointsCostPerUnit)
                    .sum();
            utilizedPointsByType.put(type, utilizedPoints);
        }
        model.addAttribute("utilizedPointsByType", utilizedPointsByType);
        model.addAttribute("utilizedPointsByType", calculateUtilizedPointsByType());

        return "menu";
    }

    @PostMapping("/addUnit")
    public String addUnit(@RequestParam("unitId") Integer unitId) {
        Optional<Unit> unitOptional = unitRepository.findById(unitId);
        unitOptional.ifPresent(selectedUnits::addUnit);

        return "redirect:/menu";
    }

    public Map<String, Double> calculatePointsLimitsByType() {
        Map<String, Double> pointsLimitsByType = new HashMap<>();

        double lordsLimit = pointsRestriction * 0.5;
        pointsLimitsByType.put("Lords", lordsLimit);

        double heroLimit = pointsRestriction * 0.5;
        pointsLimitsByType.put("Hero", heroLimit);

        double coreLimit = pointsRestriction * 0.25;
        pointsLimitsByType.put("Core", coreLimit);

        double specialLimit = pointsRestriction * 0.5;
        pointsLimitsByType.put("Special", specialLimit);

        double rareLimit = pointsRestriction * 0.25;
        pointsLimitsByType.put("Rare", rareLimit);

        return pointsLimitsByType;
    }

    @PostMapping("/removeUnit")
    public String removeUnit(@RequestParam("unitId") Integer unitId) {
        selectedUnits.removeUnitById(unitId);

        return "redirect:/menu";
    }

    @PostMapping("/increaseUnitQuantity")
    public String increaseUnitQuantity(@RequestParam("unitId") Integer unitId) {
        selectedUnits.increaseUnitQuantity(unitId);

        return "redirect:/menu";
    }

    @PostMapping("/decreaseUnitQuantity")
    public String decreaseUnitQuantity(@RequestParam("unitId") Integer unitId) {
        selectedUnits.decreaseUnitQuantity(unitId);
        return "redirect:/menu";
    }

    @GetMapping("/calculateTotalPoints")
    @ResponseBody
    public ArmyComposition calculateTotalPoints() {
        return armyComposition;
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

    @PostMapping("/addUpgrade")
    public String addUpgrade(@RequestParam Integer unitId, @RequestParam Integer upgradeId) {
        unitService.getUpgrades(unitId, upgradeId);
        return "redirect:/menu";
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
    public Map<String, Double> calculateUtilizedPointsByType() {
        Map<String, Double> utilizedPointsByType = new HashMap<>();

        double totalLordsPoints = selectedUnits.getSelectedUnits().stream()
                .filter(unit -> unit.getUnitType().equals("Lords"))
                .mapToDouble(unit -> unit.getPointsCostPerUnit() * unit.getQuantity())
                .sum();
        utilizedPointsByType.put("Lords", totalLordsPoints);

        double totalHeroPoints = selectedUnits.getSelectedUnits().stream()
                .filter(unit -> unit.getUnitType().equals("Hero"))
                .mapToDouble(unit -> unit.getPointsCostPerUnit() * unit.getQuantity())
                .sum();
        utilizedPointsByType.put("Hero", totalHeroPoints);

        double totalCorePoints = selectedUnits.getSelectedUnits().stream()
                .filter(unit -> unit.getUnitType().equals("Core"))
                .mapToDouble(unit -> unit.getPointsCostPerUnit() * unit.getQuantity())
                .sum();
        utilizedPointsByType.put("Core", totalCorePoints);

        double totalSpecialPoints = selectedUnits.getSelectedUnits().stream()
                .filter(unit -> unit.getUnitType().equals("Special"))
                .mapToDouble(unit -> unit.getPointsCostPerUnit() * unit.getQuantity())
                .sum();
        utilizedPointsByType.put("Special", totalSpecialPoints);

        double totalRarePoints = selectedUnits.getSelectedUnits().stream()
                .filter(unit -> unit.getUnitType().equals("Rare"))
                .mapToDouble(unit -> unit.getPointsCostPerUnit() * unit.getQuantity())
                .sum();
        utilizedPointsByType.put("Rare", totalRarePoints);

        return utilizedPointsByType;
    }

}
