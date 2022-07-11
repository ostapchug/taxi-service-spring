package com.epam.spring.homework2.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("inside MyBeanPostProcessor.postProcessBeforeInitialization() for :" + beanName);

        if (bean instanceof AbstractBean) {
            ((AbstractBean) bean).validate();
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("inside MyBeanPostProcessor.postProcessAfterInitialization() for :" + beanName);
        return bean;
    }
}
