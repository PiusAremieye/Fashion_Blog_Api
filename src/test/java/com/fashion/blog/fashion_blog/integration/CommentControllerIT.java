package com.fashion.blog.fashion_blog.integration;

import com.fashion.blog.fashion_blog.controller.CommentController;
import com.fashion.blog.fashion_blog.model.Comment;
import com.fashion.blog.fashion_blog.responsitory.CommentRespository;
import com.fashion.blog.fashion_blog.service.CommentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerIT {

    private static Comment comment1;
    private static Comment comment2;

    @MockBean
    private CommentRespository commentRespository;

    @Autowired
    private CommentController commentController;

    @BeforeAll
    public static void init(){
        comment1 = new Comment("i love this product");
        comment2 = new Comment("i love this bag");
        comment1.setId(1);
        comment2.setId(2);
    }

    @Test
    public void testController(){
        assertNotNull(commentController);
    }

}
