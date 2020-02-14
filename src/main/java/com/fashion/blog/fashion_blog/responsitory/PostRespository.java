package com.fashion.blog.fashion_blog.responsitory;

import com.fashion.blog.fashion_blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRespository extends JpaRepository<Post, Integer> {
    List<Post> findByTitle(String title);
}
