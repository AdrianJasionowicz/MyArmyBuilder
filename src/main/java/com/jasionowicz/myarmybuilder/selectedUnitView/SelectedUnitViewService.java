package com.jasionowicz.myarmybuilder.selectedUnitView;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnitRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class SelectedUnitViewService {
    private final SelectedUnit selectedUnit;
    private SelectedUnitRepository selectedUnitRepository;

    public SelectedUnitViewService(SelectedUnitRepository selectedUnitRepository, SelectedUnit selectedUnit) {
        this.selectedUnitRepository = selectedUnitRepository;
        this.selectedUnit = selectedUnit;
    }


    public List<SelectedUnitView> getSelectedUnitView(int unitId) {
        return selectedUnitRepository.findAllByUnitId(unitId).stream()
                .map(selectedUnit -> new SelectedUnitView(selectedUnit))
                .toList();
    }

    public SelectedUnitView getSelectedSingleUnitView(int unitId) {
        return new SelectedUnitView(selectedUnitRepository.getReferenceById(unitId));
    }

    public List<SelectedUnitView> getSelectedUnitViews() {
        List<SelectedUnitView> selectedUnitViews = new ArrayList<>();
        List<SelectedUnit> selectedUnits = selectedUnitRepository.findAll();
        for (SelectedUnit unit : selectedUnits) {
            if (selectedUnits.contains(selectedUnit)) {
                selectedUnitViews.add(new SelectedUnitView(unit));
            }
        }
        return selectedUnitViews;
    }


}
