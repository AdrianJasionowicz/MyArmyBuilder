package com.jasionowicz.myarmybuilder.unit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@ToString

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UnitStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Integer m;
    private Integer i;

    public UnitStats(Integer ws, Integer bs, Integer s, Integer t, Integer w, Integer a, Integer ld, Integer basicSave, Integer wardSave, Integer m, Integer i) {
        this.ws = ws;
        this.bs = bs;
        this.s = s;
        this.t = t;
        this.w = w;
        this.a = a;
        this.ld = ld;
        this.basicSave = basicSave;
        this.wardSave = wardSave;
        this.m = m;
        this.i = i;
    }
}
