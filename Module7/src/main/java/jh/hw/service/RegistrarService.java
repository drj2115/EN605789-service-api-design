package jh.hw.service;

import jh.hw.model.Course;
import jh.hw.model.JsonResponse;
import jh.hw.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(value = "/registrar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrarService {
    private static final Map<Course, Set<Student>> COURSE_STUDENT_MAP = new HashMap<>();
    private static final int COURSE_MAX_STUDENTS = 15;

    @RequestMapping(value = "/course/{courseNumber}/student/{studentId}", method = RequestMethod.POST)
    public ResponseEntity<JsonResponse> registerStudent(@PathVariable("courseNumber") Integer courseNumber,
                                                        @PathVariable("studentId") Integer studentId) {
        Student student = StudentService.STUDENT_MAP.get(studentId);
        Course course = CourseService.COURSE_MAP.get(courseNumber);
        if (student == null || course == null) {
            return JsonResponse.buildResponse(HttpStatus.BAD_REQUEST);
        }
        Set<Student> studentsInCourse = COURSE_STUDENT_MAP.getOrDefault(course, new HashSet<>());
        if (studentsInCourse.contains(student) || studentsInCourse.size() >= COURSE_MAX_STUDENTS) {
            return JsonResponse.buildResponse(HttpStatus.NOT_MODIFIED);
        }
        studentsInCourse.add(student);
        COURSE_STUDENT_MAP.put(course, studentsInCourse);
        return JsonResponse.buildResponse(HttpStatus.OK);
    }

    @RequestMapping(value = "/course/{courseNumber}", method = RequestMethod.GET)
    public ResponseEntity<?> getRegisteredStudentsByCourseNumber(@PathVariable("courseNumber") final Integer courseNumber) {
        Course course = CourseService.COURSE_MAP.get(courseNumber);
        if (course == null) {
            return JsonResponse.buildResponse(HttpStatus.BAD_REQUEST);
        }
        Set<Student> studentsInCourse = COURSE_STUDENT_MAP.get(course);
        if (studentsInCourse == null || studentsInCourse.isEmpty()) {
            return JsonResponse.buildResponse(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(studentsInCourse);
    }

    @RequestMapping(value = "/course/{courseNumber}/student/{studentId}", method = RequestMethod.DELETE)
    public ResponseEntity<JsonResponse> getRegisteredStudentsByCourseNumber(@PathVariable("courseNumber") final Integer courseNumber,
                                                              @PathVariable("studentId") final Integer studentId) {
        Student student = StudentService.STUDENT_MAP.get(studentId);
        Course course = CourseService.COURSE_MAP.get(courseNumber);
        if (student == null || course == null) {
            return JsonResponse.buildResponse(HttpStatus.BAD_REQUEST);
        }
        Set<Student> studentsInCourse = COURSE_STUDENT_MAP.get(course);
        if (studentsInCourse == null || !studentsInCourse.contains(student)) {
            return JsonResponse.buildResponse(HttpStatus.NOT_MODIFIED);
        }
        studentsInCourse.remove(student);
        return JsonResponse.buildResponse(HttpStatus.OK);
    }
}