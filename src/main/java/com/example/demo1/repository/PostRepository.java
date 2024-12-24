package com.example.demo1.repository;

import com.example.demo1.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post,Long> {
    public List<Post> findByCategoryId(Long id);
}
