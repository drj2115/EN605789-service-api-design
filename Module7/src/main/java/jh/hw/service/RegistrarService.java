package jh.hw.service;

import jh.hw.model.Course;
import jh.hw.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(value = "/registrar", produces = "application/json")
public class RegistrarService {
    static final Map<Course, Set<Student>> COURSE_STUDENT_MAP = new HashMap<>();

    @RequestMapping(value = "/course/{courseNumber}/student/{studentId}", method = RequestMethod.POST)
    public ResponseEntity registerStudent(@PathVariable("courseNumber") Integer courseNumber,
                                          @PathVariable("studentId") Integer studentId) {
        Student student = StudentService.STUDENT_MAP.get(studentId);
        Course course = CourseService.COURSE_MAP.get(courseNumber);
        if (student == null || course == null) {
            return ResponseEntity.badRequest().build();
        }
        Set<Student> studentsInCourse = COURSE_STUDENT_MAP.getOrDefault(course, new HashSet<>());
        if (studentsInCourse.contains(student)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        studentsInCourse.add(student);
        COURSE_STUDENT_MAP.put(course, studentsInCourse);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/course/{courseNumber}", method = RequestMethod.GET)
    public ResponseEntity getRegisteredStudentsByCourseNumber(@PathVariable("courseNumber") final Integer courseNumber) {
        Course course = CourseService.COURSE_MAP.get(courseNumber);
        if (course == null) {
            return ResponseEntity.badRequest().build();
        }
        Set<Student> studentsInCourse = COURSE_STUDENT_MAP.get(course);
        if (studentsInCourse == null || studentsInCourse.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(studentsInCourse);
    }

    @RequestMapping(value = "/course/{courseNumber}/student/{studentId}", method = RequestMethod.DELETE)
    public ResponseEntity getRegisteredStudentsByCourseNumber(@PathVariable("courseNumber") final Integer courseNumber,
                                                              @PathVariable("studentId") final Integer studentId) {
        Student student = StudentService.STUDENT_MAP.get(studentId);
        Course course = CourseService.COURSE_MAP.get(courseNumber);
        if (student == null || course == null) {
            return ResponseEntity.badRequest().build();
        }
        Set<Student> studentsInCourse = COURSE_STUDENT_MAP.get(course);
        if (studentsInCourse == null || !studentsInCourse.contains(student)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        studentsInCourse.remove(student);
        return ResponseEntity.ok().build();
    }
}