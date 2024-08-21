package com.jasionowicz.myarmybuilder.armyComposition;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import org.springframework.web.bind.annotation.GetMapping;
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

//    @GetMapping("/showComposition/{id}")
//    public SelectedUnit armyComposition(Integer armyId) {
//     return armyCompositionRepository.getSelectedUnitsList(armyId);
//    }



}
