package com.jasionowicz.myarmybuilder.selectedUnits;

import com.jasionowicz.myarmybuilder.unit.Unit;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Getter
@Component
public class    SelectedUnits {
    private final List<Unit> selectedUnits = new ArrayList<>();

    public void addUnit(Unit unit) {
        selectedUnits.add(unit);
    }

    public void removeUnitBySelectedId(Integer selectedId) {
        selectedUnits.removeIf(unit -> unit.getSelectedId().equals(selectedId));
    }

    public List<Unit> getSelectedUnits() {
        return new ArrayList<>(selectedUnits);
    }

    public Optional<Unit> findBySelectedId(Integer selectedId) {
        return selectedUnits.stream()
                .filter(unit -> unit.getSelectedId().equals(selectedId))
                .findFirst();
    }

    public double getUnitQuantityBySelectedId(Integer selectedId) {
        for (Unit selectedUnit : selectedUnits) {
            if (selectedUnit.getSelectedId().equals(selectedId)) {
                return selectedUnit.getQuantity();
            }
        }
        return 1;
    }
}
