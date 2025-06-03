package com.example.studytwo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.example.studytwo.model.Blog;
import com.example.studytwo.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    // 发布博客
    @PostMapping
    public Blog createBlog(@RequestBody Blog blog) {
        return blogRepository.save(blog);
    }

    // 查询所有公开博客
    @GetMapping("/public")
    public List<Blog> getAllPublicBlogs() {
        return blogRepository.findByIsDraftFalseOrderByCreateTimeDesc();
    }

    // 查询某个用户的所有博客
    @GetMapping("/user/{userId}")
    public List<Blog> getBlogsByUser(@PathVariable Long userId) {
        return blogRepository.findByUserId(userId);
    }

    // 查询单篇博客
    @GetMapping("/{id}")
    public Blog getBlogById(@PathVariable Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    // 删除博客
    @DeleteMapping("/{id}")
    public void deleteBlog(@PathVariable Long id) {
        blogRepository.deleteById(id);
    }

    // 编辑博客
    @PutMapping("/{id}")
    public Blog updateBlog(@PathVariable Long id, @RequestBody Blog blog) {
        blog.setId(id);
        return blogRepository.save(blog);
    }
    //分页
    @GetMapping("/public/page")
    public Page<Blog> getPublicBlogsByPage(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        return blogRepository.findByIsDraftFalse(pageable);
    }
//每次加载5条博客数据
    @GetMapping("/user/{userId}/page")
    public Page<Blog> getUserBlogsPage(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        return blogRepository.findByUserId(userId, pageable);
    }




}
