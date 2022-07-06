package com.epam.spring.homework2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.epam.spring.homework2.beans.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        String[] beanNames = applicationContext.getBeanDefinitionNames();

        for (String beanName : beanNames) {
            System.out.println(beanName);
        }

        String[] myBeanNames = applicationContext.getBeanNamesForType(Bean.class);

        for (String myBeanName : myBeanNames) {
            System.out.println(applicationContext.getBean(myBeanName));
        }
    }
}
