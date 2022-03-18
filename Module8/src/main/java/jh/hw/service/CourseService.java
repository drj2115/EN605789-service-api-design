package jh.hw.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jh.hw.model.Course;
import jh.hw.model.JsonResponse;
import jh.hw.utils.ModelValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "course", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Courses", description = "The Course API")
public class CourseService {
    static final Map<Integer, Course> COURSE_MAP = new HashMap<>();

    @GetMapping(value = "{number}")
    @Operation(summary = "Get course by course number")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "successful request")
    })
    public ResponseEntity<Course> getCourseByNumber(@PathVariable(value = "number") Integer number) {
        return ResponseEntity.ok(COURSE_MAP.get(number));
    }

    @GetMapping
    @Operation(summary = "Get all courses")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "successful request")
    })
    public ResponseEntity<Collection<Course>> getAllCourses() {
        return ResponseEntity.ok(COURSE_MAP.values());
    }

    @PostMapping
    @Operation(summary = "Create a course")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "successfully created a course"),
        @ApiResponse(responseCode = "400", description = "bad request - improper course entity"),
        @ApiResponse(responseCode = "304", description = "not modified - the course already exists")
    })
    public ResponseEntity<JsonResponse> createCourse(@RequestBody Course course) {
        String violations = ModelValidator.getModelViolations(course);
        if (violations != null) {
            return JsonResponse.buildResponse(HttpStatus.BAD_REQUEST, violations);
        }
        if (COURSE_MAP.containsKey(course.getCourseNumber())) {
            return JsonResponse.buildResponse(HttpStatus.NOT_MODIFIED);
        }
        COURSE_MAP.put(course.getCourseNumber(), course);
        return JsonResponse.buildResponse(HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Modify an existing course")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "successfully modified a course"),
        @ApiResponse(responseCode = "400", description = "bad request - improper course entity"),
        @ApiResponse(responseCode = "204", description = "no content - the course does not exist")
    })
    public ResponseEntity<JsonResponse> updateCourse(@RequestBody Course course) {
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

    @DeleteMapping(value = "{number}")
    @Operation(summary = "Delete a course")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successfully deleted the course"),
            @ApiResponse(responseCode = "204", description = "no content - the course does not exist")
    })
    public ResponseEntity<JsonResponse> deleteCourse(@PathVariable(value = "number") Integer number) {
        if (COURSE_MAP.remove(number) == null) {
            return JsonResponse.buildResponse(HttpStatus.NO_CONTENT);
        }
        return JsonResponse.buildResponse(HttpStatus.OK);
    }
}