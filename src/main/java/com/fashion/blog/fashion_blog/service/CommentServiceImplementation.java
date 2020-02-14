package com.fashion.blog.fashion_blog.service;

import com.fashion.blog.fashion_blog.model.Comment;
import com.fashion.blog.fashion_blog.model.Post;
import com.fashion.blog.fashion_blog.responsitory.CommentRespository;
import com.fashion.blog.fashion_blog.responsitory.PostRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class CommentServiceImplementation implements CommentService {

    private CommentRespository commentRespository;

    private PostRespository postRespository;

    @Autowired
    public CommentServiceImplementation(CommentRespository commentRespository, PostRespository postRespository){
        this.commentRespository = commentRespository;
        this.postRespository = postRespository;
    }

    @Override
    public Comment makeAComment(Comment comment, Integer postId) {
        Post post = postRespository.findById(postId).orElse(null);
        if (post != null){
            comment.setPost(post);
            return commentRespository.save(comment);
        }
        return null;
    }

    @Override
    public Page<Comment> viewAllCommentByPostId(Pageable pageable, Integer postId) {
        Post post = postRespository.findById(postId).orElse(null);
        if (post != null){
            return commentRespository.findByPostId(pageable, postId);
        }
        return null;
    }

    @Override
    public Comment viewAComment(Integer commentId){
        return commentRespository.findById(commentId).orElse(null);
    }

    @Override
    public List<Comment> viewAllComment(){
        return commentRespository.findAll();
    }

    @Override
    public Object deleteAComment(Integer id){
        if (commentRespository.existsById(id)){
            commentRespository.deleteById(id);
            return true;
        }
        return false;
    }
}
