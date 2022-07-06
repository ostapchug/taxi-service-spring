package com.epam.spring.homework2.beans;

public class BeanB implements Bean {

    private String name;
    private int value;

    public BeanB() {
        System.out.println("inside BeanB Constructor()");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BeanB [name=" + name + ", value=" + value + "]";
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

    public void validate() {
        if (name == null || value < 1) {
            System.out.println(this.getClass().getSimpleName() + " is not valid");
        }
    }
}
