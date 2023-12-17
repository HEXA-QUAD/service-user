package com.example.oauth2.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "student_history")
public class StudentHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @NotBlank
    @Column(name = "semester")
    private String semester;
    @NotBlank
    @Column(name = "track")
    private String track;
    @NotBlank
    @Column(name = "course_one")
    private String courseOne;
    @Column(name = "course_two")
    private String courseTwo;
    @Column(name = "course_three")
    private String courseThree;
    @Column(name = "course_four")
    private String courseFour;
    @Column(name = "course_five")
    private String courseFive;

    public String getTrack() {return track;}

    public void setTrack(String track) {this.track = track;}

    public String getCourseThree() {
        return courseThree;
    }

    public void setCourseThree(String courseThree) {
        this.courseThree = courseThree;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourseOne() {
        return courseOne;
    }

    public void setCourseOne(String courseOne) {
        this.courseOne = courseOne;
    }



    public String getCourseTwo() {
        return courseTwo;
    }

    public void setCourseTwo(String courseTwo) {
        this.courseTwo = courseTwo;
    }

    public String getCourseFour() {
        return courseFour;
    }

    public void setCourseFour(String courseFour) {
        this.courseFour = courseFour;
    }



    public String getCourseFive() {
        return courseFive;
    }

    public void setCourseFive(String courseFive) {
        this.courseFive = courseFive;
    }




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
