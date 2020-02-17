package com.fashion.blog.fashion_blog.responsitory;

import com.fashion.blog.fashion_blog.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRespository extends JpaRepository<Comment, Integer> {
    Page<Comment> findByPostId(Pageable pageable, Integer postId);
    Page<Comment> findAll(Pageable pageable);
    Page<Comment> findAllByComment(Pageable pageable, String comment);
}
