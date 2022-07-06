package com.epam.spring.homework2.beans;

public class BeanF implements Bean {

    private String name;
    private int value;

    public BeanF() {
        System.out.println("inside BeanF Constructor()");
    }

    @Override
    public String toString() {
        return "BeanF [name=" + name + ", value=" + value + "]";
    }

    public void validate() {
        if (name == null || value < 1) {
            System.out.println(this.getClass().getSimpleName() + " is not valid");
        }
    }
}
