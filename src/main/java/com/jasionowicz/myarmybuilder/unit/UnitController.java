package com.jasionowicz.myarmybuilder.unit;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UnitController {

    @Autowired
    private final UnitService unitService;
    @Autowired
    private SelectedUnitService selectedUnitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @DeleteMapping("/admin/unit/{id}")
    public void deleteUnit(@PathVariable Integer id) {
        unitService.deleteById(id);
    }

    @GetMapping("/getAvailableUnits")
    public List<UnitDTO> getAvailableUnits() {
        return unitService.getUnitDtoList();
    }

    @PostMapping("/addUnit")
    public void addUnit(@RequestParam("unitId") Integer unitId) {
        Optional<UnitDTO> unitOptional = unitService.getUnitDTO(unitId);

        if (unitOptional.isPresent()) {
            UnitDTO unit = unitOptional.get();
            SelectedUnit selectedUnit = new SelectedUnit(unitService.getUnitAndSendItToSelected(unitId));
            selectedUnitService.saveSelectedUnit(selectedUnit);
        }

    }
}
