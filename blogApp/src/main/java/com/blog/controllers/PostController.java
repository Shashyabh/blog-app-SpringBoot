package com.blog.controllers;

import com.blog.config.AppConstants;
import com.blog.entities.Post;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPosts(@RequestBody PostDto postDto, @PathVariable Integer userId,@PathVariable
                                               Integer categoryId){

        PostDto createPost=this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
    }


    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto updatePost=this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    }

    //Post by given user Id

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>>getPostByUserid(@PathVariable Integer userId){

        List<PostDto> postDtos=this.postService.getPostByUser(userId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    //Get post by given Category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>>getPostByCategoryId(@PathVariable Integer categoryId){

        List<PostDto> postDtos=this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    //Get all Post
    @GetMapping("/posts")
    public ResponseEntity <PostResponse> getALLPost(
            @RequestParam (value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam (value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize ,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)  String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)  String sortDir){
        //AppContants is in Config pakage for defining constants


        PostResponse allPost=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
        //List<PostDto> allPost=this.postService.getAllPost();
        return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
    }

    //Get  all post without using pagination
//    public ResponseEntity <List<PostDto>> getALLPost(){
//        //List<PostDto> allPost=this.postService.getAllPost();
//        return new ResponseEntity<List<PostDto>>(allPost,HttpStatus.OK);
//    }

    //Get post by Post Id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostByID(@PathVariable Integer postId){
        PostDto postDto=this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
    }

    //Delete post By Id
    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ApiResponse("Post is successfully deleted!!",true);
    }

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> serachPostByTitle(@PathVariable("keywords") String keywords){
        List<PostDto> result=this.postService.searchPost(keywords);
        return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
    }


    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDto>uploadImage(@RequestParam("image") MultipartFile image,
                                                    @PathVariable Integer postId) throws IOException {

        PostDto postDto=this.postService.getPostById(postId);
        String fileName=this.fileService.uploadImage(path,image);
        postDto.setImageName(fileName);
        PostDto updatePost=this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    }


    @GetMapping(value = "posts/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downLoadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException{

        InputStream resource=this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
