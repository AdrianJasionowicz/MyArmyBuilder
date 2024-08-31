package com.jasionowicz.myarmybuilder.armyComposition;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArmyCompositionController {
    private ArmyCompositionService armyCompositionService;
    private ArmyCompositionRepository armyCompositionRepository;

    public ArmyCompositionController(ArmyCompositionService armyCompositionService, ArmyCompositionRepository armyCompositionRepository) {
        this.armyCompositionService = armyCompositionService;
        this.armyCompositionRepository = armyCompositionRepository;
    }

    @GetMapping("/showComposition/{armyId}")
    public ArmyComposition armyComposition(@PathVariable Integer armyId) {
        return   armyCompositionRepository.findById(armyId).orElseThrow();
    }



}
