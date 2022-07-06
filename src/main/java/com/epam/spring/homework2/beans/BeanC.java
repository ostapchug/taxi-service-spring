package com.epam.spring.homework2.beans;

public class BeanC implements Bean {

    private String name;
    private int value;

    public BeanC() {
        System.out.println("inside BeanC Constructor()");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BeanC [name=" + name + ", value=" + value + "]";
    }

    private void customInitMethod() {
        System.out.println("inside BeanC.customInitMethod()");
    }

    private void customDestroyMethod() {
        System.out.println("inside BeanC.customDestroyMethod()");
    }

    public void validate() {
        if (name == null || value < 1) {
            System.out.println(this.getClass().getSimpleName() + " is not valid");
        }
    }
}
