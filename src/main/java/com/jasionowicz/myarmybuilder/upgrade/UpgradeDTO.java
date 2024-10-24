package com.jasionowicz.myarmybuilder.upgrade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Component
public class UpgradeDTO {
    private int id;
    private String name;
    private double pointsCost;
    private String description;
    private String upgradeType;

}
