package com.example.demo1.service;

import com.example.demo1.entity.Comment;
import com.example.demo1.payload.CommentDTO;

import java.util.List;

public interface CommentSevice {
    CommentDTO createComment(long postId, CommentDTO comment);
    List<CommentDTO> getAllComments(long postId);
    CommentDTO getCommentById(Long postid,Long commentId);
    CommentDTO updateCommentbyId(Long postid,Long commentId ,CommentDTO dto);
void Delete(Long postid, Long cid);
}
