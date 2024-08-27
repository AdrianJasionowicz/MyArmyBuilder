package com.jasionowicz.myarmybuilder.myLibrary;


public class MyModuleDefaultImpl implements MyModuleConfig {
    @Override
    public boolean shouldLoad() {
        return false;
    }
}
