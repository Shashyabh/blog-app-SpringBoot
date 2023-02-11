package com.blog.services;

import com.blog.entities.Post;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost (PostDto postDto,Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);


    //Get all post
    //List<PostDto> getAllPost(};

    //Get all posts by pagination
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy, String sortDir);

    //Get post by PostId
    PostDto getPostById(Integer postId);

    //Get all post by category
    List<PostDto> getPostByCategory(Integer categoryId);

    //Get all post by user
    List<PostDto> getPostByUser(Integer userId);

    //Search post by keyword
    List<PostDto> searchPost(String keyword);
}
