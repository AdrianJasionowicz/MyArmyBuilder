package com.jasionowicz.myarmybuilder.selectedUnits;

import com.jasionowicz.myarmybuilder.unit.Unit;
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
    private SelectedService selectedService;

    public SelectedUpgrades(SelectedService selectedService) {
        this.selectedService = selectedService;
        this.selectedUpgrades = new ArrayList<>();
    }

    public void addUpgrade(Upgrade upgrade) {
        selectedUpgrades.add(upgrade);
    }

    public void removeUpgrade(Integer selectedId, Integer upgradeId) {
        Unit unit = selectedService.getBySelectedId(selectedId);
        if (unit != null) {
            Iterator<Upgrade> iterator = selectedUpgrades.iterator();
            while (iterator.hasNext()) {
                Upgrade upgrade = iterator.next();
                if (upgrade.getId().equals(upgradeId) && unit.getSelectedId().equals(selectedId)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

}
