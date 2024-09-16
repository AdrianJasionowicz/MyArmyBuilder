package com.jasionowicz.myarmybuilder.armyComposition;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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


}
