package com.fashion.blog.fashion_blog.controller;

import com.fashion.blog.fashion_blog.model.Comment;
import com.fashion.blog.fashion_blog.model.Post;
import com.fashion.blog.fashion_blog.response.ApiExceptionHandler;
import com.fashion.blog.fashion_blog.service.CommentService;
import com.fashion.blog.fashion_blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class CommentController {

    private CommentService commentService;

    private PostService postService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService){
        this.commentService = commentService;
        this.postService = postService;
    }

    @RequestMapping(path = "/{postId}/comments", method = RequestMethod.POST)
    public ResponseEntity<ApiExceptionHandler<Comment>> createComment(@Valid @RequestBody Comment comment, @PathVariable(value = "postId") Integer postId){
        Comment newComment = commentService.makeAComment(comment, postId);
        ApiExceptionHandler<Comment> arh;
        if (newComment != null){
            arh = new ApiExceptionHandler<>(HttpStatus.CREATED);
            arh.setMessage("Successfully created");
            arh.setData(newComment);
            return new ResponseEntity<>(arh, HttpStatus.CREATED);
        }
        arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
        arh.setError("Cannot create comment");
        arh.setMessage("Post does not exists");
        return new ResponseEntity<>(arh, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/{postId}/comments", method = RequestMethod.GET)
    public ResponseEntity<ApiExceptionHandler<Page<Comment>>> getAllCommentByPostId(Pageable pageable, @PathVariable(value = "postId") Integer postId){
        Page<Comment> posts_comment = commentService.viewAllCommentByPostId(pageable, postId);
        ApiExceptionHandler<Page<Comment>> arh;
        if (posts_comment != null) {
            if (posts_comment.hasContent()) {
                arh = new ApiExceptionHandler<>(HttpStatus.OK);
                arh.setMessage("Comment(s) of post " + postId + " retrieved Successfully");
                arh.setData(posts_comment);
                return new ResponseEntity<>(arh, HttpStatus.OK);
            }
            arh = new ApiExceptionHandler<>(HttpStatus.OK);
            arh.setMessage("Post " + postId + " does not have comment(s)");
            return new ResponseEntity<>(arh, HttpStatus.OK);
        }
        arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
        arh.setMessage("Do not have post for id : " + postId);
        arh.setError("Cant get comments because post does not exists");
        return new ResponseEntity<>(arh, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/comments/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiExceptionHandler<Comment>> getAComment(@PathVariable(value = "id") Integer id){
        Comment comment = commentService.viewAComment(id);
        ApiExceptionHandler<Comment> arh;
        if (comment != null){
            arh = new ApiExceptionHandler<>(HttpStatus.OK);
            arh.setMessage("Comment retrieved Successfully");
            arh.setData(comment);
            return new ResponseEntity<>(arh, HttpStatus.OK);
        }
        arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
        arh.setError("Do not have comment of id : " + id);
        arh.setMessage("Comment does not exists");
        return new ResponseEntity<>(arh, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/comments", method = RequestMethod.GET)
    public ResponseEntity<ApiExceptionHandler<List<Comment>>> getAllComment(){
        List<Comment> comment = commentService.viewAllComment();
        ApiExceptionHandler<List<Comment>> arh;
        if (!comment.isEmpty()){
            arh = new ApiExceptionHandler<>(HttpStatus.OK);
            arh.setMessage("All comment(s) retrieved Successfully");
            arh.setData(comment);
            return new ResponseEntity<>(arh, HttpStatus.OK);
        }
        arh = new ApiExceptionHandler<>(HttpStatus.OK);
        arh.setError("There are no comment(s)");
        return new ResponseEntity<>(arh, HttpStatus.OK);
    }

    @RequestMapping(path = "/comments/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiExceptionHandler<Comment>> deleteComment(@PathVariable(name = "id") Integer id){
        Object bool = commentService.deleteAComment(id);
        ApiExceptionHandler<Comment> arh;
        if (bool.equals(true)){
            arh = new ApiExceptionHandler<>(HttpStatus.OK);
            arh.setMessage("Comment deleted successfully");
            return new ResponseEntity<>(arh, HttpStatus.OK);
        }
        arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
        arh.setError("Comment of id : "+ id +" cannot be deleted");
        arh.setDebugMessage("Cannot deleted an invalid id, please specify a correct id");
        return new ResponseEntity<>(arh, HttpStatus.BAD_REQUEST);
    }

}
