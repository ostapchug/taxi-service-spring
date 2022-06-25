package com.epam.spring.homework2.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class BeanA implements Bean, InitializingBean, DisposableBean {
	private String name;
	private int value;
	
	public BeanA(){
		System.out.println("inside BeanA Constructor()");
	}

	@Override
	public String toString() {
		return "BeanA [name=" + name + ", value=" + value + "]";
	}

	public void destroy() throws Exception {
		System.out.println("inside BeanA.destroy()");
	}

	public void afterPropertiesSet() throws Exception {
		System.out.println("inside BeanA.afterPropertiesSet()");
	}

	public void validate() {
		if(name == null || value < 1) {
			System.out.println(this.getClass().getSimpleName() + " is not valid");
		}
	}

}
