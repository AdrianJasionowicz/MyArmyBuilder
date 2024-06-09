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


    private UnitStats unitStats;

    public UnitStatsDTO(UnitStats unitStats) {
        this.unitStats = unitStats;
        getEntityToDTO();
    }

    public void getEntityToDTO() {
        ws = unitStats.getWs();
        bs = unitStats.getBs();
        s = unitStats.getS();
        t = unitStats.getT();
        w = unitStats.getW();
        a = unitStats.getA();
        ld = unitStats.getLd();
        basicSave = unitStats.getBasicSave();
        wardSave = unitStats.getWardSave();
    }

    public void getDTOToEntity() {
        unitStats.setWs(ws);
        unitStats.setBs(bs);
        unitStats.setS(s);
        unitStats.setT(t);
        unitStats.setW(w);
        unitStats.setA(a);
        unitStats.setLd(ld);
        unitStats.setBasicSave(basicSave);
        unitStats.setWardSave(wardSave);
    }


}

