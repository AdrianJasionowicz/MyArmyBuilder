package com.jasionowicz.myarmybuilder.unit;

import com.jasionowicz.myarmybuilder.upgrade.Upgrade;
import com.jasionowicz.myarmybuilder.upgrade.UpgradeDTO;
import com.jasionowicz.myarmybuilder.upgrade.UpgradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnitService {
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private UpgradeRepository upgradeRepository;

    public Optional<UnitDTO> findById(Integer id) {
        return unitRepository.findById(id).map(this::convertToDto);
    }

    public UnitDTO updateUnit(Integer id, UnitDTO updatedUnitDto) {
        return unitRepository.findById(id).map(unit -> {
            unit.setName(updatedUnitDto.getName());
            unit.setPointsCostPerUnit(updatedUnitDto.getPointsCostPerUnit());
            unit.setQuantity(updatedUnitDto.getQuantity());
            unit.setMinQuantity(updatedUnitDto.getMinQuantity());
            unit.setUnitType(updatedUnitDto.getUnitType());
            unit.setStatsId(updatedUnitDto.getStatsId());
            unit.setUnitStats(updatedUnitDto.getUnitStats());
            List<Upgrade> upgrades = updatedUnitDto.getUpgradeList().stream()
                    .map(dto -> new UpgradeDTO(upgradeRepository).toEntity())
                    .collect(Collectors.toList());
            unit.setUpgradesList(upgrades);
            Unit updatedUnit = unitRepository.save(unit);
            return convertToDto(updatedUnit);
        }).orElseThrow(() -> new RuntimeException("Unit not found"));
    }

    private UnitDTO convertToDto(Unit unit) {
        UnitDTO unitDto = new UnitDTO();
        unitDto.setId(unit.getId());
        unitDto.setName(unit.getName());
        unitDto.setPointsCostPerUnit(unit.getPointsCostPerUnit());
        unitDto.setQuantity(unit.getQuantity());
        unitDto.setMinQuantity(unit.getMinQuantity());
        unitDto.setUnitType(unit.getUnitType());
        unitDto.setStatsId(unit.getStatsId());
        unitDto.setUnitStats(unit.getUnitStats());
        List<Upgrade> upgrades = unit.getUpgradesList().stream()
                .map(dto -> new UpgradeDTO(dto, upgradeRepository).toEntity())
                .collect(Collectors.toList());
        unitDto.setUpgradeList(upgrades);
        return unitDto;
    }

    public void add(UnitDTO unitDTO) {
        Unit unit = convertToEntity(unitDTO);
        unitRepository.save(unit);
    }

    public List<UnitDTO> findAll() {
        return unitRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void deleteById(Integer id) {
        unitRepository.deleteById(id);
    }

    public void save(UnitDTO unit) {
        unitRepository.save(convertToEntity(unit));
    }

    private Unit convertToEntity(UnitDTO unitDto) {
        Unit unit = new Unit();
        unit.setId(unitDto.getId());
        unit.setName(unitDto.getName());
        unit.setPointsCostPerUnit(unitDto.getPointsCostPerUnit());
        unit.setQuantity(unitDto.getQuantity());
        unit.setMinQuantity(unitDto.getMinQuantity());
        unit.setUnitType(unitDto.getUnitType());
        unit.setStatsId(unitDto.getStatsId());
        unit.setUnitStats(unitDto.getUnitStats());
        return unit;
    }

}
