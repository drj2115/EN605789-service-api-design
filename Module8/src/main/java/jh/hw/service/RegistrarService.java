package jh.hw.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jh.hw.model.Course;
import jh.hw.model.JsonResponse;
import jh.hw.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "registrar", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Registrar", description = "The Registrar API")
public class RegistrarService {
    private static final Map<Integer, Set<Student>> COURSE_STUDENT_MAP = new HashMap<>();
    private static final int COURSE_MAX_STUDENTS = 15;

    @PostMapping("{courseNumber}/{studentId}")
    @Operation(summary = "Register a student to a course")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successful request"),
            @ApiResponse(responseCode = "304", description = "not modified - the student is either already in the course or the course is full"),
            @ApiResponse(responseCode = "400", description = "bad request - invalid course number or student id")
    })
    public ResponseEntity<JsonResponse> registerStudent(@PathVariable("courseNumber") Integer courseNumber,
                                                        @PathVariable("studentId") Integer studentId) {
        Student student = StudentService.STUDENT_MAP.get(studentId);
        Course course = CourseService.COURSE_MAP.get(courseNumber);
        if (student == null || course == null) {
            return JsonResponse.buildResponse(HttpStatus.BAD_REQUEST);
        }
        Set<Student> studentsInCourse = COURSE_STUDENT_MAP.getOrDefault(courseNumber, new HashSet<>());
        if (studentsInCourse.contains(student) || studentsInCourse.size() >= COURSE_MAX_STUDENTS) {
            return JsonResponse.buildResponse(HttpStatus.NOT_MODIFIED);
        }
        studentsInCourse.add(student);
        COURSE_STUDENT_MAP.put(courseNumber, studentsInCourse);
        return JsonResponse.buildResponse(HttpStatus.OK);
    }

    @GetMapping(value = "{courseNumber}")
    @Operation(summary = "Get the list of registered students by course")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successful request"),
            @ApiResponse(responseCode = "204", description = "no content - the course has not students"),
            @ApiResponse(responseCode = "400", description = "bad request - invalid course number")
    })
    public ResponseEntity<?> getRegisteredStudentsByCourseNumber(@PathVariable("courseNumber") final Integer courseNumber) {
        if (!CourseService.COURSE_MAP.containsKey(courseNumber)) {
            COURSE_STUDENT_MAP.remove(courseNumber);
            return JsonResponse.buildResponse(HttpStatus.BAD_REQUEST);
        }
        Set<Student> studentsInCourse = COURSE_STUDENT_MAP.get(courseNumber);
        if (studentsInCourse == null || studentsInCourse.isEmpty()) {
            return JsonResponse.buildResponse(HttpStatus.NO_CONTENT);
        }
        studentsInCourse.removeIf(student -> !StudentService.STUDENT_MAP.containsKey(student.getStudentId()));

        return ResponseEntity.ok(studentsInCourse);
    }

    @DeleteMapping(value = "{courseNumber}/{studentId}")
    @Operation(summary = "Delete a student from a course")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successful request"),
            @ApiResponse(responseCode = "304", description = "not modified - the student is not in the course"),
            @ApiResponse(responseCode = "400", description = "bad request - invalid course number or student id")
    })
    public ResponseEntity<JsonResponse> deleteStudentFromCourse(@PathVariable("courseNumber") final Integer courseNumber,
                                                                @PathVariable("studentId") final Integer studentId) {
        Student student = StudentService.STUDENT_MAP.get(studentId);
        Course course = CourseService.COURSE_MAP.get(courseNumber);
        if (student == null || course == null) {
            return JsonResponse.buildResponse(HttpStatus.BAD_REQUEST);
        }
        Set<Student> studentsInCourse = COURSE_STUDENT_MAP.get(courseNumber);
        if (studentsInCourse == null || !studentsInCourse.contains(student)) {
            return JsonResponse.buildResponse(HttpStatus.NOT_MODIFIED);
        }
        studentsInCourse.remove(student);
        return JsonResponse.buildResponse(HttpStatus.OK);
    }
}