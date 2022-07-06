package com.epam.spring.homework2.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("getBeanB");

        System.out.println("Setting BeanB initMethod parameter to 'otherCustomInitMethod'");
        beanDefinition.setInitMethodName("otherCustomInitMethod");
    }
}
