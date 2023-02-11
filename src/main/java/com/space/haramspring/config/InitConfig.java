package com.space.haramspring.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitConfig {

    public void init(){

    }

    @PostConstruct
    public void initData(){

    }
}
