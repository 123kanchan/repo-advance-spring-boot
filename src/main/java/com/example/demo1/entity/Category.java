package com.example.demo1.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="category")

    ///1 category has many post @onetomany mapped by=category measns in post table category column will come  and captegory table become parent now
    //CATGORY
    //ID NAME DES
    //POST
    //NAME POSTID CATEGORYID
    //SO if removing anything from category table from post also remove thats why casxade-ALL
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @OneToMany(mappedBy = "category",cascade=CascadeType.ALL,orphanRemoval = true)
    private List<Post> post;
}
