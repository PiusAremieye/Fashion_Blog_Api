package com.fashion.blog.fashion_blog.responsitory;

import com.fashion.blog.fashion_blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRespository extends JpaRepository<Post, Integer> {
    Page<Post> findAllByTitle(Pageable pageable, String title);
    Page<Post> findAll(Pageable pageable);
}
