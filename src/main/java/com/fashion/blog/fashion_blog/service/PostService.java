package com.fashion.blog.fashion_blog.service;

import com.fashion.blog.fashion_blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Post createAPost(Post post);
    Post viewAPost(Integer id);
    Page<Post> viewAllPost(Pageable pageable);
    Boolean deletePost(Integer id);
    Post likePost(Integer id);
    Post disLikePost(Integer id);
    Page<Post> findAllPostsByTitle(Pageable pageable, String title);
    Post updateAPost(Post post, Integer id);
}
