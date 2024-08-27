package com.jasionowicz.myarmybuilder.myLibrary;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserConfig {

    @Bean
    public MyModuleConfig config() {
        return new UserModuleConfig(true);
    }


}
