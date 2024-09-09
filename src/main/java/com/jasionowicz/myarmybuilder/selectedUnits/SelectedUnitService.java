package com.jasionowicz.myarmybuilder.selectedUnits;

import com.jasionowicz.myarmybuilder.selectedStats.SelectedStatsRepository;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgrade;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeRepository;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SelectedUnitService {


    @Autowired
    private SelectedUnitRepository selectedUnitRepository;
    @Autowired
    private SelectedUnit selectedUnit;
    private SelectedUnitDTO selectedUnitDTO;
    @Autowired
    private SelectedUpgradeService selectedUpgradeService;
    private SelectedStatsRepository selectedStatsRepository;
    private SelectedUpgradeRepository selectedUpgradeRepository;

    public SelectedUnitService(SelectedStatsRepository selectedStatsRepository, SelectedUpgradeRepository selectedUpgradeRepository) {
        this.selectedStatsRepository = selectedStatsRepository;
        this.selectedUpgradeRepository = selectedUpgradeRepository;
    }


    public void removeUnitById(int id) {
        selectedUnitRepository.deleteById(id);
    }


    public ResponseEntity<String> increaseUnitQuantity(Integer id) {
        Optional<SelectedUnit> selectedUnit = selectedUnitRepository.findById(id);
        if (selectedUnit.isPresent()) {
            SelectedUnit unit = selectedUnit.get();
            unit.setQuantity(unit.getQuantity() + 1);
            selectedUnitRepository.save(unit);
            selectedUpgradeService.checkUpgradesQuantities(id, unit.getQuantity());

            return ResponseEntity.ok("Quantity increased");
        }
        return ResponseEntity.badRequest().body("Unit not found");
    }

    public ResponseEntity<String> decreaseUnitQuantity(Integer id) {
        Optional<SelectedUnit> optionalSelectedUnit = selectedUnitRepository.findById(id);

        if (optionalSelectedUnit.isPresent()) {
            SelectedUnit selectedUnit = optionalSelectedUnit.get();

            if (selectedUnit.getQuantity() > 0) {
                if (selectedUnit.getQuantity() == selectedUnit.getUnit().getMinQuantity()) {
                    return ResponseEntity.badRequest().body("Cant decrease quantity");
                }
                selectedUnit.setQuantity(selectedUnit.getQuantity() - 1);
                selectedUnitRepository.save(selectedUnit);
                selectedUpgradeService.checkUpgradesQuantities(id, selectedUnit.getQuantity());

                return ResponseEntity.ok("Quantity decreased");
            } else {
                return ResponseEntity.badRequest().body("Quantity cannot be less than zero");
            }
        } else {
            return ResponseEntity.badRequest().body("Unit not found");
        }
    }

    public List<SelectedUnit> getSelectedUnits() {
        return selectedUnitRepository.findAll();
    }


    public void saveSelectedUnit(SelectedUnit selectedUnit) {

        if (selectedUnit.getSelectedStats() != null) {
            selectedStatsRepository.save(selectedUnit.getSelectedStats());
        }

        selectedUnitRepository.save(selectedUnit);

        List<SelectedUpgrade> selectedUpgradeList = selectedUnit.getSelectedUpgrades().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!selectedUpgradeList.isEmpty()) {
            selectedUpgradeService.addFreeUpgradesAndSpecialRaceUpgrades(selectedUnit.getId());
            selectedUpgradeRepository.saveAll(selectedUpgradeList);
        }
    }
}
