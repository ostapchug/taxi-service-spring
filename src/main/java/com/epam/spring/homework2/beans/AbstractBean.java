package com.epam.spring.homework2.beans;

public abstract class AbstractBean implements Validate {
    private String name;
    private int value;

    public AbstractBean() {
        System.out.println("inside " + this.getClass().getSimpleName() + " Constructor()");
    }

    public AbstractBean(String name, int value) {
        this.name = name;
        this.value = value;
        System.out.println("inside " + this.getClass().getSimpleName() + " parameterized Constructor()");
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[name=" + name + ", value=" + value + "]";
    }

    @Override
    public void validate() {
        if (name == null || value < 1) {
            System.out.println(this.getClass().getSimpleName() + " is not valid");
        }
    }
}
