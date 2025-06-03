package com.example.studytwo.controller;

import com.example.studytwo.model.UserFile;
import com.example.studytwo.repository.UserFileRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    private UserFileRepository userFileRepository;

    @Value("${upload.local-path}")
    private String basePathCfg;

    @Value("${app.domain}")
    private String domain; // 例如：https://momosongqing.xyz:8443

    private Path resourceUploadBase;

    @PostConstruct
    public void init() {
        try {
            Path base = Paths.get(basePathCfg).toAbsolutePath();
            resourceUploadBase = base;
            System.out.println("✅ 上传保存目录 => " + resourceUploadBase);
        } catch (Exception e) {
            throw new RuntimeException("无法定位资源上传目录", e);
        }
    }

    private String saveToResources(MultipartFile file, String fileType) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path saveDir = resourceUploadBase.resolve(fileType);
        Files.createDirectories(saveDir);
        Path savePath = saveDir.resolve(filename);
        file.transferTo(savePath.toFile());
        return filename;
    }

    @PostMapping("/upload/userfile")
    public ResponseEntity<?> uploadUserFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam("fileType") String fileType) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("文件为空");
        }

        try {
            String filename = saveToResources(file, fileType);

            // ✅ 生成类似 https://momosongqing.xyz:8443/image/xxx.jpg 的 URL
            String url = domain + "/" + fileType + "/" + filename;

            UserFile uf = new UserFile();
            uf.setUserId(userId);
            uf.setFileType(fileType);
            uf.setFileName(file.getOriginalFilename());
            uf.setFilePath(url); // 保存完整 URL
            uf.setUploadTime(LocalDateTime.now());
            userFileRepository.save(uf);

            return ResponseEntity.ok(Map.of("url", url));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("文件上传失败");
        }
    }

    @PostMapping("/upload/blogcover")
    public ResponseEntity<?> uploadBlogCover(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) {
        return uploadUserFile(file, userId, "covers");
    }

    @GetMapping("/user/files")
    public ResponseEntity<?> getUserFiles(@RequestParam Long userId,
                                          @RequestParam String fileType) {
        List<UserFile> list = userFileRepository.findByUserIdAndFileType(userId, fileType);
        List<Map<String, String>> out = new ArrayList<>();
        for (UserFile uf : list) {
            out.add(Map.of("name", uf.getFileName(), "url", uf.getFilePath()));
        }
        return ResponseEntity.ok(out);
    }
}
