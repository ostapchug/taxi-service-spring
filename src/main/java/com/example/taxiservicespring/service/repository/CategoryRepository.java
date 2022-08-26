package com.example.taxiservicespring.service.repository;

import java.util.List;

import com.example.taxiservicespring.service.model.Category;

public interface CategoryRepository {

    Category findById(int id);

    List<Category> findAll();
}
