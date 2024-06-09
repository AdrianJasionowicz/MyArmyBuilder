package com.jasionowicz.myarmybuilder;

// import com.jasionowicz.myarmybuilder.unit.FrontendConsole;
import com.jasionowicz.myarmybuilder.unit.Unit;
import com.jasionowicz.myarmybuilder.unit.UnitController;
import com.jasionowicz.myarmybuilder.unit.UnitService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class MyArmyBuilderApplication {

    public static void main(String[] args) {
//        UnitService unitService = new UnitService();
//        UnitController unitController = new UnitController(unitService);
//        FrontendConsole frontendConsole = new FrontendConsole(unitService, unitController);
//        frontendConsole.run();



         SpringApplication.run(MyArmyBuilderApplication.class, args);
    }


}
