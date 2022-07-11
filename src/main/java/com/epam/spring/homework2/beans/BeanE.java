package com.epam.spring.homework2.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class BeanE extends AbstractBean {

    public BeanE() {
    }

    public BeanE(String name, int value) {
        super(name, value);
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("inside BeanE.postConstruct()");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("inside BeanE.preDestroy()");
    }
}
