package com.jasionowicz.myarmybuilder.unit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class UnitController {

    @Autowired
    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @DeleteMapping("/unit/{id}")
    public void deleteUnit(@PathVariable Integer id) {
        unitService.deleteById(id);
    }

}
