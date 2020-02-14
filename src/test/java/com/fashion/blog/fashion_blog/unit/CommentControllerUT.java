package com.fashion.blog.fashion_blog.unit;

import com.fashion.blog.fashion_blog.controller.CommentController;
import com.fashion.blog.fashion_blog.model.Comment;
import com.fashion.blog.fashion_blog.model.Post;
import com.fashion.blog.fashion_blog.response.ApiExceptionHandler;
import com.fashion.blog.fashion_blog.responsitory.CommentRespository;
import com.fashion.blog.fashion_blog.service.CommentService;
import com.fashion.blog.fashion_blog.service.CommentServiceImplementation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CommentControllerUT {

    private static Comment comment1;
    private static Comment comment2;

    private static Post post1;
    private static Post post2;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init(){
        comment1 = new Comment("good designs");
        comment2 = new Comment("good products");
        post1 = new Post("shoes", "big shoes");
        post2 = new Post("bags", "big bags");

        comment1.setId(1);
        comment1.setId(2);
        post1.setId(1);
        post2.setId(2);
    }

    @Test
    public void be_able_to_make_a_post_request(){
        Mockito.when(commentService.makeAComment(comment1, post1.getId())).thenReturn(comment1);
        ResponseEntity<ApiExceptionHandler<Comment>> comment = commentController.createComment(comment1, post1.getId());
        assertThat(comment.getBody().getData(), is(comment1));
        assertThat(comment.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    public void be_able_to_make_a_get_request(){
        Mockito.when(commentService.viewAComment(comment1.getId())).thenReturn(comment1);
        ResponseEntity<ApiExceptionHandler<Comment>> comment = commentController.getAComment(comment1.getId());
        assertThat(comment.getBody().getData(), is(comment1));
        assertThat(comment.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void be_able_to_view_all_comments(){
        Mockito.when(commentService.viewAllComment()).thenReturn(Arrays.asList(comment1, comment2));
        ResponseEntity<ApiExceptionHandler<List<Comment>>> allComment = commentController.getAllComment();
        assertThat(allComment.getBody().getData().size(), is(2));
        assertThat(allComment.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void be_able_to_view_comments_of_a_post(){

    }

    @Test
    public void be_able_to_delete_a_comment(){
        Mockito.when(commentService.deleteAComment(comment1.getId())).thenReturn(true);
        ResponseEntity<ApiExceptionHandler<Comment>> deletedComment = commentController.deleteComment(comment1.getId());
        assertThat(deletedComment.getStatusCode(), is(HttpStatus.OK));
        assertThat(deletedComment.getBody().getMessage(), is("Comment deleted successfully"));
    }
}
