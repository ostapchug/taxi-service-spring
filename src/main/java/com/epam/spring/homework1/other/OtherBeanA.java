package com.epam.spring.homework1.other;

import org.springframework.stereotype.Component;

import com.epam.spring.homework1.beans.BeanA;

@Component
public class OtherBeanA {

    private BeanA beanA;

    public OtherBeanA(BeanA beanA) {
	this.beanA = beanA;
	System.out.println(this.getClass().getSimpleName() + ", " + beanA.getClass().getSimpleName()
		+ " was injected through the constructor");
    }
}
