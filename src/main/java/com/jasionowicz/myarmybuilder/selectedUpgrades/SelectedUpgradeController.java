package com.jasionowicz.myarmybuilder.selectedUpgrades;

import org.springframework.stereotype.Controller;

@Controller
public class SelectedUpgradeController {

    private final SelectedUpgradeRepository selectedUpgradeRepository;


    public SelectedUpgradeController(SelectedUpgradeRepository selectedUpgradeRepository) {
        this.selectedUpgradeRepository = selectedUpgradeRepository;
    }
}
