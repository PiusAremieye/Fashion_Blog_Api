package com.fashion.blog.fashion_blog.unit;

import com.fashion.blog.fashion_blog.model.Post;
import com.fashion.blog.fashion_blog.responsitory.PostRespository;
import com.fashion.blog.fashion_blog.service.PostServiceImplementation;
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

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PostServiceUT {
    private static Post post1;
    private static Post post2;

    @Mock
    private PostRespository postRespository;

    @InjectMocks
    private PostServiceImplementation postServiceImplementation;

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
    public void be_able_to_create_a_post(){
        Mockito.when(postRespository.save(post1)).thenReturn(post1);
        assertThat(postServiceImplementation.createAPost(post1), is(post1));
        Mockito.verify(postRespository, Mockito.times(1)).save(post1);
    }

    @Test
    public void be_able_to_delete_a_post(){
        Mockito.when(postRespository.existsById(post2.getId())).thenReturn(true);
        assertThat(postServiceImplementation.deletePost(post2.getId()), is(true));
        Mockito.verify(postRespository, Mockito.times(1)).existsById(post2.getId());
    }

    @Test
    public void be_able_to_view_a_post(){
        Mockito.when(postRespository.findById(post1.getId())).thenReturn(Optional.of(post1));
        assertThat(postServiceImplementation.viewAPost(post1.getId()), is(post1));
        Post getPost = postServiceImplementation.viewAPost(post1.getId());
        assertThat(getPost.getTitle(), is(post1.getTitle()));
        Mockito.verify(postRespository, Mockito.times(2)).findById(post1.getId());
    }

    @Test
    public void be_able_to_view_all_post(){
        Mockito.when(postRespository.findAll()).thenReturn(Arrays.asList(post1, post2));
        assertThat(postServiceImplementation.viewAllPost().size(), is(2));
        Mockito.verify(postRespository, Mockito.times(1)).findAll();
    }

    @Test
    public void be_able_to_like_a_post(){
        Mockito.when(postRespository.findById(post2.getId())).thenReturn(Optional.of(post2));
        Mockito.when(postRespository.save(post2)).thenReturn(post2);
        Integer getLikes = postServiceImplementation.viewAPost(post2.getId()).getLikes();
        assertThat(postServiceImplementation.likePost(post2.getId()), is(post2));
        Post getPost = postServiceImplementation.viewAPost(post2.getId());
        assertThat(getPost.getLikes(), is(++getLikes));
        Mockito.verify(postRespository, Mockito.times(3)).findById(post2.getId());
    }

    @Test
    public void be_able_to_dislike_a_post(){
        Mockito.when(postRespository.findById(post1.getId())).thenReturn(Optional.of(post1));
        Mockito.when(postRespository.save(post1)).thenReturn(post1);
        Integer getDisLikes = postServiceImplementation.viewAPost(post1.getId()).getDisLikes();
        assertThat(postServiceImplementation.disLikePost(post1.getId()), is(post1));
        Post getPost = postServiceImplementation.viewAPost(post1.getId());
        assertThat(getPost.getDisLikes(), is(++getDisLikes));
        Mockito.verify(postRespository, Mockito.times(3)).findById(post1.getId());
    }
}
