package com.fashion.blog.fashion_blog.controller;

import com.fashion.blog.fashion_blog.model.Post;
import com.fashion.blog.fashion_blog.response.ApiExceptionHandler;
import com.fashion.blog.fashion_blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<ApiExceptionHandler<Post>> createPost(@Valid @RequestBody Post post){
       Post newPost = postService.createAPost(post);
       ApiExceptionHandler<Post> arh = new ApiExceptionHandler<>(HttpStatus.CREATED);
       arh.setMessage("Successfully created");
       arh.setData(newPost);
       return new ResponseEntity<>(arh, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiExceptionHandler<Post>> getAPost(@PathVariable Integer id){
        Post newPost = postService.viewAPost(id);
        ApiExceptionHandler<Post> arh;
        if (newPost != null){
            arh = new ApiExceptionHandler<>(HttpStatus.OK);
            arh.setMessage("Post retrieved successfully");
            arh.setData(newPost);
            return new ResponseEntity<>(arh, HttpStatus.OK);
        }
        arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
        arh.setMessage("Do not have post of id : " + id);
        arh.setError("No data available for this endpoint");
        return new ResponseEntity<>(arh, HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<ApiExceptionHandler<List<Post>>> getAllPost(){
        List<Post> allPost = postService.viewAllPost();
        if (!allPost.isEmpty()) {
            ApiExceptionHandler<List<Post>> arh = new ApiExceptionHandler<>(HttpStatus.OK);
            arh.setMessage("All Posts retrieved successfully");
            arh.setData(allPost);
            return new ResponseEntity<>(arh, HttpStatus.OK);
        }
        ApiExceptionHandler<List<Post>> arh = new ApiExceptionHandler<>(HttpStatus.OK);
        arh.setMessage("There are no post(s)");
        return new ResponseEntity<>(arh, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiExceptionHandler<Post>> deleteAPost(@PathVariable Integer id){
        Object bool = postService.deletePost(id);
        ApiExceptionHandler<Post> arh;
        if (bool.equals(true)){
            arh = new ApiExceptionHandler<>(HttpStatus.OK);
            arh.setMessage("Post deleted successfully");
            return new ResponseEntity<>(arh, HttpStatus.OK);
        }
        arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
        arh.setMessage("Cannot delete post of id : " + id);
        arh.setError("No data available for this endpoint");
        return new ResponseEntity<>(arh, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/title?titleOfPost", method = RequestMethod.GET)
    public ResponseEntity<ApiExceptionHandler<List<Post>>> getPostsByTitle(@RequestParam(value = "titleOfPost") String title){
        List<Post> allPostsByTitle = postService.findAPostByTitle(title);
        ApiExceptionHandler<List<Post>> arh;
        if (allPostsByTitle != null){
            arh = new ApiExceptionHandler<>(HttpStatus.OK);
            arh.setMessage("All posts of title " + title + " successfully");
            arh.setData(allPostsByTitle);
            return new ResponseEntity<>(arh, HttpStatus.OK);
        }
        arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
        arh.setMessage("Cannot find post(s) of title : " + title);
        arh.setError("No data available for this endpoint");
        return new ResponseEntity<>(arh, HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(path = "/{id}/like", method = RequestMethod.POST)
    public ResponseEntity<ApiExceptionHandler<Post>> likeAPost(@PathVariable Integer id){
        Object obj = postService.likePost(id);
        ApiExceptionHandler<Post> arh;
        if (obj.equals(false)){
            arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
            arh.setMessage("Cannot like post of id : " + id);
            arh.setError("No data available for this endpoint");
            return new ResponseEntity<>(arh, HttpStatus.BAD_REQUEST);
        }
        arh = new ApiExceptionHandler<>(HttpStatus.OK);
        arh.setMessage("Post was liked");
        return new ResponseEntity<>(arh, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/dislike", method = RequestMethod.POST)
    public ResponseEntity<ApiExceptionHandler<Post>> dislikeAPost(@PathVariable Integer id){
        Object obj = postService.disLikePost(id);
        ApiExceptionHandler<Post> arh;
        if (obj.equals(false)){
            arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
            arh.setMessage("Cannot dislike post of id : " + id);
            arh.setError("No data available for this endpoint");
            return new ResponseEntity<>(arh, HttpStatus.BAD_REQUEST);
        }
        arh = new ApiExceptionHandler<>(HttpStatus.OK);
        arh.setMessage("Post was disliked");
        return new ResponseEntity<>(arh, HttpStatus.OK);
    }
}
