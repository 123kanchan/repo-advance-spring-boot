package com.example.demo1.payload;

import com.example.demo1.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "POSTDTO MODEL INFORMATION"
)
public class PostDto {

    private long id;

    @Schema(
            description = "title description"
    )
   @NotEmpty
    @Size(min=2,message="title should hv atleat 2 characters")
    private String title;
    @NotEmpty
    @Size(min=10,message="des should hv atleat 10 characters")
    @Schema(
            description = " description about post"
    )
    private String description;
    private String content;

    //this adding now as post has many comments and we are sending comment dto only so to show what alk comments are there
    private Set<Comment> comments;
 @Schema(
         description = " blog post category"
 )
    private long  categoryid;
}
