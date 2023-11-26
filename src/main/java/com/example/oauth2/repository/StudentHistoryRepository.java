package com.example.oauth2.repository;

import com.example.oauth2.model.StudentHistory;
import com.example.oauth2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.function.Function;
@Repository
public interface StudentHistoryRepository extends JpaRepository<StudentHistory, Integer> {
    List<StudentHistory> findByUser(User user);
    boolean existsByUserAndSemester(User user, String semester);
    boolean existsByTrack(@NotBlank String track);
}
