package com.jasionowicz.myarmybuilder.exceptions;

public class UpgradeAlreadySelectedException extends RuntimeException {
    public UpgradeAlreadySelectedException(String message) {
        super(message);
    }
}