package com.example.taxiservicespring.service.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.taxiservicespring.service.exception.EntityNotFoundException;
import com.example.taxiservicespring.service.model.Category;
import com.example.taxiservicespring.service.repository.CategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CategoryRepositoryImpl implements CategoryRepository {
    private final List<Category> categories = new ArrayList<>();

    CategoryRepositoryImpl() {
        Category category = new Category();
        category.setId(1);
        category.setName("Economy");
        category.setPrice(BigDecimal.valueOf(25));
        categories.add(category);

        category = new Category();
        category.setId(2);
        category.setName("Standard");
        category.setPrice(BigDecimal.valueOf(40));
        categories.add(category);

        category = new Category();
        category.setId(3);
        category.setName("Comfort");
        category.setPrice(BigDecimal.valueOf(50));
        categories.add(category);
    }

    @Override
    public Category find(int id) {
        log.info("find category by id {}", id);
        return categories.stream()
                .filter(category -> category.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Category is not found!"));
    }

    @Override
    public List<Category> getAll() {
        log.info("find all categories");
        return new ArrayList<>(categories);
    }
}
