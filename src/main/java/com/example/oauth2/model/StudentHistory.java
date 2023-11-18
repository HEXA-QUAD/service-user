package com.example.oauth2.model;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "student_history")
public class StudentHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User student;

}
