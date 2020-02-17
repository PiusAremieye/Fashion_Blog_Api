package com.fashion.blog.fashion_blog.service;

import com.fashion.blog.fashion_blog.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Comment makeAComment(Comment comment, Integer postId);
    Object viewAComment(Integer postId, Integer commentId);
    Page<Comment> viewAllCommentByPostId(Pageable pageable, Integer postId);
    Page<Comment> viewAllComment(Pageable pageable);
    Page<Comment> viewAllCommentsByComment(Pageable pageable, String comment);
    Object deleteAComment(Integer postId, Integer commentId);
}
