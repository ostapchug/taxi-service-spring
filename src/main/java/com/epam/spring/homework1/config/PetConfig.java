package com.epam.spring.homework1.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;

import com.epam.spring.homework1.pet.Animal;
import com.epam.spring.homework1.pet.Cheetah;
import com.epam.spring.homework1.pet.Spider;

@Configuration
@ComponentScan(
        basePackages = "com.epam.spring.homework1.pet", 
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Spider.class))
public class PetConfig {

    @Bean
    @Primary
    public Animal getCheetah() {
        return new Cheetah();
    }

    @Bean
    @Qualifier("cheetah1")
    public Animal getCheetah1() {
        return new Cheetah();
    }
}
