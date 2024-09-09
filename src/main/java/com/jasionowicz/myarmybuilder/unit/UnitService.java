package com.jasionowicz.myarmybuilder.unit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UnitService {
    @Autowired
    private UnitRepository unitRepository;

    public void deleteById(Integer id) {
        unitRepository.deleteById(id);
    }

    public UnitDTO converToDto(Unit unit) {
        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setId(unit.getId());
        unitDTO.setName(unit.getName());
        unitDTO.setNation(unit.getNation());
        unitDTO.setUnitType(unit.getUnitType());
        unitDTO.setMinQuantity(unit.getMinQuantity());
        unitDTO.setPointsCostPerUnit(unit.getPointsCostPerUnit());
        unitDTO.setUnitStats(unit.getUnitStats());
        unitDTO.setUpgradeList(new ArrayList<>(unit.getUpgradesList()));
        return unitDTO;
    }

    public List<UnitDTO> getUnitDtoList() {
        List<UnitDTO> unitDTOList = new ArrayList<>();
        List<Unit> units = unitRepository.findAll();
        for (Unit unit : units) {
            if (units.contains(unit)) {
                unitDTOList.add(converToDto(unit));
            }
        }
        return unitDTOList;
    }

    public Optional<UnitDTO> getUnitDTO(Integer unitId) {
        Optional<Unit> optUnit = unitRepository.findById(unitId);
        if (optUnit.isPresent()) {
            Unit unit = optUnit.get();
            UnitDTO unitDTO = converToDto(unit);
            return Optional.of(unitDTO);
        }
        return Optional.empty();
    }

    public Unit getUnitAndSendItToSelected(Integer id) {
        return unitRepository.getReferenceById(id);
    }
}

