package com.fashion.blog.fashion_blog.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "posts")
public class Post extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @NotNull
    private String category;

    @NotBlank
    @NotNull
    @Column(name = "post_title")
    @Size(max = 100)
    private String title;

    private Integer likes = 0;
    private Integer disLikes = 0;

    public Post(String category, String title) {
        this.category = category;
        this.title = title;
    }

    public Post() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDisLikes() {
        return disLikes;
    }

    public void setDisLikes(Integer disLikes) {
        this.disLikes = disLikes;
    }
}
