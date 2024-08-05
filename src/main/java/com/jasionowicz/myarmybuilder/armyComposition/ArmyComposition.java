package com.jasionowicz.myarmybuilder.armyComposition;

import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import com.jasionowicz.myarmybuilder.selectedUpgrades.SelectedUpgrade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component
public class ArmyComposition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @OneToMany
    @JoinColumn(name = "selectedUnit_id")
    List<SelectedUnit> selectedUnitList = new ArrayList<>();


}
