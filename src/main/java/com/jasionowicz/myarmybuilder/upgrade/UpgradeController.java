package com.jasionowicz.myarmybuilder.upgrade;

import com.jasionowicz.myarmybuilder.unit.SelectedUnits;
import com.jasionowicz.myarmybuilder.unit.Unit;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/upgrades")
public class UpgradeController {
private SelectedUnits selectedUnits;
    private final UpgradeRepository upgradeRepository;
    private final UpgradesService upgradesService;

    public UpgradeController(SelectedUnits selectedUnits, UpgradeRepository upgradeRepository, UpgradesService upgradesService) {
        this.selectedUnits = selectedUnits;
        this.upgradeRepository = upgradeRepository;
        this.upgradesService = upgradesService;
    }

    @PostMapping("/addUpgrade")
    public void addUpgrade(@RequestBody Upgrade upgrade) {
        upgradesService.addUpgrade(upgrade);
    }

    @GetMapping("/showall")
    public List<Upgrade> getAllUpgrades() {
        return upgradesService.findAll();
    }
    @GetMapping("/upu")
    public List<Unit> getAllSelectedUnits() {
        return selectedUnits.getSelectedUnits();
    }

}
