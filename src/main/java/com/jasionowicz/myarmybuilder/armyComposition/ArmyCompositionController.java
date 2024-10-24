package com.jasionowicz.myarmybuilder.armyComposition;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ArmyCompositionController {
    private ArmyCompositionService armyCompositionService;

    public ArmyCompositionController(ArmyCompositionService armyCompositionService) {
        this.armyCompositionService = armyCompositionService;
    }

    @GetMapping("/loadTemplatesByUser")
    public List<ArmyComposition> getTemplatesList() {
        List<ArmyComposition> armyCompositionList = armyCompositionService.getArmyComposition();
        return armyCompositionList;

    }

    @GetMapping("/template")
    public String viewTemplates() {
        return "template";
    }

    @PostMapping("/saveTemplate")
    public String saveTemplate(@RequestParam String templateName) {
        armyCompositionService.saveArmyTemplate(templateName);
        return "redirect:/menu";
    }

    @DeleteMapping("/deleteTemplate")
    public void deleteTemplate(@RequestParam int templateId) {
        armyCompositionService.removeTemplate(templateId);
    }


    @PostMapping("/setPointsRestriction")
    public Map<String,Double> setPoints(@RequestParam("points") double newPoints) {
       Map<String,Double> pointsRestriction;
        pointsRestriction = armyCompositionService.calculatePointsLimitsByType(newPoints);
        return pointsRestriction;
    }

@GetMapping("/usedPoints")
public Map<String,Double> usedPoints() {
        return armyCompositionService.calculateDedicatedPoints();
}

    @PostMapping("/saveRoaster")
    public ResponseEntity<String> saveArmyComposition() {
        armyCompositionService.saveSelectedUnitsList();

        return ResponseEntity.ok().body("Army composition saved");
    }
}
