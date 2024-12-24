package com.example.demo1.controller;

import com.example.demo1.payload.PageResponse;
import com.example.demo1.payload.PostDto;
import com.example.demo1.payload.PostDtoV2;
import com.example.demo1.service.PostService;
import com.example.demo1.utils.AppConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


import static javax.xml.transform.OutputKeys.VERSION;
import static jdk.javadoc.doclet.DocletEnvironment.ModuleMode.API;

@RestController
//@RequestMapping("/api/posts")
public class PostController {
    PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @Operation(
            summary="Create POST Rest api",
            description="craete post rest api is used to save post into db"
    )
    @ApiResponse(
            responseCode = "201",
            description="Http Status 201 craeted"
    )
@SecurityRequirement(name="Bear Authentication")
    //this preauthoriuze added in spring security video that specific api should perform this create deleer
    @PreAuthorize("hasRole('ADMIN')")
@PostMapping("/api/v1/posts")
    public ResponseEntity<PostDto> createpost(@Valid @RequestBody PostDto dto){
        return new ResponseEntity<>(service.createpdt(dto), HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/posts/get")
    public PageResponse getAllpost(@RequestParam (value="pageNo",defaultValue = AppConstant.DEFAULT_PAGE_NUMBER,required=false) int pageNo,
                                   @RequestParam(value="pageSize",defaultValue = "10",required=false)int pageSize,
                                   @RequestParam(value="sortBy",defaultValue = AppConstant.DEFAULT_SORT_BY,required=false)String sortBy,
                                       @RequestParam(value="sortDir",defaultValue = "id",required=false)String sortDir
    ){
        return service.getAllpost(pageNo,pageSize,sortBy,sortDir);
    }
    @Operation(
            summary="Create GET Rest api",
            description="craete GET rest api is used to get single postfrom db"
    )
    @ApiResponse(
            responseCode = "200",
            description="Http Status 200 ok"
    )
   // @GetMapping("/api/v1/posts/getbyid/{id}")
    //@GetMapping(value="/api/posts/getbyid/{id}", params = "version=1")
    @GetMapping(value="/api/posts/getbyid/{id}", headers = "X-API-VERSION=1")
    //@GetMapping(value="/api/posts/getbyid/{id}", produces = "application.v1+json")
    public ResponseEntity<PostDto> getByid(@PathVariable Long id){
        return ResponseEntity.ok(service.getpostById(id));
    }



    //versioning

   // @GetMapping("/api/v2/posts/getbyid/{id}")
   //@GetMapping(value="/api/posts/getbyid/{id}" , params = "version=2")
   @GetMapping(value="/api/posts/getbyid/{id}", headers = "X-API-VERSION=2")
   //@GetMapping(value="/api/posts/getbyid/{id}", produces = "application.v2+json")
    public ResponseEntity<PostDtoV2> getByidV2(@PathVariable Long id){
        PostDto dto=service.getpostById(id);
        PostDtoV2 dto1=new PostDtoV2();
        dto1.setId(dto.getId());
        dto1.setContent(dto.getContent());
        dto1.setCategoryid(dto.getCategoryid());
        dto1.setDescription(dto.getDescription());
        dto1.setTitle(dto.getTitle());
        List<String>tags=new ArrayList<>();
        tags.add("java");
        tags.add("spring");
        tags.add("boot");
        dto1.setTags(tags);
        return ResponseEntity.ok(dto1);
    }

    @SecurityRequirement(name="Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary="Create UPDATE Rest api",
            description="craete UPDATE post rest api is used to update particular post into db"
    )
    @ApiResponse(
            responseCode = "200",
            description="Http Status 200 OK"
    )
    @PutMapping("/api/v1/posts/update/{id}")
    public ResponseEntity<PostDto> update(@Valid @RequestBody PostDto dto,@PathVariable  Long id){
        return ResponseEntity.ok(service.update(dto,id));
    }

    @SecurityRequirement(name="Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary="Create dleete Rest api",
            description="craete delete rest api is used to dleet post into db"
    )
    @ApiResponse(
            responseCode = "200",
            description="Http Status 200 OK"
    )
    @DeleteMapping("/api/posts/delete/{id}")
            public ResponseEntity<String> delete(@PathVariable("id") Long id){
        service.delete(id);
        return new ResponseEntity<>("deleted successfully",HttpStatus.OK);

    }
    @GetMapping("/api/v1/posts/Bycatid/{cid}")
    public ResponseEntity<List<PostDto>> getBycategoryid(@PathVariable Long cid){
       return ResponseEntity.ok(service.getAllByCategoryId(cid));
    }
}
