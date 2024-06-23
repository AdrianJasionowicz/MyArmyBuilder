package com.jasionowicz.myarmybuilder;

import com.jasionowicz.myarmybuilder.armyComposition.ArmyComposition;
import com.jasionowicz.myarmybuilder.armyComposition.ArmyCompositionRepository;
import com.jasionowicz.myarmybuilder.armyComposition.ArmyCompositionService;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedService;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnits;
import com.jasionowicz.myarmybuilder.unit.Unit;
import com.jasionowicz.myarmybuilder.unit.UnitDTO;
import com.jasionowicz.myarmybuilder.unit.UnitRepository;
import com.jasionowicz.myarmybuilder.unit.UnitService;
import com.jasionowicz.myarmybuilder.upgrade.Upgrade;
import com.jasionowicz.myarmybuilder.upgrade.UpgradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class MenuController {

    private final SelectedService selectedService;
    private final UnitRepository unitRepository;
    private final UpgradesService upgradesService;
    private final ArmyCompositionRepository armyCompositionRepository;
    private final ArmyComposition armyComposition;
    private final ArmyCompositionService armyCompositionService;
    private final UnitService unitService;
    private final SelectedUnits selectedUnits;
    private double pointsRestriction = 0;

    @Autowired
    public MenuController(UnitRepository unitRepository, UpgradesService upgradesService, ArmyCompositionRepository armyCompositionRepository, ArmyComposition armyComposition, ArmyCompositionService armyCompositionService, UnitService unitService, SelectedUnits selectedUnits, SelectedService selectedService) {
        this.unitRepository = unitRepository;
        this.upgradesService = upgradesService;
        this.armyCompositionRepository = armyCompositionRepository;
        this.armyComposition = armyComposition;
        this.armyCompositionService = armyCompositionService;
        this.unitService = unitService;
        this.selectedUnits = selectedUnits;
        this.selectedService = selectedService;
    }

    @GetMapping("/menu")
    public String showMenu(Model model) {
        List<Unit> availableUnits = unitRepository.findAll();
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

        Map<String, Double> utilizedPointsByType = calculateUtilizedPointsByType(unitsByType);
        model.addAttribute("utilizedPointsByType", utilizedPointsByType);

        return "menu";
    }

    @PostMapping("/addUnit")
    public String addUnit(@RequestParam("unitId") Integer unitId) {
        Optional<Unit> unitOptional = unitRepository.findById(unitId);
        unitOptional.ifPresent(unit -> {
            UnitDTO unitDTO = new UnitDTO(unit);
            unitDTO.setSelectedId(selectedService.generateSelectedId());
            selectedService.addUnit(unitDTO);
        });

        return "redirect:/menu";
    }

    @PostMapping("/removeUnit")
    public String removeUnit(@RequestParam("selectedId") Integer selectedId) {
        selectedService.removeUnitById(selectedId);

        return "redirect:/menu";
    }

    @PostMapping("/increaseUnitQuantity")
    public String increaseUnitQuantity(@RequestParam("selectedId") Integer selectedId) {
        selectedService.increaseUnitQuantity(selectedId);

        return "redirect:/menu";
    }

    @PostMapping("/decreaseUnitQuantity")
    public String decreaseUnitQuantity(@RequestParam("selectedId") Integer selectedId) {
        selectedService.decreaseUnitQuantity(selectedId);

        return "redirect:/menu";
    }

    @GetMapping("/calculateTotalPoints")
    @ResponseBody
    public ArmyComposition calculateTotalPoints() {
        return armyComposition;
    }


@GetMapping("/units/{selectedId}/upgrades")
public ResponseEntity<List<Upgrade>> getUpgradesForUnit(@PathVariable Integer selectedId) {
    int unitId = selectedService.getIdBySelectedId(selectedId);
    List<Upgrade> upgrades = upgradesService.findUpgradesByUnitId(unitId);
    if (!upgrades.isEmpty()) {
        return ResponseEntity.ok(upgrades);
    } else {
        return ResponseEntity.notFound().build();
    }
}

    @PostMapping("/addUpgrade")
    public String toggleUpgradeSelection(@RequestParam Integer unitId, @RequestParam Integer upgradeId) {
        selectedService.addUpgrade(upgradeId);
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

    private Map<String, Double> calculatePointsLimitsByType() {
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

    private Map<String, Double> calculateUtilizedPointsByType(Map<String, List<Unit>> unitsByType) {
        Map<String, Double> utilizedPointsByType = new HashMap<>();

        unitsByType.forEach((type, units) -> {
            double utilizedPoints = selectedUnits.getSelectedUnits().stream()
                    .filter(unit -> unit.getUnitType().equals(type))
                    .mapToDouble(unit -> unit.getPointsCostPerUnit() * unit.getQuantity())
                    .sum();
            utilizedPointsByType.put(type, utilizedPoints);
        });

        return utilizedPointsByType;
    }

}
