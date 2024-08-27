package com.jasionowicz.myarmybuilder.myLibrary;


public class UserModuleConfig implements MyModuleConfig{
private boolean shouldLoad;

    public UserModuleConfig(boolean shouldLoad) {
        this.shouldLoad = shouldLoad;
    }

    @Override
    public boolean shouldLoad() {
        return shouldLoad;
    }
}