package com.kgc.travel.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kgc.travel.domain.Category;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<Category> findAll() throws IOException;
}
