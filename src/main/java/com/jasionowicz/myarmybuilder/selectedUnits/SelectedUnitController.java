package com.jasionowicz.myarmybuilder.selectedUnits;

import com.jasionowicz.myarmybuilder.armyComposition.ArmyCompositionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SelectedUnitController {
    private SelectedUnitService selectedUnitService;
    private ArmyCompositionService armyCompositionService;


    public SelectedUnitController(SelectedUnitService selectedUnitService, ArmyCompositionService armyCompositionService) {
        this.selectedUnitService = selectedUnitService;
        this.armyCompositionService = armyCompositionService;
    }

    @GetMapping("/getSelectedUnits")
    public List<SelectedUnitDTO> getSelectedUnits() {
       return selectedUnitService.convertListToDTO();

    }

    @PostMapping("/removeUnit")
    public void removeUnit(@RequestParam("id") Integer selectedId) {
        selectedUnitService.removeUnitById(selectedId);
    }

    @PostMapping("/increaseUnitQuantity")
    public void increaseUnitQuantity(@RequestParam("id") Integer selectedId) {
        selectedUnitService.increaseUnitQuantity(selectedId);
    }

    @PostMapping("/decreaseUnitQuantity")
    public void decreaseUnitQuantity(@RequestParam("id") Integer selectedId) {
        selectedUnitService.decreaseUnitQuantity(selectedId);
    }
}
