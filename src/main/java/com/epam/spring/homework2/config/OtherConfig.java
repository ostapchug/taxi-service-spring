package com.epam.spring.homework2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;

import com.epam.spring.homework2.beans.BeanA;
import com.epam.spring.homework2.beans.BeanE;
import com.epam.spring.homework2.beans.BeanF;

@Configuration
public class OtherConfig {

    @Bean
    @DependsOn("getBeanC")
    public BeanA getBeanA() {
        return new BeanA();
    }

    @Bean
    @DependsOn("getBeanC")
    public BeanE getBeanE() {
        return new BeanE();
    }

    @Bean
    @Lazy
    @DependsOn("getBeanC")
    public BeanF getBeanF() {
        return new BeanF();
    }
}
