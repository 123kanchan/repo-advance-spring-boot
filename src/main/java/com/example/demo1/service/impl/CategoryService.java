package com.example.demo1.service.impl;

import com.example.demo1.payload.CategoryDTO;

import java.util.List;

public interface CategoryService {
    public CategoryDTO addCategory(CategoryDTO dto);
    public  CategoryDTO getCateory(long id);
    public List<CategoryDTO> getAllCategory();
        public CategoryDTO update(CategoryDTO dto,long id);
        public void delete(long id);
}
