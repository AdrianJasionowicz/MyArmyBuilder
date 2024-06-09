/* package com.jasionowicz.myarmybuilder.unit;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
@Component
public class FrontendConsole  {

    private UnitService unitService;
    private UnitController unitController;

    public FrontendConsole(UnitService unitService, UnitController unitController) {
        this.unitService = unitService;
        this.unitController = unitController;
        System.out.println("Making Frontend Console");
    }

@PostConstruct
    public void run(){
        Thread thread = new Thread(()->{
            do {
                Scanner scanner = new Scanner(System.in);
                System.out.println("[1] dodaj jednostkę, [2] wyswietl jednostki");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        System.out.println("Podaj nazwe jednostki");
                        String name = scanner.nextLine();
                        Unit unit = new Unit(name);
                        unitController.addUnit(unit);
                        break;
                    case 2:
                        List<Unit> units = unitController.getUnits();
                        for (Unit aUnit : units) {
                            System.out.println(aUnit);
                        }
                }

            } while (true);
        });
        thread.start();

    }
}

 */