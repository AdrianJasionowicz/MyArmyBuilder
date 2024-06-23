package com.jasionowicz.myarmybuilder.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnitStatsDTO {
    private int id;
    private Integer ws;
    private Integer bs;
    private Integer s;
    private Integer t;
    private Integer w;
    private Integer a;
    private Integer ld;
    private Integer basicSave;
    private Integer wardSave;

    public UnitStatsDTO(UnitStats unitStats) {
        this.id = unitStats.getId();
        this.ws = unitStats.getWs();
        this.bs = unitStats.getBs();
        this.s = unitStats.getS();
        this.t = unitStats.getT();
        this.w = unitStats.getW();
        this.a = unitStats.getA();
        this.ld = unitStats.getLd();
        this.basicSave = unitStats.getBasicSave();
        this.wardSave = unitStats.getWardSave();
    }

    public void copyToEntity(UnitStats unitStats) {
        unitStats.setWs(this.ws);
        unitStats.setBs(this.bs);
        unitStats.setS(this.s);
        unitStats.setT(this.t);
        unitStats.setW(this.w);
        unitStats.setA(this.a);
        unitStats.setLd(this.ld);
        unitStats.setBasicSave(this.basicSave);
        unitStats.setWardSave(this.wardSave);
    }
}
