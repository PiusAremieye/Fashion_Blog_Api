package com.fashion.blog.fashion_blog.service;

import com.fashion.blog.fashion_blog.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    Comment makeAComment(Comment comment, Integer postId);
    Comment viewAComment(Integer commentId);
    Page<Comment> viewAllCommentByPostId(Pageable pageable, Integer postId);
    List<Comment> viewAllComment();
    Object deleteAComment(Integer id);
}
