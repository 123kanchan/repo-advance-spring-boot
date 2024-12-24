package com.example.demo1.repository;

import com.example.demo1.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//not needed its fine to remove also
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(long id);
}
