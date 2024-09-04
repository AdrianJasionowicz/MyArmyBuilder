package com.jasionowicz.myarmybuilder.unit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
public class UnitController {

    @Autowired
    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @DeleteMapping("/{id}")
    public void deleteUnit(@PathVariable Integer id) {
        unitService.deleteById(id);
    }

    @GetMapping("/nation/{nation}")
    public List<Unit> getUnitsByNation(@PathVariable String nation) {
        return unitService.getUnitsByNation(nation);
    }
}
