package jh.hw;

import java.util.List;

public class Course {
    public String courseName;
    public String courseNumber;
    public List<String> modeOfStudy;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public List<String> getModeOfStudy() {
        return modeOfStudy;
    }

    public void setModeOfStudy(List<String> modeOfStudy) {
        this.modeOfStudy = modeOfStudy;
    }
}