package com.jasionowicz.myarmybuilder;

import com.jasionowicz.myarmybuilder.armyComposition.ArmyCompositionService;
import com.jasionowicz.myarmybuilder.security.model.BuilderUser;
import com.jasionowicz.myarmybuilder.selectedStats.SelectedStatsRepository;
import com.jasionowicz.myarmybuilder.selectedUnitView.SelectedUnitView;
import com.jasionowicz.myarmybuilder.selectedUnitView.SelectedUnitViewService;
import com.jasionowicz.myarmybuilder.selectedUnits.*;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgrade;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeDTO;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeRepository;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeService;
import com.jasionowicz.myarmybuilder.unit.UnitDTO;
import com.jasionowicz.myarmybuilder.unit.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MenuController {

    @GetMapping("/menu")
    public String showMenu() {
        return "menu";
    }






}