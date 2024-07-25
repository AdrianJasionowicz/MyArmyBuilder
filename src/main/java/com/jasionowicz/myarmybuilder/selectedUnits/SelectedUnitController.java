package com.jasionowicz.myarmybuilder.selectedUnits;

import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class SelectedUnitController {
    private SelectedUnit selectedUnit;
    private SelectedUnitRepository selectedUnitRepository;

    public SelectedUnitController(SelectedUnit selectedUnit, SelectedUnitRepository selectedUnitRepository) {
        this.selectedUnit = selectedUnit;
        this.selectedUnitRepository = selectedUnitRepository;
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<SelectedUnit> getSelectedUnitById(@PathVariable int id) {
        Optional<SelectedUnit> optSelectedUnit = selectedUnitRepository.findById(id);
        SelectedUnit selectedUnit = optSelectedUnit.get();
        return ResponseEntity.ok().body(selectedUnit);
    }
    @GetMapping("/show")
    public List<SelectedUnit> getSelectedUnit() {

        return selectedUnitRepository.findAll();
    }

}
