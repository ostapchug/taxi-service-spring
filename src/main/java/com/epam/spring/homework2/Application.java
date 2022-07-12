package com.epam.spring.homework2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.epam.spring.homework2.config.BeansConfig;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeansConfig.class);
        String[] beanNames = applicationContext.getBeanDefinitionNames();

        for (String beanName : beanNames) {
            System.out.println(beanName + "\n" + applicationContext.getBeanDefinition(beanName));
        }
        
        ((AbstractApplicationContext) applicationContext).close();
    }
}
