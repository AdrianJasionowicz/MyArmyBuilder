package com.jasionowicz.myarmybuilder.unit;

import com.jasionowicz.myarmybuilder.upgrade.Upgrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/units")
public class UnitController {

    @Autowired
    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }



    @GetMapping("/{id}")
    public Optional<Optional> getUnitById(@PathVariable Integer id) {
        return Optional.ofNullable(unitService.findById(id));
    }

    @GetMapping("/getUnits")
    public List<UnitDTO> getUnits() {
        return unitService.findAll();
    }

    @PostMapping("/addUrUnit")
    public void createUnit(@RequestBody UnitDTO unit) {
        unitService.save(unit);
    }

    @PutMapping("/{id}")
    public UnitDTO updateUnit(@PathVariable Integer id, @RequestBody UnitDTO updatedUnit) {
        return unitService.updateUnit(id, updatedUnit);
    }

    @DeleteMapping("/{id}")
    public void deleteUnit(@PathVariable Integer id) {
        unitService.deleteById(id);
    }

}
