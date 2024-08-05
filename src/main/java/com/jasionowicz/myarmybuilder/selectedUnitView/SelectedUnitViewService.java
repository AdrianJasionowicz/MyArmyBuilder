package com.jasionowicz.myarmybuilder.selectedUnitView;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnitRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class SelectedUnitViewService {
private SelectedUnitRepository selectedUnitRepository;

    public SelectedUnitViewService(SelectedUnitRepository selectedUnitRepository) {
        this.selectedUnitRepository = selectedUnitRepository;
    }


    public List<SelectedUnitView> getSelectedUnitView(int unitId) {
        return selectedUnitRepository.findAllByUnitId(unitId).stream()
                .map(selectedUnit -> new SelectedUnitView(selectedUnit))
                .toList();
    }
}
