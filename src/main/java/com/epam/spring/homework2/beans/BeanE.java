package com.epam.spring.homework2.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class BeanE implements Bean {
    
    private String name;
    private int value;

    public BeanE() {
	System.out.println("inside BeanE Constructor()");
    }

    @Override
    public String toString() {
	return "BeanE [name=" + name + ", value=" + value + "]";
    }

    @PostConstruct
    public void postConstruct() {
	System.out.println("inside BeanE.postConstruct()");
    }

    @PreDestroy
    public void preDestroy() {
	System.out.println("inside BeanE.preDestroy()");
    }

    public void validate() {
	if (name == null || value < 1) {
	    System.out.println(this.getClass().getSimpleName() + " is not valid");
	}
    }
}
