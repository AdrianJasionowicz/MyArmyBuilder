package com.jasionowicz.myarmybuilder.upgrade;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/upgrades")
public class UpgradeController {
    private SelectedUnit selectedUnit;
    private final UpgradeRepository upgradeRepository;
    private final UpgradesService upgradesService;

    public UpgradeController(SelectedUnit selectedUnit, UpgradeRepository upgradeRepository, UpgradesService upgradesService) {
        this.selectedUnit = selectedUnit;
        this.upgradeRepository = upgradeRepository;
        this.upgradesService = upgradesService;
    }

    @PostMapping("/addUpgrade")
    public void addUpgrade(@RequestBody Upgrade upgrade) {
        upgradesService.addUpgrade(upgrade);
    }

}
