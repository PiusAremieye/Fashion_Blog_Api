package com.fashion.blog.fashion_blog.service;

import com.fashion.blog.fashion_blog.model.Post;

import java.util.List;

public interface PostService {

    Post createAPost(Post post);
    Post viewAPost(Integer id);
    List<Post> viewAllPost();
    Object deletePost(Integer id);
    Object likePost(Integer id);
    Object disLikePost(Integer id);
    List<Post> findAPostByTitle(String title);
}
