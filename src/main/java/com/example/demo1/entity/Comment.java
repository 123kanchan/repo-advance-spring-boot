package com.example.demo1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String body;
    @ManyToOne(fetch=FetchType.LAZY)//many is comment
   @JoinColumn(name="post_id",nullable = false)
    @JsonBackReference
    private Post post; //isi nam se Post mai jana jayega mapppedby mai


}
