package com.epam.spring.homework1;

import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epam.spring.homework1.config.BeansConfig;
import com.epam.spring.homework1.pet.Animal;
import com.epam.spring.homework1.pet.Cheetah;
import com.epam.spring.homework1.pet.Pet;

public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeansConfig.class);

        Pet pet = applicationContext.getBean(Pet.class);
        pet.printPets();

        /**
         * Without @Primary annotation would be an error with message 
         * "expected single matching bean but found 3"
         */
        Animal animalByType = applicationContext.getBean(Cheetah.class);

        Animal animalByName = (Animal) applicationContext.getBean("getCheetah");

        Animal animalByQualifier = BeanFactoryAnnotationUtils
                .qualifiedBeanOfType(applicationContext.getAutowireCapableBeanFactory(), Cheetah.class, "cheetah1");
    }
}
