package com.jasionowicz.myarmybuilder.armyComposition.selectedStats;

import com.jasionowicz.myarmybuilder.unit.UnitStats;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SelectedStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer m;
    private Integer ws;
    private Integer bs;
    private Integer s;
    private Integer t;
    private Integer w;
    private Integer i;
    private Integer a;
    private Integer ld;
    private Integer basicSave;
    private Integer wardSave;

    public SelectedStats(UnitStats unitStats) {
        this.m = unitStats.getM();
        this.ws = unitStats.getWs();
        this.bs = unitStats.getBs();
        this.s = unitStats.getS();
        this.t = unitStats.getT();
        this.w = unitStats.getW();
        this.i = unitStats.getI();
        this.a = unitStats.getA();
        this.ld = unitStats.getLd();
        this.basicSave = unitStats.getBasicSave();
        this.wardSave = unitStats.getWardSave();
    }

}
