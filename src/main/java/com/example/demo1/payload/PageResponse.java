package com.example.demo1.payload;
//this claass for ftching page details

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PageResponse {
private List<PostDto> content;
private int pageNo;
private int pageSize;
private long totalElements;
private int totalPage;
private boolean last;

}
