package com.epam.spring.homework1.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.spring.homework1.beans.BeanC;

@Component
public class OtherBeanC {

    @Autowired
    private BeanC beanC;

    OtherBeanC() {
	System.out.println(beanC);
    }
}
