package com.jasionowicz.myarmybuilder.selectedUpgrades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jasionowicz.myarmybuilder.selectedUnits.SelectedUnit;
import com.jasionowicz.myarmybuilder.upgrade.Upgrade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SelectedUpgrades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int quantity;
    private boolean selected;
    private Double totalCost;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SelectedUnits_id")
    @JsonBackReference
    private SelectedUnit selectedUnit;
    @ManyToOne
    @JoinColumn(name = "upgrade_id")
    private Upgrade upgrade;


    public SelectedUpgrades(Upgrade upgrade, SelectedUnit selectedUnit) {
        this.selectedUnit = selectedUnit;
        this.upgrade = upgrade;
    }

    public SelectedUpgrades(SelectedUpgradesDTO dto, SelectedUnit selectedUnit) {
        this.id = dto.getId();
        this.quantity = dto.getQuantity();
        this.selected = dto.isSelected();
        this.totalCost = dto.getTotalCost();
        this.selectedUnit = selectedUnit;
    }

    public SelectedUpgrades(SelectedUpgrades selectedUpgrades) {
    }



}
