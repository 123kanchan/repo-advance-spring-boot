package com.example.demo1.service;

import com.example.demo1.payload.PageResponse;
import com.example.demo1.payload.PostDto;

import java.util.List;

public interface PostService {
      PostDto createpdt(PostDto dto);
      //      List<PostDto> getAllpost(int pageNo,int pageSize);
    PageResponse getAllpost(int pageNo, int pageSize,String sortBy,String sortDir);
      PostDto getpostById(long id);
      PostDto update(PostDto DTO,long id);
      void delete(long id);
      List<PostDto>getAllByCategoryId(Long id);

}
