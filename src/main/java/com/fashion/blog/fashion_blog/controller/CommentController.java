package com.fashion.blog.fashion_blog.controller;

import com.fashion.blog.fashion_blog.model.Comment;
import com.fashion.blog.fashion_blog.response.ApiExceptionHandler;
import com.fashion.blog.fashion_blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
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
        arh.setError("Cannot make a comment");
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

    @RequestMapping(path = "/{postId}/comments/{commentId}", method = RequestMethod.GET)
    public ResponseEntity<ApiExceptionHandler<Object>> getAComment(@PathVariable(value = "postId") Integer postId, @PathVariable(value = "commentId") Integer commentId){
        Object obj = commentService.viewAComment(postId, commentId);
        ApiExceptionHandler<Object> arh;
        if (!obj.equals("noPostId")){
            if (!obj.equals("noCommentId")) {
                arh = new ApiExceptionHandler<>(HttpStatus.OK);
                arh.setMessage("Comment retrieved Successfully");
                arh.setData(obj);
                return new ResponseEntity<>(arh, HttpStatus.OK);
            }
            arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
            arh.setError("Do not have comment of id : " + commentId);
            arh.setMessage("Comment does not exists");
            return new ResponseEntity<>(arh, HttpStatus.BAD_REQUEST);
        }
        arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
        arh.setError("Do not have post of id : " + postId);
        arh.setMessage("Post does not exists");
        return new ResponseEntity<>(arh, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/comments", method = RequestMethod.GET)
    public ResponseEntity<ApiExceptionHandler<Page<Comment>>> getAllComment(Pageable pageable){
        Page<Comment> comment = commentService.viewAllComment(pageable);
        ApiExceptionHandler<Page<Comment>> arh;
        if (comment.hasContent()){
            arh = new ApiExceptionHandler<>(HttpStatus.OK);
            arh.setMessage("All comment(s) retrieved Successfully");
            arh.setData(comment);
            return new ResponseEntity<>(arh, HttpStatus.OK);
        }
        arh = new ApiExceptionHandler<>(HttpStatus.OK);
        arh.setError("There are no comment(s)");
        return new ResponseEntity<>(arh, HttpStatus.OK);
    }

    @RequestMapping(path = "/findComment", method = RequestMethod.GET)
    public ResponseEntity<ApiExceptionHandler<Page<Comment>>> getAllCommentsByAComment(Pageable pageable, @RequestParam(value = "comment") String comment){
        Page<Comment> allComments = commentService.viewAllCommentsByComment(pageable, comment);
        ApiExceptionHandler<Page<Comment>> arh;
        if (allComments != null){
            if(allComments.hasContent()) {
                arh = new ApiExceptionHandler<>(HttpStatus.OK);
                arh.setMessage("All " + "'"+comment+"'" + " retrieved successfully");
                arh.setData(allComments);
                return new ResponseEntity<>(arh, HttpStatus.OK);
            }
            arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
            arh.setMessage("Cannot find comment(s) of "+"'"+comment+"'");
            arh.setError("No data available for this endpoint");
            return new ResponseEntity<>(arh, HttpStatus.BAD_REQUEST);
        }
        arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
        arh.setMessage("Searching with comment should not be blank");
        arh.setError("No data available for this endpoint");
        return new ResponseEntity<>(arh, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/{postId}/comments/{commentId}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiExceptionHandler<Object>> deleteComment(@PathVariable(name = "postId") Integer postId, @PathVariable(name = "commentId") Integer commentId){
        Object bool = commentService.deleteAComment(postId, commentId);
        ApiExceptionHandler<Object> arh;
        if (!bool.equals("noPostId")){
            if (!bool.equals("noCommentId") || bool.equals("deleted")){
                arh = new ApiExceptionHandler<>(HttpStatus.OK);
                arh.setMessage("Comment deleted successfully");
                return new ResponseEntity<>(arh, HttpStatus.OK);
            }
            arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
            arh.setError("Comment of id : "+ commentId +" cannot be deleted");
            arh.setDebugMessage("Cannot delete an invalid comment, please specify the correct comment");
            return new ResponseEntity<>(arh, HttpStatus.BAD_REQUEST);
        }
        arh = new ApiExceptionHandler<>(HttpStatus.BAD_REQUEST);
        arh.setError("Post of id : "+ postId +" does not exists");
        arh.setDebugMessage("Cannot delete an invalid post, please specify the correct post");
        return new ResponseEntity<>(arh, HttpStatus.BAD_REQUEST);
    }

}
