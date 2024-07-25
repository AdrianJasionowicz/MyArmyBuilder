package com.jasionowicz.myarmybuilder.selectedUpgrades;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SelectedUpgradesController {

    private final SelectedUpgradesRepository selectedUpgradesRepository;


//    @GetMapping("/showAllUpgr")
//    public List<SelectedUpgrades> selectedUpgradesDTOS(SelectedUpgrades selectedUpgrades, SelectedUpgradesRepository selectedUpgradesRepository) {
//
//        return selectedUpgradesRepository.findAllBy();
//    }


    public SelectedUpgradesController(SelectedUpgradesRepository selectedUpgradesRepository) {
        this.selectedUpgradesRepository = selectedUpgradesRepository;
    }
}
