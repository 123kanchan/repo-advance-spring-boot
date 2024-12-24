package com.example.demo1.service.impl;

import com.example.demo1.entity.Comment;
import com.example.demo1.entity.Post;
import com.example.demo1.exception.BlogAPIException;
import com.example.demo1.exception.ResourceNotfoundException;
import com.example.demo1.payload.CommentDTO;
import com.example.demo1.payload.PostDto;
import com.example.demo1.repository.CommentRepository;
import com.example.demo1.repository.PostRepository;
import com.example.demo1.service.CommentSevice;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentSevice {
    private CommentRepository repo;
    private PostRepository repo1;

    //for model mapper implemetnation so that dont need to convert entity to dto or viceversea manually
    ModelMapper map;
    public CommentServiceImpl(CommentRepository repo,PostRepository repo1,ModelMapper map) {
        this.repo = repo;
        this.repo1=repo1;
        this.map=map;
    }

    //reating entity to dto
    private CommentDTO maptoDto(Comment newComment){
        CommentDTO dto1=  map.map(newComment,CommentDTO.class);
      /*  CommentDTO dto1=new CommentDTO();
      dto1.setId(newComment.getId());
      dto1.setBody(newComment.getBody());
      dto1.setEmail(newComment.getEmail());
      dto1.setName(newComment.getName());*/
        return dto1;
    }

    //creting dto to entity
    private Comment maptoentity(CommentDTO dto){
        Comment comment =map.map(dto,Comment.class);
     /* Comment comment =new Comment();
        comment.setId(dto.getId());
        comment.setBody(dto.getBody());
        comment.setEmail(dto.getEmail());
        comment.setName(dto.getName());*/
        return comment;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO comment) {
        Comment comment1= maptoentity(comment);
       Post post= repo1.findById(postId).orElseThrow(()->new ResourceNotfoundException("Post","id",postId));
       //set post to cmmen entity
       comment1.setPost(post);
       repo.save(comment1);
        CommentDTO dto=maptoDto(comment1);
        return dto;
    }

    @Override
    public List< CommentDTO >getAllComments(long postId) {
    List<Comment > comment=repo.findByPostId(postId);
    return comment.stream().map(comments->maptoDto(comments)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long postId,Long commentId) {
       Comment comment= repo.findById(commentId).orElseThrow(()->new ResourceNotfoundException("CommentId","id",commentId));
        Post post = repo1.findById(postId).orElseThrow(()->new ResourceNotfoundException("postId","id",postId));
if(comment.getPost().getId()!=(post.getId()))
    throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment does not belong to post");
        return maptoDto(comment);

    }

    @Override
    public CommentDTO updateCommentbyId(Long postid, Long commentId ,CommentDTO comment) {
        Comment comment1= repo.findById(commentId).orElseThrow(()->new ResourceNotfoundException("CommentId","id",commentId));
        Post post = repo1.findById(postid).orElseThrow(()->new ResourceNotfoundException("postId","id",postid));
        if(comment1.getPost().getId()!=(post.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment does not belong to post");
      comment1.setName(comment.getName());
      comment1.setEmail(comment.getEmail());
      comment1.setBody(comment.getBody());
      repo.save(comment1);
     return maptoDto(comment1);
    }

    @Override
    public void Delete(Long postid, Long cid) {
        Comment comment1= repo.findById(cid).orElseThrow(()->new ResourceNotfoundException("CommentId","id",cid));
        Post post = repo1.findById(postid).orElseThrow(()->new ResourceNotfoundException("postId","id",postid));
        if(comment1.getPost().getId()!=(post.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"comment does not belong to post");

    repo.delete(comment1);

    }
}
