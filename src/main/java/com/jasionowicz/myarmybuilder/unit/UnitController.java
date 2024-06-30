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


    @PostMapping("/addUnits")
    public void addUnit(@RequestParam("name") String name, @RequestParam("pointsCostPerUnit") Double pointsCostPerUnit, @RequestParam("quantity") int quantity, @RequestParam("minQuantity") int minQuantity, @RequestParam("unitType") String unitType) {
        UnitDTO unitDTO = new UnitDTO(name, pointsCostPerUnit, quantity, minQuantity, unitType);
        System.out.println("Adding unit: " + unitDTO.getName() + " with minimum quantity: " + minQuantity);
        unitService.add(unitDTO);
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
