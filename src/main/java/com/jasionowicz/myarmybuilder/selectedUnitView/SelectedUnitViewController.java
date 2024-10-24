package com.jasionowicz.myarmybuilder.selectedUnitView;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SelectedUnitViewController {
    private SelectedUnitViewService selectedUnitViewService;

    public SelectedUnitViewController(SelectedUnitViewService selectedUnitViewService) {
        this.selectedUnitViewService = selectedUnitViewService;
    }

    @GetMapping("/view/units/{unitId}")
    public List<SelectedUnitView> getSelectedUnitView(@PathVariable int unitId) {
        return selectedUnitViewService.getSelectedUnitView(unitId);
    }

    @GetMapping("/view/unit/{unitId}")
    public SelectedUnitView getSelectedSingleUnitView(@PathVariable int unitId) {
        return selectedUnitViewService.getSelectedSingleUnitView(unitId);
    }

}
