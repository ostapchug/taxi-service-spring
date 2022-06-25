package com.epam.spring.homework2.beans;

public class BeanD implements Bean {
	private String name;
	private int value;
	
	public BeanD(){
		System.out.println("inside BeanD Constructor()");
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "BeanD [name=" + name + ", value=" + value + "]";
	}
	
	private void customInitMethod() {
		System.out.println("inside BeanD.customInitMethod()");
	}
	
	private void customDestroyMethod() {
		System.out.println("inside BeanD.customDestroyMethod()");
	}

	public void validate() {
		if(name == null || value < 1) {
			System.out.println(this.getClass().getSimpleName() + " is not valid");
		}
	}
}
