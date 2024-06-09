package com.jasionowicz.myarmybuilder.armyComposition;


import lombok.Getter;
import org.springframework.stereotype.Service;


@Service
public class ArmyCompositionService {

    @Getter
    private final ArmyComposition armyComposition = new ArmyComposition();

    public void addToCalculate(double points, int quantity, String unitType) {
        double totalPointsForUnit = points * quantity;

        switch (unitType) {
            case "Lord":
                armyComposition.setTotalLords(armyComposition.getTotalLords() + totalPointsForUnit);
                break;
            case "Hero":
                armyComposition.setTotalHeroes(armyComposition.getTotalHeroes() + totalPointsForUnit);
                break;
            case "Core":
                armyComposition.setTotalCore(armyComposition.getTotalCore() + totalPointsForUnit);
                break;
            case "Special":
                armyComposition.setTotalSpecial(armyComposition.getTotalSpecial() + totalPointsForUnit);
                break;
            case "Rare":
                armyComposition.setTotalRare(armyComposition.getTotalRare() + totalPointsForUnit);
                break;
        }

        armyComposition.setTotalPoints(armyComposition.getTotalPoints() + totalPointsForUnit);
    }

    public void resetPoints() {
        armyComposition.setTotalPoints(0);
        armyComposition.setTotalSpecial(0);
        armyComposition.setTotalLords(0);
        armyComposition.setTotalCore(0);
        armyComposition.setTotalHeroes(0);
        armyComposition.setTotalRare(0);
    }
}
