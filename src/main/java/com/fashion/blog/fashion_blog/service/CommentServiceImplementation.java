package com.fashion.blog.fashion_blog.service;

import com.fashion.blog.fashion_blog.model.Comment;
import com.fashion.blog.fashion_blog.model.Post;
import com.fashion.blog.fashion_blog.responsitory.CommentRespository;
import com.fashion.blog.fashion_blog.responsitory.PostRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public Object viewAComment(Integer postId, Integer commentId){
        Post post = postRespository.findById(postId).orElse(null);
        if (post != null){
            Comment comment = commentRespository.findById(commentId).orElse(null);
            if (comment != null){
                if(comment.getPost().getId().equals(post.getId())){
                    return commentRespository.save(comment);
                }
            }
            return "noCommentId";
        }
        return "noPostId";
    }

    @Override
    public Page<Comment> viewAllComment(Pageable pageable){
        return commentRespository.findAll(pageable);
    }

    @Override
    public Page<Comment> viewAllCommentsByComment(Pageable pageable, String comment) {
        if (comment.isBlank()){
            return null;
        }
        return commentRespository.findAllByComment(pageable, comment);
    }

    @Override
    public Object deleteAComment(Integer postId, Integer commentId){
        if (postRespository.existsById(postId)){
            if (commentRespository.existsById(commentId)){
                commentRespository.deleteById(commentId);
                return "deleted";
            }
            return "noCommentId";
        }
        return "noPostId";
    }
}
