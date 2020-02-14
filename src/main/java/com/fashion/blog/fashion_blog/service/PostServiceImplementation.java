package com.fashion.blog.fashion_blog.service;

import com.fashion.blog.fashion_blog.model.Post;
import com.fashion.blog.fashion_blog.responsitory.PostRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImplementation implements PostService {

    private PostRespository postRespository;

    @Autowired
    public PostServiceImplementation(PostRespository postRespository){
        this.postRespository = postRespository;
    }

    @Override
    public Post createAPost(Post post) {
        return postRespository.save(post);
    }

    @Override
    public Post viewAPost(Integer id) {
        return postRespository.findById(id).orElse(null);
    }

    @Override
    public List<Post> viewAllPost() {
        return postRespository.findAll();
    }

    @Override
    public Object deletePost(Integer id){
        if (postRespository.existsById(id)){
            postRespository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Post> findAPostByTitle(String title){
        return postRespository.findByTitle(title);
    }

    @Override
    public Object likePost(Integer id){
        Post postToLike = postRespository.findById(id).orElse(null);
        if (postToLike != null){
            int likes = postToLike.getLikes();
            postToLike.setLikes(++likes);
            return postRespository.save(postToLike);
        }
        return false;
    }

    @Override
    public Object disLikePost(Integer id){
        Post postTodisLike = postRespository.findById(id).orElse(null);
        if (postTodisLike != null){
            int disLikes = postTodisLike.getDisLikes();
            postTodisLike.setDisLikes(++disLikes);
            return postRespository.save(postTodisLike);
        }
        return false;
    }
}
