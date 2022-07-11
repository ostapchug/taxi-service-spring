package com.epam.spring.homework2.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class BeanA extends AbstractBean implements InitializingBean, DisposableBean {

    public BeanA() {
    }

    public BeanA(String name, int value) {
        super(name, value);
    }

    public void destroy() throws Exception {
        System.out.println("inside BeanA.destroy()");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("inside BeanA.afterPropertiesSet()");
    }
}
