package com.epam.spring.homework2.beans;

public class BeanB extends Bean {

    public BeanB() {
    }

    public BeanB(String name, int value) {
        super(name, value);
    }

    private void customInitMethod() {
        System.out.println("inside BeanB.customInitMethod()");
    }

    private void otherCustomInitMethod() {
        System.out.println("inside BeanB.otherCustomInitMethod()");
    }

    private void customDestroyMethod() {
        System.out.println("inside BeanB.customDestroyMethod()");
    }
}
