package com.example.demo1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter//for getter setter lombok
@AllArgsConstructor
@NoArgsConstructor
//post is parent for commnt and category is parent for post
@Entity
@Table(name="posts", uniqueConstraints={@UniqueConstraint(columnNames = {"title"}) })
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name="title",nullable = false)
    private String title;
    @Column(name="description",nullable = false)
    private String description;
    @Column(name="content",nullable = false)
    private String content;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category_id")

    private Category category;

    @OneToMany(mappedBy="post",cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JsonManagedReference
    private Set<Comment> comments=new HashSet<>();
}
