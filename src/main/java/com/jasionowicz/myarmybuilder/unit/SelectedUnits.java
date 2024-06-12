package com.jasionowicz.myarmybuilder.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Getter
@Setter
@Component
public class SelectedUnits {
    public List<Unit> selectedUnits;
    private Unit unit;

    public SelectedUnits() {
        this.selectedUnits = new ArrayList<>();
    }

    public ResponseEntity addUnit(Unit unit) {
        this.selectedUnits.add(unit);
        return ResponseEntity.ok(unit);
    }

    public ResponseEntity removeUnitById(int id) {
        Unit unitToRemove = null;
        for (Unit unit : selectedUnits) {
            if (unit.getId().equals(id)) {
                unitToRemove = unit;
                break;
            }
        }
        if (unitToRemove != null) {
            this.selectedUnits.remove(unitToRemove);
        }
        return ResponseEntity.ok(unitToRemove);
    }

    public Unit findUnitById(Integer unitId) {
        return this.selectedUnits.stream()
                .filter(unit -> unit.getId().equals(unitId))
                .findFirst()
                .orElse(null);
    }


    public ResponseEntity increaseUnitQuantity(int unitId) {
        Unit selectedUnit = selectedUnits.stream()
                .filter(unit -> unit.getId() == unitId)
                .findFirst()
                .orElse(null);

        if (selectedUnit != null) {
            selectedUnit.setQuantity(selectedUnit.getQuantity() + 1);
        }
        return ResponseEntity.ok("Done");
    }

    public ResponseEntity decreaseUnitQuantity(int unitId) {
        Unit selectedUnit = findUnitById(unitId);
        if (selectedUnit != null && selectedUnit.getQuantity() > 0) {
            selectedUnit.setQuantity(selectedUnit.getQuantity() - 1);
        }
        return ResponseEntity.ok("Done");
    }


}