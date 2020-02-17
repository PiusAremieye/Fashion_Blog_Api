package com.fashion.blog.fashion_blog.service;

import com.fashion.blog.fashion_blog.model.Post;
import com.fashion.blog.fashion_blog.responsitory.PostRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

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
    public Page<Post> viewAllPost(Pageable pageable) {
        return postRespository.findAll(pageable);
    }

    @Override
    public Post updateAPost(Post post, Integer id){
        Post oldPost = postRespository.findById(id).orElse(null);
        if (oldPost != null){
            oldPost.setTitle(post.getTitle());
            oldPost.setCategory(post.getCategory());
            return postRespository.save(oldPost);
        }
        return null;
    }

    @Override
    public Boolean deletePost(Integer id){
        Boolean res = postRespository.existsById(id);
        if (res){
            postRespository.deleteById(id);
        }
        return res;
    }

    @Override
    public Page<Post> findAllPostsByTitle(Pageable pageable, String title){
        if (title.isBlank()){
            return null;
        }
        return postRespository.findAllByTitle(pageable, title);
    }

    @Override
    public Post likePost(Integer id){
        Post postToLike = postRespository.findById(id).orElse(null);
        if (postToLike != null){
            int likes = postToLike.getLikes();
            postToLike.setLikes(++likes);
            return postRespository.save(postToLike);
        }
        return null;
    }

    @Override
    public Post disLikePost(Integer id){
        Post postTodisLike = postRespository.findById(id).orElse(null);
        if (postTodisLike != null){
            int disLikes = postTodisLike.getDisLikes();
            postTodisLike.setDisLikes(++disLikes);
            return postRespository.save(postTodisLike);
        }
        return null;
    }
}
