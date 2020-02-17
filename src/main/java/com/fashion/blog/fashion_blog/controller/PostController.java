package com.fashion.blog.fashion_blog.controller;

import com.fashion.blog.fashion_blog.model.Post;
import com.fashion.blog.fashion_blog.response.ApiExceptionHandler;
import com.fashion.blog.fashion_blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

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
        ApiExceptionHandler<Post> arh = new ApiExceptionHandler<>(HttpStatus.OK);
        arh.setMessage("Post retrieved successfully");
        arh.setData(newPost);
        if (newPost == null){
            arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
            arh.setMessage("Do not have post of id : " + id);
            arh.setError("No data available for this endpoint");
        }
        return new ResponseEntity<>(arh, arh.getStatus());
    }

    @GetMapping
    public ResponseEntity<ApiExceptionHandler<Page<Post>>> getAllPost(Pageable pageable){
        Page<Post> allPost = postService.viewAllPost(pageable);
        ApiExceptionHandler<Page<Post>> arh = new ApiExceptionHandler<>(HttpStatus.OK);
        arh.setMessage("All Posts retrieved successfully");
        if (!allPost.hasContent()) {
            arh.setMessage("There are no post(s)");
        }
        arh.setData(allPost);
        return new ResponseEntity<>(arh, arh.getStatus());
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiExceptionHandler<Post>> deleteAPost(@PathVariable Integer id){
        Boolean bool = postService.deletePost(id);
        ApiExceptionHandler<Post> arh;
        if (bool.equals(true)){
            arh = new ApiExceptionHandler<>(HttpStatus.OK);
            arh.setMessage("Post deleted successfully");
            return new ResponseEntity<>(arh, HttpStatus.OK);
        }
        arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
        arh.setMessage("Cannot delete post of id : " + id);
        arh.setError("No data available for this endpoint");
        return new ResponseEntity<>(arh, arh.getStatus());
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<ApiExceptionHandler<Post>> updatePost(@Valid @RequestBody Post post, @PathVariable Integer id){
        Post updatePost = postService.updateAPost(post, id);
        ApiExceptionHandler<Post> arh;
        if (updatePost != null){
            arh = new ApiExceptionHandler<>(HttpStatus.OK);
            arh.setMessage("Post of id : "+id+" updated successfully");
            arh.setData(updatePost);
            return new ResponseEntity<>(arh, HttpStatus.OK);
        }
        arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
        arh.setMessage("Cannot update post of id :"+id);
        arh.setError("Error in updating post");
        return new ResponseEntity<>(arh, arh.getStatus());
    }

    @RequestMapping(path = "/title", method = RequestMethod.GET)
    public ResponseEntity<ApiExceptionHandler<Page<Post>>> getPostsByTitle(Pageable pageable, @RequestParam(value = "titleOfPost") String title){
        Page<Post> allPostsByTitle = postService.findAllPostsByTitle(pageable, title);
        ApiExceptionHandler<Page<Post>> arh;
        if (allPostsByTitle != null) {
            if (allPostsByTitle.hasContent()) {
                arh = new ApiExceptionHandler<>(HttpStatus.OK);
                arh.setMessage("All " + "'"+title+"'" + " retrieved successfully");
                arh.setData(allPostsByTitle);
                return new ResponseEntity<>(arh, arh.getStatus());
            }
            arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
            arh.setMessage("Cannot find title of "+"'"+title+"'");
            arh.setError("No data available for this endpoint");
            return new ResponseEntity<>(arh, arh.getStatus());
        }
        arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
        arh.setMessage("Searching with title should not be blank");
        arh.setError("No data available for this endpoint");
        return new ResponseEntity<>(arh, arh.getStatus());
    }

    @RequestMapping(path = "/{id}/like", method = RequestMethod.POST)
    public ResponseEntity<ApiExceptionHandler<Post>> likeAPost(@PathVariable Integer id){
        Post post = postService.likePost(id);
        ApiExceptionHandler<Post> arh;
        if (post != null){
            arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
            arh.setMessage("Cannot like post of id : " + id);
            arh.setError("No data available for this endpoint");
            return new ResponseEntity<>(arh, arh.getStatus());
        }
        arh = new ApiExceptionHandler<>(HttpStatus.OK);
        arh.setMessage("Post was liked");
        return new ResponseEntity<>(arh, arh.getStatus());
    }

    @RequestMapping(path = "/{id}/dislike", method = RequestMethod.POST)
    public ResponseEntity<ApiExceptionHandler<Post>> dislikeAPost(@PathVariable Integer id){
        Post post = postService.disLikePost(id);
        ApiExceptionHandler<Post> arh;
        if (post != null){
            arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
            arh.setMessage("Cannot dislike post of id : " + id);
            arh.setError("No data available for this endpoint");
            return new ResponseEntity<>(arh, arh.getStatus());
        }
        arh = new ApiExceptionHandler<>(HttpStatus.OK);
        arh.setMessage("Post was disliked");
        return new ResponseEntity<>(arh,arh.getStatus());
    }
}
