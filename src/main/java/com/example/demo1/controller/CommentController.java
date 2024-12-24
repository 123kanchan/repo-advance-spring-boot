package com.example.demo1.controller;

import com.example.demo1.payload.CommentDTO;
import com.example.demo1.service.CommentSevice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments/v1")
public class CommentController {
@Autowired
    CommentSevice service;

    @PostMapping("/create/{id}")
    public ResponseEntity<CommentDTO> Createcomment(@PathVariable("id") Long postid,@Valid @RequestBody CommentDTO dto){
      CommentDTO dto1=  service.createComment(postid,dto);
      return  new ResponseEntity<>(dto1, HttpStatus.CREATED);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<List<CommentDTO>> getAll(@PathVariable("id") long postId){
        return new ResponseEntity<>(service.getAllComments(postId),HttpStatus.OK);
    }

    @GetMapping("/getcomment/{pid}/{cid}")
    public ResponseEntity<CommentDTO> getcommentbyid(@PathVariable("pid") long postid,@PathVariable("cid") long commentid){
        return new ResponseEntity<>(service.getCommentById(postid,commentid),HttpStatus.OK);
    }

    @PutMapping("/update/{pid}/{cid}")
    public ResponseEntity<CommentDTO >updateComment(@PathVariable Long pid,@PathVariable Long cid,@Valid @RequestBody CommentDTO dt){
return new ResponseEntity<>(service.updateCommentbyId(pid,cid,dt),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{pid}/{cid}")
    public ResponseEntity<String > delete(@PathVariable Long pid, @PathVariable Long cid){
        service.Delete(pid,cid);
        return new ResponseEntity<>("deleted succesfully",HttpStatus.OK);
    }
}


