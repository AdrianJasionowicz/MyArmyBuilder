package com.jasionowicz.myarmybuilder.selectedStats;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnitRepository;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgrade;

import java.util.List;

public class SelectedStatsService {
    private SelectedStats selectedStats;
    private SelectedStatsService selectedStatsService;
    private SelectedUnitRepository selectedUnitRepository;
    private SelectedStatsRepository selectedStatsRepository;

    public SelectedStatsService(SelectedStats selectedStats, SelectedStatsService selectedStatsService, SelectedUnitRepository selectedUnitRepository, SelectedStatsRepository selectedStatsRepository) {
        this.selectedStats = selectedStats;
        this.selectedStatsService = selectedStatsService;
        this.selectedUnitRepository = selectedUnitRepository;
        this.selectedStatsRepository = selectedStatsRepository;
    }


    public void setUnitStats(int selectedId) {
        SelectedUnit selectedUnit = selectedUnitRepository.getReferenceById(selectedId);
        int statsId = selectedUnit.getSelectedStats().getId();
        List<SelectedUpgrade> selectedUpgradeList = selectedUnit.getSelectedUpgrades();
        SelectedStats selectedStats = selectedStatsRepository.getReferenceById(statsId);
        for (SelectedUpgrade selectedUpgrade : selectedUpgradeList) {
            if (selectedUpgrade.isSelected()) {
                if (selectedUpgrade.getUpgrade().getName().equalsIgnoreCase("Light Armor")) {
                    selectedUnit.getSelectedStats().setBasicSave(6);
                }
                if (selectedUpgrade.getUpgrade().getName().equalsIgnoreCase("Heavy Armor")) {
                    selectedUnit.getSelectedStats().setBasicSave(5);
                }
                if (selectedUpgrade.getUpgrade().getName().equalsIgnoreCase("Shield")) {
                    int basicSave = selectedUnit.getSelectedStats().getBasicSave();
                    selectedUnit.getSelectedStats().setBasicSave(basicSave - 1);
                    Integer wardSave = selectedUnit.getSelectedStats().getWardSave();
                    if (wardSave == 0 || wardSave == null) {
                        selectedUnit.getSelectedStats().setWardSave(6);
                    } else {
                        selectedUnit.getSelectedStats().setWardSave(wardSave - 1);
                    }
                }
            }


        }


    }


}
