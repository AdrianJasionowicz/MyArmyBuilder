package com.jasionowicz.myarmybuilder.unit;

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


    @PostMapping("/addUnits")
    public void addUnit(@RequestParam("name") String name,@RequestParam("pointsCostPerUnit") Double pointsCostPerUnit, @RequestParam("quantity") int quantity,@RequestParam("minQuantity") int minQuantity,@RequestParam("unitType") String unitType) {
        Unit unit = new Unit(name,pointsCostPerUnit, quantity, minQuantity,unitType);
        System.out.println("Adding unit: " + unit.getName() + " with minimum quantity: " + minQuantity);
        unitService.saveUnitToList(unit);
    }

    @GetMapping("/{id}")
    public Optional<Optional> getUnitById(@PathVariable Integer id) {
        return Optional.ofNullable(unitService.findById(id));
    }

    @GetMapping("/getUnits")
    public List<Unit> getUnits() {
        return unitService.findAll();
    }

    @PostMapping("/addUrUnit")
    public Unit createUnit(@RequestBody Unit unit) {
        return unitService.save(unit);
    }

    @PutMapping("/{id}")
    public Unit updateUnit(@PathVariable Integer id, @RequestBody Unit updatedUnit) {
        return unitService.updateUnit(id, updatedUnit);
    }

    @DeleteMapping("/{id}")
    public void deleteUnit(@PathVariable Integer id) {
        unitService.deleteById(id);
    }

}
