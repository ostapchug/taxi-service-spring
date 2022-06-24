package com.epam.spring.homework1.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.spring.homework1.beans.BeanB;

@Component
public class OtherBeanB {
	
	private BeanB beanB;
	
	@Autowired
	public void setBeanB(BeanB beanB) {
		this.beanB = beanB;
		System.out.println(this.getClass().getSimpleName() + ", " + beanB.getClass().getSimpleName() + " was injected through the setter");
	}
	
	

}
