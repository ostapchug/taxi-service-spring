package com.epam.spring.homework1.pet;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Pet {

    private List<Animal> pets;

    public Pet(List<Animal> pets) {
        this.pets = pets;
    }

    public void printPets() {
        pets.forEach(pet -> System.out.println(pet.getName()));
    }
}
