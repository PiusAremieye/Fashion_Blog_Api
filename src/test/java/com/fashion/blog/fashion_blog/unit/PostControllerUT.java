package com.fashion.blog.fashion_blog.unit;


import com.fashion.blog.fashion_blog.controller.PostController;
import com.fashion.blog.fashion_blog.model.Post;
import com.fashion.blog.fashion_blog.response.ApiExceptionHandler;
import com.fashion.blog.fashion_blog.service.PostService;
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
public class PostControllerUT {

    private static Post post1;
    private static Post post2;

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init(){
        post1 = new Post("bag", "good bags");
        post2 = new Post("shoes", "nice products");
        post1.setId(1);
        post2.setId(2);
    }

    @Test
    public void be_able_to_make_a_post_request(){
        Mockito.when(postService.createAPost(post1)).thenReturn(post1);
        ResponseEntity<ApiExceptionHandler<Post>> post = postController.createPost(post1);
        assertThat(post.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(post.getBody().getData(), is(post1));
    }

    @Test
    public void be_able_to_make_a_get_request(){
        Mockito.when(postService.viewAPost(post2.getId())).thenReturn(post2);
        ResponseEntity<ApiExceptionHandler<Post>> post = postController.getAPost(post2.getId());
        assertThat(post.getStatusCode(), is(HttpStatus.OK));
        assertThat(post.getBody().getData().getId(), is(post2.getId()));
        assertThat(post.getBody().getData(), is(post2));
    }

    @Test
    public void be_able_to_get_all_the_posts(){
        Mockito.when(postService.viewAllPost()).thenReturn(Arrays.asList(post1, post2));
        ResponseEntity<ApiExceptionHandler<List<Post>>> allPost = postController.getAllPost();
        assertThat(allPost.getBody().getData().size(), is(2));
    }

    @Test
    public void be_able_to_make_a_delete_request(){
        Mockito.when(postService.deletePost(post1.getId())).thenReturn(true);
        ResponseEntity<ApiExceptionHandler<Post>> deletedPost = postController.deleteAPost(post1.getId());
        assertThat(deletedPost.getStatusCode(), is(HttpStatus.OK));
        assertThat(deletedPost.getBody().getMessage(), is("Post deleted successfully"));
    }

    @Test
    public void be_able_to_like_a_post(){
        Mockito.when(postService.likePost(post1.getId())).thenReturn(post1);
        ResponseEntity<ApiExceptionHandler<Post>> likedPost = postController.likeAPost(post1.getId());
        assertThat(likedPost.getStatusCode(), is(HttpStatus.OK));
        assertThat(likedPost.getBody().getMessage(), is("Post was liked"));
    }

    @Test
    public void be_able_to_dislike_a_post(){
        Mockito.when(postService.disLikePost(post2.getId())).thenReturn(post2);
        ResponseEntity<ApiExceptionHandler<Post>> disLikePost = postController.dislikeAPost(post2.getId());
        assertThat(disLikePost.getStatusCode(), is(HttpStatus.OK));
        assertThat(disLikePost.getBody().getMessage(), is("Post was disliked"));
    }

    @Test
    public void be_able_to_search_for_a_post_by_title(){
        Mockito.when(postService.findAPostByTitle(post1.getTitle())).thenReturn(Arrays.asList(post1, post2));
        ResponseEntity<ApiExceptionHandler<List<Post>>> allPostByTitle = postController.getPostsByTitle(post1.getTitle());
        assertThat(allPostByTitle.getStatusCode(), is(HttpStatus.OK));
        assertThat(allPostByTitle.getBody().getData().size(), is(2));
        assertThat(allPostByTitle.getBody().getMessage(), is("All posts of title " + post1.getTitle() + " successfully"));
    }
}


