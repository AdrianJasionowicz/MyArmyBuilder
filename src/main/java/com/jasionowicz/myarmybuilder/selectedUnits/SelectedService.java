package com.jasionowicz.myarmybuilder.selectedUnits;

import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgrades;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradesDTO;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgradesRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SelectedService {


    @Autowired
    private SelectedUpgradesRepository selectedUpgradesRepository;
    @Autowired
    private SelectedUnitRepository selectedUnitRepository;
    @Autowired
    private SelectedUnit selectedUnit;
    private SelectedUnitDTO selectedUnitDTO;
    @Autowired
    private SelectedUpgrades selectedUpgrades;

    public SelectedService() {
    }

    @PostConstruct
    public void clear() {
        selectedUnitRepository.deleteAll();
    }


    public ResponseEntity<String> removeUnitById(int id) {
        SelectedUnit selectedUnit = selectedUnitRepository.findById(id).orElse(null);
        if (selectedUnit != null) {
            selectedUnitRepository.delete(selectedUnit);
            return ResponseEntity.ok("Done");
        }
        return ResponseEntity.badRequest().body(null);
    }


    public ResponseEntity<String> increaseUnitQuantity(Integer id) {
        Optional<SelectedUnit> selectedUnit = selectedUnitRepository.findById(id);
        if (selectedUnit.isPresent()) {
            SelectedUnit unit = selectedUnit.get();
            unit.setQuantity(unit.getQuantity() + 1);
            selectedUnitRepository.save(unit);
            return ResponseEntity.ok("Quantity increased");
        }
        return ResponseEntity.badRequest().body("Unit not found");
    }

    public ResponseEntity<String> decreaseUnitQuantity(Integer id) {
        Optional<SelectedUnit> optionalSelectedUnit = selectedUnitRepository.findById(id);

        if (optionalSelectedUnit.isPresent()) {
            SelectedUnit selectedUnit = optionalSelectedUnit.get();

            if (selectedUnit.getQuantity() > 0) {
                selectedUnit.setQuantity(selectedUnit.getQuantity() - 1);
                selectedUnitRepository.save(selectedUnit);

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


}
