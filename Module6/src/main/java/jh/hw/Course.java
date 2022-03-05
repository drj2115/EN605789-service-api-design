package jh.hw;

import javax.validation.constraints.NotBlank;

public class Course {

    @NotBlank
    private String courseNumber;

    @NotBlank
    private String courseTitle;

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
}