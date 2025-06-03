package com.example.studytwo.repository;

import com.example.studytwo.model.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserFileRepository extends JpaRepository<UserFile, Long> {
    List<UserFile> findByUserId(Long userId);
    List<UserFile> findByUserIdAndFileType(Long userId, String fileType);

}
