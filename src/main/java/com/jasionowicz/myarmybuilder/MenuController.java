package com.jasionowicz.myarmybuilder;

import com.jasionowicz.myarmybuilder.armyComposition.ArmyCompositionService;
import com.jasionowicz.myarmybuilder.selectedStats.SelectedStatsRepository;
import com.jasionowicz.myarmybuilder.selectedUnitView.SelectedUnitView;
import com.jasionowicz.myarmybuilder.selectedUnitView.SelectedUnitViewService;
import com.jasionowicz.myarmybuilder.selectedUnits.*;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgrade;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeDTO;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeRepository;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeService;
import com.jasionowicz.myarmybuilder.unit.UnitDTO;
import com.jasionowicz.myarmybuilder.unit.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MenuController {


    private final SelectedUnitService selectedUnitService;
    private final ArmyCompositionService armyCompositionService;
    private final UnitService unitService;
    private final SelectedUnitRepository selectedUnitRepository;   // TO USUNAC
    private final SelectedUpgradeRepository selectedUpgradeRepository;   // TO USUNAC
    private double pointsRestriction = 0;
    @Autowired
    private SelectedUpgradeService selectedUpgradeService;
    @Autowired
    private SelectedUnitViewService selectedUnitViewService;

    @Autowired
    public MenuController(ArmyCompositionService armyCompositionService, UnitService unitService, SelectedUnitService selectedUnitService, SelectedUnitRepository selectedUnitRepository, SelectedUpgradeRepository selectedUpgradeRepository, SelectedUnit selectedUnit, SelectedUpgradeDTO selectedUpgradeDTO, SelectedUpgrade selectedUpgrade) {
        this.armyCompositionService = armyCompositionService;
        this.unitService = unitService;
        this.selectedUnitService = selectedUnitService;
        this.selectedUnitRepository = selectedUnitRepository;
        this.selectedUpgradeRepository = selectedUpgradeRepository;
    }


    @GetMapping("/menu")
    public String showMenu(Model model) {
        List<UnitDTO> availableUnits = unitService.getUnitDtoList();
        List<SelectedUnit> selectedUnits = selectedUnitService.getSelectedUnits();
        Map<String, List<SelectedUnit>> unitsByType = selectedUnits.stream()
                .collect(Collectors.groupingBy(SelectedUnit::getUnitType));

        model.addAttribute("unitsByType", unitsByType);
        model.addAttribute("availableUnits", availableUnits);
        model.addAttribute("selectedUnits", selectedUnits);

        double totalPoints = armyCompositionService.calculateTotalPoints();
        model.addAttribute("totalPoints", totalPoints);

        Map<String, Double> dedicatedPoints = armyCompositionService.calculateDedicatedPoints();
        model.addAttribute("dedicatedPoints", dedicatedPoints);

        model.addAttribute("pointsRestriction", pointsRestriction);

        Map<String, Double> pointsLimitsByType = armyCompositionService.calculatePointsLimitsByType(pointsRestriction);
        model.addAttribute("pointsLimitsByType", pointsLimitsByType);

        Map<String, Double> utilizedPointsByType = armyCompositionService.calculateUtilizedPointsByType(unitsByType);
        model.addAttribute("utilizedPointsByType", utilizedPointsByType);

        return "menu";
    }



    @PostMapping("/addUnit")
    public String addUnit(@RequestParam("unitId") Integer unitId) {
        Optional<UnitDTO> unitOptional = unitService.getUnitDTO(unitId);

        if (unitOptional.isPresent()) {
            UnitDTO unit = unitOptional.get();
            SelectedUnit selectedUnit = new SelectedUnit(unitService.getUnitAndSendItToSelected(unitId));
            selectedUnitService.saveSelectedUnit(selectedUnit);
        }
        if (unitOptional.isEmpty()) {
            return "redirect:/error?message=Unit not found";
        }

        return "redirect:/menu";
    }


//    @PostMapping("/addUnit")
//    public String addUnit(@RequestParam("unitId") Integer unitId) {
//        Optional<UnitDTO> unitOptional = unitService.getUnitDTO(unitId);
//
//        if (unitOptional.isPresent()) {
//            UnitDTO unit = unitOptional.get();
//            SelectedUnit selectedUnit = new SelectedUnit(unitService.getUnitAndSendItToSelected(unitId));
//
//            if (selectedUnit.getSelectedStats() != null) {
//                selectedStatsRepository.save(selectedUnit.getSelectedStats());
//            }
//
//            selectedUnitRepository.save(selectedUnit);
//
//            List<SelectedUpgrade> selectedUpgradeList = selectedUnit.getSelectedUpgrades().stream().filter(Objects::nonNull).collect(Collectors.toList());
//            if (!selectedUpgradeList.isEmpty()) {
//                selectedUpgradeService.addFreeUpgradesAndSpecialRaceUpgrades(selectedUnit.getId());
//                selectedUpgradeRepository.saveAll(selectedUpgradeList);
//
//            }
//        }
//        return "redirect:/menu";
//    }


    @PostMapping("/removeUnit")
    public String removeUnit(@RequestParam("id") Integer selectedId) {
        selectedUnitService.removeUnitById(selectedId);
        double totalPoints = armyCompositionService.calculateTotalPoints();
        Map<String, Double> dedicatedPoints = armyCompositionService.calculateDedicatedPoints();
        return "redirect:/menu";
    }

    @PostMapping("/increaseUnitQuantity")
    public String increaseUnitQuantity(@RequestParam("id") Integer selectedId) {
        selectedUnitService.increaseUnitQuantity(selectedId);
        return "redirect:/menu";
    }

    @PostMapping("/decreaseUnitQuantity")
    public String decreaseUnitQuantity(@RequestParam("id") Integer selectedId) {
        selectedUnitService.decreaseUnitQuantity(selectedId);
        return "redirect:/menu";
    }



    @PostMapping("/addUpgrade")
    public ResponseEntity<String> addUpgrade(@RequestParam Integer upgradeId) {
        try {
            selectedUpgradeService.addUpgrade(upgradeId);
            armyCompositionService.calculateDedicatedPoints();
            return ResponseEntity.ok("Upgrade added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @PostMapping("/removeSelectedUpgrade")
    public ResponseEntity<String> removeSelectedUpgrade(int upgradeId) {
            selectedUpgradeService.removeSelectedUpgrade(upgradeId);
        return ResponseEntity.ok().body("Error while deleting selectedUpgrade");
    }

    @PostMapping("/setPointsRestriction")
    public String setPoints(@RequestParam("points") double newPoints) {
        pointsRestriction = newPoints;
        return "redirect:/menu";
    }

    @GetMapping("/units/{id}/upgrades")
    public ResponseEntity<List<SelectedUpgradeDTO>> getSelectedUpgrades(@PathVariable Integer id) {
        SelectedUnitView selectedUnitView = selectedUnitViewService.getSelectedSingleUnitView(id);
        List<SelectedUpgradeDTO> selectedUpgradeDTO = selectedUnitView.getUpgrades();
        return ResponseEntity.ok().body(selectedUpgradeDTO);

    }


    @PostMapping("/saveRoaster")
    public ResponseEntity<String> saveArmyComposition() {
        armyCompositionService.saveSelectedUnitsList();

        return ResponseEntity.ok().body("Army composition saved");
    }

//    @GetMapping("/loadRoaster")
//    public ResponseEntity<String> loadArmyComposition(@RequestParam int armyId) {
//
//    }


}