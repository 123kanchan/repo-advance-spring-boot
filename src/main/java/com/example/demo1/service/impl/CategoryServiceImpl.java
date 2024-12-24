package com.example.demo1.service.impl;

import com.example.demo1.entity.Category;
import com.example.demo1.exception.ResourceNotfoundException;
import com.example.demo1.payload.CategoryDTO;
import com.example.demo1.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository repo;
    @Autowired
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository repo, ModelMapper modelMapper) {
        this.repo = repo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO dto) {
        Category category=modelMapper.map(dto,Category.class);
       Category savecat= repo.save(category);

        return modelMapper.map(savecat,CategoryDTO.class);
    }

    @Override
    @GetMapping("/get")
    public CategoryDTO getCateory(long id) {
        Category category=repo.findById(id).orElseThrow(()->new ResourceNotfoundException("category","id",id));
        return modelMapper.map(category,CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = repo.findAll();
        return categories.stream().map((category) -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO update(CategoryDTO dto, long id) {
        Category category=repo.findById(id).orElseThrow(()->new ResourceNotfoundException("category","id",id));
        category.setName(dto.getName());
   //     category.setId(dto.getId());
        category.setDescription(dto.getDescription());
       Category updated =repo.save(category);
      return  modelMapper.map(updated,CategoryDTO.class);
    }

    @Override
    public void delete(long id) {
        Category cat=repo.findById(id).orElseThrow(()->new ResourceNotfoundException("category","id",id));
        repo.delete(cat);
    }
}
