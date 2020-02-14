package com.fashion.blog.fashion_blog.unit;

import com.fashion.blog.fashion_blog.model.Comment;
import com.fashion.blog.fashion_blog.model.Post;
import com.fashion.blog.fashion_blog.responsitory.CommentRespository;
import com.fashion.blog.fashion_blog.responsitory.PostRespository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CommentServiceUT {

    private static Comment comment1;
    private static Comment comment2;
    private static Comment comment3;
    private static Post post1;
    private static Post post2;

    @Mock
    private CommentRespository commentRespository;

    @Mock
    private PostRespository postRespository;

    @InjectMocks
    private CommentServiceImplementation commentServiceImplementation;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init(){
        comment1 = new Comment("i love those bags");
        comment2 = new Comment("good products all around");
        comment1.setId(1);
        comment2.setId(2);

        post1 = new Post("shoes", "good shoes");
        post2 = new Post("bags", "good bags");
        post1.setId(1);
        post2.setId(2);

        comment3 = new Comment("good products all around");
        comment3.setId(3);
        comment3.setPost(post1);
    }

    @Test
    public void make_a_comment(){
        Mockito.when(postRespository.findById(post1.getId())).thenReturn(Optional.of(post1));
        Mockito.when(commentRespository.save(comment3)).thenReturn(comment3);

        assertThat(commentServiceImplementation.makeAComment(comment3, post1.getId()).getPost(), is(comment3.getPost()));
        Mockito.verify(postRespository, Mockito.times(1)).findById(post1.getId());
        Mockito.verify(commentRespository, Mockito.times(1)).save(comment3);
    }

    @Test
    public void get_a_comment(){
        Mockito.when(commentRespository.findById(comment1.getId())).thenReturn(Optional.of(comment1));
        assertThat(commentServiceImplementation.viewAComment(comment1.getId()), is(comment1));
        Mockito.verify(commentRespository, Mockito.times(1)).findById(comment1.getId());
    }

    @Test
    public void get_all_comments(){
        Mockito.when(commentRespository.findAll()).thenReturn(Arrays.asList(comment1, comment2));
        assertThat(commentServiceImplementation.viewAllComment().size(), is(2));
        Mockito.verify(commentRespository, Mockito.times(1)).findAll();
    }

    @Test
    public void delete_a_comment(){
        Mockito.when(commentRespository.existsById(comment2.getId())).thenReturn(true);
        assertThat(commentServiceImplementation.deleteAComment(comment2.getId()), is(true));
        Mockito.verify(commentRespository, Mockito.times(1)).existsById(comment2.getId());
    }


}
