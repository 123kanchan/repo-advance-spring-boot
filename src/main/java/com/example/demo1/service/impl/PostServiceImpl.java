package com.example.demo1.service.impl;

import com.example.demo1.entity.Category;
import com.example.demo1.entity.Post;
import com.example.demo1.exception.ResourceNotfoundException;
import com.example.demo1.payload.PageResponse;
import com.example.demo1.payload.PostDto;
import com.example.demo1.repository.CategoryRepository;
import com.example.demo1.repository.PostRepository;
import com.example.demo1.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository repo;

    private CategoryRepository repo1;
//for model mapper doing
    private ModelMapper mapper;
    public PostServiceImpl(PostRepository repo,ModelMapper map,CategoryRepository repo1) {
        this.repo = repo;
        this.mapper=map;
        this.repo1=repo1;
    }

    @Override
    public PostDto createpdt(PostDto dto) {
        Category category=repo1.findById(dto.getId()).orElseThrow(()->new ResourceNotfoundException("category","id",dto.getCategoryid()));
        //convert dto to entity
       Post post= maptoentity(dto);
        post.setCategory(category);
        Post newpost=repo.save(post);
         //db saved

        //return newpost entity to dto
        PostDto dt=maptoDto(newpost);
        return dt;
    }

    //reating entity to dto
    private PostDto maptoDto(Post newpost){

        //this i did for mapper so commented below
      //  PostDto dto1=new PostDto();
       /* dto1.setId(newpost.getId());
        dto1.setTitle(newpost.getTitle());
        dto1.setDescription(newpost.getDescription());
        dto1.setContent(newpost.getContent());*/
        PostDto dto1= mapper.map(newpost,PostDto.class);
        return dto1;
    }

    //creting dto to entity
    private Post maptoentity(PostDto dto){
        /*Post post=new Post();
        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setContent(dto.getContent());*/
       Post post= mapper.map(dto,Post.class);
        return post;
    }
    @Override
    //I made it now pagination code

    //public List<PostDto> getAllpost(int pageNo,int pageSize) earlier this before making page response
    public PageResponse getAllpost(int pageNo,int pageSize,String sortBy,String sortDir) {
        //making pageable instance
       // Pageable pageable= PageRequest.of( pageNo, pageSize);
        //if sorting included + ascending descemding default
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();


        //if sorting is alos included
       // Pageable pageable= PageRequest.of( pageNo, pageSize, Sort.by(sortBy));
        //if ascending descending giving dynamically
         Pageable pageable= PageRequest.of( pageNo, pageSize, sort);

        Page<Post> posts=repo.findAll(pageable); //but need to send in dto

        //get content from page object

        List<Post>listofpost=posts.getContent();
       List<PostDto>p= listofpost.stream().map(pos->maptoDto(pos)).collect(Collectors.toList());
        //return p;  //abhi tk yhi return krri thi but after creating page response
        PageResponse pgres=new PageResponse();
        pgres.setContent(p);
        pgres.setPageNo(posts.getNumber());
        pgres.setPageSize(posts.getSize());
        pgres.setTotalElements(posts.getTotalElements());
        pgres.setTotalPage(posts.getTotalPages());
        pgres.setLast(posts.isLast());
        return pgres;
    }

    @Override
    public PostDto getpostById(long id) {
       Post post=repo.findById(id).orElseThrow(()->new ResourceNotfoundException("Post","id",id));
       PostDto dt= maptoDto(post);
       return dt;
    }

    @Override
    public PostDto update(PostDto DTO, long id) {
        Post post=repo.findById(id).orElseThrow(()->new ResourceNotfoundException("Post","id",id));
        Category category=repo1.findById(DTO.getCategoryid()).orElseThrow(()->new ResourceNotfoundException("category","id",DTO.getCategoryid()));

        post.setContent(DTO.getContent());
        post.setTitle(DTO.getTitle());
        post.setDescription(DTO.getDescription());
        post.setCategory(category);
        //upar ka sab save hogya db mai
        repo.save(post);
        //now send it in dto
        PostDto dto=maptoDto(post);
        return dto;
    }

    @Override
    public void delete(long id) {
        Post post=repo.findById(id).orElseThrow(()->new ResourceNotfoundException("Post","id",id));
        repo.delete(post);
    }

    @Override
    public List<PostDto> getAllByCategoryId(Long id) {
        Category cat=repo1.findById(id).orElseThrow(()->new ResourceNotfoundException("category","id",id));
   List<Post>p=repo.findByCategoryId(id);
   return p.stream().map((post)->maptoDto(post)).collect(Collectors.toList());

    }


}
