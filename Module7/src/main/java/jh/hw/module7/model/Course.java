package jh.hw.module7.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Course {

    @NotNull
    private Integer courseNumber;

    @NotBlank
    private String courseTitle;

    public Course() {
    }

    public Course(Integer courseNumber, String courseTitle) {
        this.courseNumber = courseNumber;
        this.courseTitle = courseTitle;
    }

    public Integer getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(Integer courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseNumber=" + courseNumber +
                ", courseTitle='" + courseTitle + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course)o;
        return Objects.equals(courseNumber, course.courseNumber) && Objects.equals(courseTitle, course.courseTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseNumber, courseTitle);
    }
}