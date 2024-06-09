package com.jasionowicz.myarmybuilder.upgrade;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/upgrades")
public class UpgradeController {

    private final UpgradeRepository upgradeRepository;
    private final UpgradesService upgradesService;

    public UpgradeController(UpgradeRepository upgradeRepository, UpgradesService upgradesService) {
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

}
