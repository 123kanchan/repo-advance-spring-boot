package com.example.demo1.controller;

import com.example.demo1.payload.CategoryDTO;
import com.example.demo1.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CaegoryController {
    @Autowired
    private CategoryService service;

    public CaegoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO>addcategory(@RequestBody  CategoryDTO dto){
       CategoryDTO dto1= service.addCategory(dto);
       return new ResponseEntity<>(dto1, HttpStatus.CREATED);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryDTO> getbyid(@PathVariable  long id){
       CategoryDTO dto= service.getCateory(id);
       return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>>getallcategories(){
        return ResponseEntity.ok(service.getAllCategory());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> update (@RequestBody CategoryDTO dto,@PathVariable long id){
        return new ResponseEntity<>((service.update(dto,id)),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable long id){
        service.delete(id);
        return ResponseEntity.ok("deleted");
    }

}
