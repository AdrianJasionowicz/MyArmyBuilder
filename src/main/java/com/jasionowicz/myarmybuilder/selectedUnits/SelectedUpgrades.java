package com.jasionowicz.myarmybuilder.selectedUnits;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedService;
import com.jasionowicz.myarmybuilder.upgrade.Upgrade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
@Component
public class SelectedUpgrades {

    private List<Upgrade> selectedUpgrades;

    private List<Integer> selectedUnitIds;

    private SelectedService selectedService;

    public SelectedUpgrades(SelectedService selectedService) {
        this.selectedService = selectedService;
        this.selectedUpgrades = new ArrayList<>();
        this.selectedUnitIds = new ArrayList<>();
    }

    public void addUpgrade(Upgrade upgrade, int selectedUnitId) {
        selectedUpgrades.add(upgrade);
        selectedUnitIds.add(selectedUnitId);
    }

    public void removeUpgrade(Integer upgradeId) {
        Iterator<Upgrade> iterator = selectedUpgrades.iterator();
        Iterator<Integer> unitIdIterator = selectedUnitIds.iterator();
        while (iterator.hasNext() && unitIdIterator.hasNext()) {
            Upgrade upgrade = iterator.next();
            Integer unitId = unitIdIterator.next();
            if (upgrade.getId().equals(upgradeId)) {
                iterator.remove();
                unitIdIterator.remove();
                break;
            }
        }
    }
}
