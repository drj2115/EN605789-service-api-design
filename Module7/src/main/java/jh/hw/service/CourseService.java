package jh.hw.service;

import jh.hw.model.Course;
import jh.hw.model.JsonResponse;
import jh.hw.utils.ModelValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/course", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseService {
    static final Map<Integer, Course> COURSE_MAP = new HashMap<>();

    @RequestMapping(value = "/number/{number}", method = RequestMethod.GET)
    public ResponseEntity<Course> getStudentById(@PathVariable(value = "number") Integer number) {
        return ResponseEntity.ok(COURSE_MAP.get(number));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<Collection<Course>> getAllCourses() {
        return ResponseEntity.ok(COURSE_MAP.values());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<JsonResponse> createCourse(@RequestBody Course course) {
        String violations = ModelValidator.getModelViolations(course);
        if (violations != null) {
            return JsonResponse.buildResponse(HttpStatus.BAD_REQUEST, violations);
        }
        if (COURSE_MAP.containsKey(course.getCourseNumber())) {
            return JsonResponse.buildResponse(HttpStatus.BAD_REQUEST);
        }
        COURSE_MAP.put(course.getCourseNumber(), course);
        return JsonResponse.buildResponse(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<JsonResponse> updateStudent(@RequestBody Course course) {
        String violations = ModelValidator.getModelViolations(course);
        if (violations != null) {
            return JsonResponse.buildResponse(HttpStatus.BAD_REQUEST, violations);
        }
        if (!COURSE_MAP.containsKey(course.getCourseNumber())) {
            return JsonResponse.buildResponse(HttpStatus.NO_CONTENT);
        }
        COURSE_MAP.put(course.getCourseNumber(), course);
        return JsonResponse.buildResponse(HttpStatus.OK);
    }

    @RequestMapping(value = "/number/{number}", method = RequestMethod.DELETE)
    public ResponseEntity<JsonResponse> deleteStudent(@PathVariable(value = "number") Integer number) {
        if (COURSE_MAP.remove(number) == null) {
            return JsonResponse.buildResponse(HttpStatus.NO_CONTENT);
        }
        return JsonResponse.buildResponse(HttpStatus.OK);
    }
}