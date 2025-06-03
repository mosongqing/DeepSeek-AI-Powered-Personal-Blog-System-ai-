// src/main/java/com/example/studytwo/model/Blog.java
package com.example.studytwo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String title;
    private String summary;

    @Lob
    private String content;

    private String coverImage;
    private Integer views = 0;
    private Boolean isDraft = false;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime = new Date();
}