package jh.hw.service;

import jh.hw.model.Course;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/course", produces = "application/json")
public class CourseService {
    static final Map<Integer, Course> COURSE_MAP = new HashMap<>();

    static {
        Course course = new Course(1, "Intro to CS");
        COURSE_MAP.put(course.getCourseNumber(), course);
    }

    @RequestMapping(value = "/number/{number}", method = RequestMethod.GET)
    public ResponseEntity<Course> getStudentById(@PathVariable(value = "number") Integer number) {
        return ResponseEntity.ok(COURSE_MAP.get(number));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<Collection<Course>> getAllCourses() {
        return ResponseEntity.ok(COURSE_MAP.values());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createCourse(@RequestBody @Valid Course course) {
        if (COURSE_MAP.containsKey(course.getCourseNumber())) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        COURSE_MAP.put(course.getCourseNumber(), course);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity updateStudent(@RequestBody Course course) {
        if (!COURSE_MAP.containsKey(course.getCourseNumber())) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        COURSE_MAP.put(course.getCourseNumber(), course);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/number/{number}", method = RequestMethod.DELETE)
    public ResponseEntity deleteStudent(@PathVariable(value = "number") Integer number) {
        if (COURSE_MAP.remove(number) == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }
}