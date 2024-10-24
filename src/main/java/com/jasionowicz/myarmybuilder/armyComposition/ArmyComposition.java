package com.jasionowicz.myarmybuilder.armyComposition;

import com.jasionowicz.myarmybuilder.security.model.BuilderUser;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

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
    private Integer id;
    private String roasterName;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "selectedUnit_id")
    private List<SelectedUnit> selectedUnitList;
    @ManyToOne
    @JoinColumn(name = "builderUser_id")
    private BuilderUser builderUser;
}
