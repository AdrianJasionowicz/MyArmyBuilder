package com.jasionowicz.myarmybuilder.selectedUpgrades;

import com.jasionowicz.myarmybuilder.armyComposition.ArmyCompositionService;
import com.jasionowicz.myarmybuilder.selectedUnitView.SelectedUnitView;
import com.jasionowicz.myarmybuilder.selectedUnitView.SelectedUnitViewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SelectedUpgradeController {

private SelectedUpgradeService selectedUpgradeService;
private ArmyCompositionService armyCompositionService;
private SelectedUnitViewService selectedUnitViewService;

    public SelectedUpgradeController(SelectedUpgradeService selectedUpgradeService, ArmyCompositionService armyCompositionService,SelectedUnitViewService selectedUnitViewService) {
        this.selectedUpgradeService = selectedUpgradeService;
        this.armyCompositionService = armyCompositionService;
        this.selectedUnitViewService = selectedUnitViewService;
    }


    @PostMapping("/addUpgrade")
    public ResponseEntity<String> addUpgrade(@RequestParam Integer upgradeId) {
        selectedUpgradeService.addUpgrade(upgradeId);
        armyCompositionService.calculateDedicatedPoints();
        return ResponseEntity.ok("Upgrade added successfully");
    }


    @PostMapping("/removeSelectedUpgrade")
    public ResponseEntity<String> removeSelectedUpgrade(int upgradeId) {
        selectedUpgradeService.removeSelectedUpgrade(upgradeId);
        return ResponseEntity.ok().body("Error while deleting selectedUpgrade");
    }

    @GetMapping("/units/{id}/upgrades")
    public ResponseEntity<List<SelectedUpgradeDTO>> getSelectedUpgrades(@PathVariable Integer id) {
        SelectedUnitView selectedUnitView = selectedUnitViewService.getSelectedSingleUnitView(id);
        List<SelectedUpgradeDTO> selectedUpgradeDTO = selectedUnitView.getUpgrades();
        return ResponseEntity.ok().body(selectedUpgradeDTO);
    }
}
