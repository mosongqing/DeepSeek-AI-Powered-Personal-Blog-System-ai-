package com.example.studytwo.controller;

import com.example.studytwo.model.User;
import com.example.studytwo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 注册接口
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("用户名已存在");
        }
        userRepository.save(user);
        return ResponseEntity.ok("注册成功");
    }
//文件上传
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("文件为空");
        }

        // 生成唯一文件名
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // 获取项目根路径
        String basePath = System.getProperty("user.dir");

        // 保存路径：src/main/resources/static/uploads/
        String savePath = basePath + "/src/main/resources/static/uploads/" + filename;

        // 保存文件
        File dest = new File(savePath);
        dest.getParentFile().mkdirs(); // 创建目录
        file.transferTo(dest);

        // 返回前端可访问路径
        return ResponseEntity.ok("/uploads/" + filename);
    }


    // 登录接口
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> found = userRepository.findByUsername(user.getUsername());
        if (found.isPresent() && found.get().getPassword().equals(user.getPassword())) {
            Map<String, Object> result = new HashMap<>();
            result.put("token", UUID.randomUUID().toString()); // 模拟 token
            result.put("userId", found.get().getId());  // 假设 User 类有 getId()
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(401).body("用户名或密码错误");
        }
    }
}

