package com.jasionowicz.myarmybuilder.armyComposition;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class ArmyCompositionController {
    private ArmyCompositionService armyCompositionService;

    public ArmyCompositionController(ArmyCompositionService armyCompositionService) {
        this.armyCompositionService = armyCompositionService;
    }

//    @GetMapping("/showComposition/{armyId}")
//    public ArmyComposition armyComposition(@PathVariable Integer armyId) {
//        return   armyCompositionRepository.findById(armyId).orElseThrow();
//    }

    @GetMapping("/template")
    public String viewTemplates() {
        return "template";
    }

}
