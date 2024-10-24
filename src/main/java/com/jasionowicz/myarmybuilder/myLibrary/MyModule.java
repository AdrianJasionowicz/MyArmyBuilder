package com.jasionowicz.myarmybuilder.myLibrary;

import jakarta.annotation.PostConstruct;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyModule {

    private MyModuleConfig myModuleConfig;

    public MyModule(@Autowired(required = false) MyModuleConfig myModuleConfig) {
        this.myModuleConfig = myModuleConfig;
        if (myModuleConfig == null) {
            this.myModuleConfig = new MyModuleDefaultImpl();
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("Wladuje biblioteke");
        if (myModuleConfig.shouldLoad()) {
            System.out.println("Sciagam!");
        }
    }


 /*
    private ApplicationContext applicationContext;

    public MyModule(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        Context
        // czy wlaczyl?
        System.out.println("Wladuje biblioteke");
        if (myModuleConfig.shouldLoad()) {
            System.out.println("Sciagam!");
        }
    }*/
}
