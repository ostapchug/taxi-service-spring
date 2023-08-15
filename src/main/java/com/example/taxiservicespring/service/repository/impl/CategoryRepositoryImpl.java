package com.example.taxiservicespring.service.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.example.taxiservicespring.service.exception.EntityNotFoundException;
import com.example.taxiservicespring.service.model.Category;
import com.example.taxiservicespring.service.repository.CategoryRepository;
import com.example.taxiservicespring.util.DataGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private final List<Category> categories = DataGenerator.createCategories();

    @Override
    public Category findById(int id) {
        log.info("find category by id {}", id);
        return categories.stream()
                .filter(category -> category.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Category is not found!"));
    }

    @Override
    public List<Category> findAll() {
        log.info("find all categories");
        return new ArrayList<>(categories);
    }
}
