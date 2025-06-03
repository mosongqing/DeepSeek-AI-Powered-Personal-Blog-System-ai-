// src/main/java/com/example/studytwo/repository/BlogRepository.java
package com.example.studytwo.repository;

import com.example.studytwo.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByUserId(Long userId);
    List<Blog> findByIsDraftFalseOrderByCreateTimeDesc();
    Page<Blog> findByIsDraftFalse(Pageable pageable);
    List<Blog> findByTitleContainingAndIsDraftFalse(String keyword);
    Page<Blog> findByUserId(Long userId, Pageable pageable);

}