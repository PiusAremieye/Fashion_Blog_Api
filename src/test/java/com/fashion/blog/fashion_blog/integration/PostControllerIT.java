package com.fashion.blog.fashion_blog.integration;

import com.fashion.blog.fashion_blog.controller.PostController;
import com.fashion.blog.fashion_blog.model.Post;
import com.fashion.blog.fashion_blog.responsitory.PostRespository;
import com.fashion.blog.fashion_blog.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PostControllerIT {

    private static Post post1;
    private static Post post2;

    @MockBean
    private PostRespository postRespository;

    @Autowired
    private PostController postController;

    @Autowired
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
        post1 = new Post("Bags", "good bags");
        post1.setId(1);
        post2 = new Post("Shoes", "good shoes");
        post2.setId(2);
    }

    public static String toString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void load_the_controller() {
        assertNotNull(postController);
    }

    @Test
    public void make_a_post() throws Exception {
        this.mockMvc.perform(post("/posts").contentType(MediaType.APPLICATION_JSON)
                .content(toString(post1))).andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(containsString("Successfully created")));

    }
}
