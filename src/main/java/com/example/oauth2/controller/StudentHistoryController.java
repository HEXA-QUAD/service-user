package com.example.oauth2.controller;

import com.example.oauth2.exception.DuplicateStudentRecordException;
import com.example.oauth2.exception.NoSuchAccountException;
import com.example.oauth2.model.StudentHistory;
import com.example.oauth2.model.User;
import com.example.oauth2.repository.StudentHistoryRepository;
import com.example.oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
public class StudentHistoryController {
    @Autowired
    private StudentHistoryRepository studentHistoryRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/student_history/get/{email}")
    public Iterable<StudentHistory> getByUser(@PathVariable String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()){
            throw new NoSuchAccountException("account with email address "+email+ "does not exist, make sure you login first");
        }
        return studentHistoryRepository.findByUser(user.get());

    }
    @RequestMapping(path = "/student_history/add/{studentId}") // called when the dispatch starts
    public @ResponseBody
    String addNewStudentHistory(@PathVariable String studentId, @RequestBody StudentHistory history) {

        history.setUser(userRepository.getReferenceById(studentId));
        if (studentHistoryRepository.existsByUserAndSemester(history.getUser(), history.getSemester())){
            throw new DuplicateStudentRecordException("duplicated student record");
        }
        studentHistoryRepository.save(history);
        return "Saved";
    }

}
