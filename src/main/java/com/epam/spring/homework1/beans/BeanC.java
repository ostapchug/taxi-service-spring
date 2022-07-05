package com.epam.spring.homework1.beans;

import org.springframework.stereotype.Component;

@Component
public class BeanC {

    BeanC() {
	System.out.println(this.getClass().getSimpleName());
    }
}
