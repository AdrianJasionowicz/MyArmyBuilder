package com.jasionowicz.myarmybuilder.unitStats;

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
}
