package com.epam.spring.homework2.beans;

public class BeanD extends Bean {
    
    public BeanD() {
    }

    public BeanD(String name, int value) {
        super(name, value);
    }

    private void customInitMethod() {
        System.out.println("inside BeanD.customInitMethod()");
    }

    private void customDestroyMethod() {
        System.out.println("inside BeanD.customDestroyMethod()");
    }
}
