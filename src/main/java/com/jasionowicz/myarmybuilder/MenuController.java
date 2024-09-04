package com.jasionowicz.myarmybuilder;

import com.jasionowicz.myarmybuilder.armyComposition.ArmyComposition;
import com.jasionowicz.myarmybuilder.armyComposition.ArmyCompositionRepository;
import com.jasionowicz.myarmybuilder.armyComposition.ArmyCompositionService;
import com.jasionowicz.myarmybuilder.selectedStats.SelectedStatsRepository;
import com.jasionowicz.myarmybuilder.selectedUnitView.SelectedUnitView;
import com.jasionowicz.myarmybuilder.selectedUnitView.SelectedUnitViewService;
import com.jasionowicz.myarmybuilder.selectedUnits.*;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgrade;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeDTO;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeRepository;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeService;
import com.jasionowicz.myarmybuilder.unit.Unit;
import com.jasionowicz.myarmybuilder.unit.UnitRepository;
import com.jasionowicz.myarmybuilder.unit.UnitService;
import com.jasionowicz.myarmybuilder.upgrade.UpgradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MenuController {


    private final SelectedService selectedService;
    private final UnitRepository unitRepository;
    private final UpgradesService upgradesService;
    private final ArmyComposition armyComposition;
    private final ArmyCompositionService armyCompositionService;
    private final UnitService unitService;
    private final SelectedUnitRepository selectedUnitRepository;
    private final SelectedUpgradeRepository selectedUpgradeRepository;
    private double pointsRestriction = 0;
    private final SelectedUnit selectedUnit;
    private SelectedUpgradeDTO selectedUpgradeDTO;
    private final SelectedUpgrade selectedUpgrade;
    @Autowired
    private SelectedStatsRepository selectedStatsRepository;
    @Autowired
    private SelectedUpgradeService selectedUpgradeService;
    @Autowired
    private ArmyCompositionRepository armyCompositionRepository;
    @Autowired
    private SelectedUnitViewService selectedUnitViewService;

    @Autowired
    public MenuController(UnitRepository unitRepository, UpgradesService upgradesService, ArmyComposition armyComposition, ArmyCompositionService armyCompositionService, UnitService unitService, SelectedService selectedService, SelectedUnitRepository selectedUnitRepository, SelectedUpgradeRepository selectedUpgradeRepository, SelectedUnit selectedUnit, SelectedUpgradeDTO selectedUpgradeDTO, SelectedUpgrade selectedUpgrade) {
        this.unitRepository = unitRepository;
        this.upgradesService = upgradesService;
        this.armyComposition = armyComposition;
        this.armyCompositionService = armyCompositionService;
        this.unitService = unitService;
        this.selectedService = selectedService;
        this.selectedUnitRepository = selectedUnitRepository;
        this.selectedUpgradeRepository = selectedUpgradeRepository;
        this.selectedUnit = selectedUnit;
        this.selectedUpgradeDTO = selectedUpgradeDTO;
        this.selectedUpgrade = selectedUpgrade;
    }


    @GetMapping("/menu")
    public String showMenu(Model model) {
        List<Unit> availableUnits = unitRepository.findAll();
        List<SelectedUnit> selectedUnits = selectedService.getSelectedUnits();
        Map<String, List<SelectedUnit>> unitsByType = selectedUnits.stream()
                .collect(Collectors.groupingBy(SelectedUnit::getUnitType));

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
            }

            selectedUnitRepository.save(selectedUnit);

            List<SelectedUpgrade> selectedUpgradeList = selectedUnit.getSelectedUpgrades().stream().filter(Objects::nonNull).collect(Collectors.toList());
            if (!selectedUpgradeList.isEmpty()) {
                selectedUpgradeService.addFreeUpgradesAndSpecialRaceUpgrades(selectedUnit.getId());
                selectedUpgradeRepository.saveAll(selectedUpgradeList);

            }


        }

        return "redirect:/menu";
    }


    @PostMapping("/removeUnit")
    public String removeUnit(@RequestParam("id") Integer selectedId) {
        selectedService.removeUnitById(selectedId);
        double totalPoints = armyCompositionService.calculateTotalPoints();
        Map<String, Double> dedicatedPoints = armyCompositionService.calculateDedicatedPoints();
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
    public ResponseEntity<String> addUpgrade(@RequestParam Integer upgradeId) {
        Optional<SelectedUpgrade> optionalSelectedUpgrade = selectedUpgradeRepository.findById(upgradeId);
        selectedUpgradeService.checkAmmountOfSBattleStandardsInArmy();

        if (optionalSelectedUpgrade.isPresent()) {

            SelectedUpgrade selectedUpgrade = optionalSelectedUpgrade.get();
            SelectedUnit selectedUnit = selectedUpgrade.getSelectedUnit();
            int id = selectedUnit.getId();
            SelectedUnit selectedUnitForQuantity = selectedUnitRepository.getReferenceById(id);
            boolean isWeaponTeamTaken = selectedUpgradeService.checkWeaponTeams(id);


            if (isWeaponTeamTaken) {
                selectedUpgrade.setSelected(false);
                selectedUpgradeRepository.save(selectedUpgrade);
                return ResponseEntity.badRequest().body("Unit can take only one Weapon team");
            }

            if (selectedUnit.getUnitType().equals("Lords")) {
                selectedUpgradeService.checkLordsUpgrades(id);
            }

            if (selectedUnit.getUnitType().equals("Hero")) {
                boolean hasAStandardBanner = false;
                hasAStandardBanner = selectedUpgradeService.checkChieftainBattleStandard(id);
                if (!hasAStandardBanner) {
                    selectedUpgradeService.checkHeroUpgrades(id);
                } else {
                    throw new RuntimeException("Hero with Standard banner cannont take Magic items");
                }


            }


            if (selectedUpgrade.isSelected()) {

                return ResponseEntity.badRequest().body("Upgrade already selected");
            }
            selectedUpgrade.setSelected(true);
            if (selectedUpgrade.getUpgrade().getUpgradeType().equals("Weapon Team")) {

                selectedUpgrade.setQuantity(1);
            }
            if (selectedUpgrade.getUpgrade().getUpgradeType().equals("SingleBuy")) {
                selectedUpgrade.setQuantity(1);
            } else {
                selectedUpgrade.setQuantity(selectedUnitForQuantity.getQuantity());
            }
            selectedUpgradeRepository.save(selectedUpgrade);


            return ResponseEntity.ok().body("Upgrade added successfully");
        }
        return ResponseEntity.badRequest().body("Upgrade not found");
    }

    @PostMapping("/removeSelectedUpgrade")
    public ResponseEntity<String> removeSelectedUpgrade(int upgradeId) {
        Optional<SelectedUpgrade> optionalSelectedUpgrade = selectedUpgradeRepository.findById(upgradeId);
        if (optionalSelectedUpgrade.isPresent()) {
            SelectedUpgrade selectedUpgrade = optionalSelectedUpgrade.get();
            selectedUpgrade.setSelected(false);
            selectedUpgradeRepository.save(selectedUpgrade);

            return ResponseEntity.ok().body("Upgrade removed successfully");
        }
        return ResponseEntity.badRequest().body("Upgrade not found");
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
        utilizedPointsByType = armyCompositionService.calculateDedicatedPoints();
        return utilizedPointsByType;
    }


    @GetMapping("/units/{id}/upgrades")
    public ResponseEntity<List<SelectedUpgradeDTO>> getSelectedUpgrades(@PathVariable Integer id) {
        SelectedUnitView selectedUnitView = selectedUnitViewService.getSelectedSingleUnitView(id);
        List<SelectedUpgradeDTO> selectedUpgradeDTO = selectedUnitView.getUpgrades();
        return ResponseEntity.ok().body(selectedUpgradeDTO);

    }


    @PostMapping("/saveRoaster")
    public ResponseEntity<String> saveArmyComposition() {

        List<SelectedUnit> selectedUnits = selectedUnitRepository.findAll();
        armyCompositionService.addNewArmyTemplate(selectedUnits);
        if (selectedUnits.isEmpty()) {
            return ResponseEntity.badRequest().body("Army composition is empty");
        }
        return ResponseEntity.ok().body("Army composition saved");
    }

//    @GetMapping("/loadRoaster")
//    public ResponseEntity<String> loadArmyComposition(@RequestParam int armyId) {
//
//    }


}