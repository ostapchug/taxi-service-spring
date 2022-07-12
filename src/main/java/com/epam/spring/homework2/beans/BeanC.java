package com.epam.spring.homework2.beans;

public class BeanC extends Bean {   

    public BeanC() {
    }

    public BeanC(String name, int value) {
        super(name, value);
    }

    private void customInitMethod() {
        System.out.println("inside BeanC.customInitMethod()");
    }

    private void customDestroyMethod() {
        System.out.println("inside BeanC.customDestroyMethod()");
    }
}
