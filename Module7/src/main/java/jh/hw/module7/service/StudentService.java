package jh.hw.module7.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jh.hw.module7.model.JsonResponse;
import jh.hw.module7.model.Student;
import jh.hw.module7.utils.ModelValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "student", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Students", description = "The Student API")
public class StudentService {
    static final Map<Integer, Student> STUDENT_MAP = new HashMap<>();

    @GetMapping("{id}")
    @Operation(summary = "Get a student")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successful request")
    })
    public ResponseEntity<Student> getStudentById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(STUDENT_MAP.get(id));
    }

    @GetMapping
    @Operation(summary = "Get all students")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successful request")
    })
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(STUDENT_MAP.values());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a student")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "successfully created a student"),
            @ApiResponse(responseCode = "400", description = "bad request - improper student entity"),
            @ApiResponse(responseCode = "304", description = "not modified - the student already exists")
    })
    public ResponseEntity<JsonResponse> createStudent(@RequestBody Student student) {
        String violations = ModelValidator.getModelViolations(student);
        if (violations != null) {
            return JsonResponse.buildResponse(HttpStatus.BAD_REQUEST, violations);
        }
        if (STUDENT_MAP.containsKey(student.getStudentId())) {
            return JsonResponse.buildResponse(HttpStatus.NOT_MODIFIED);
        }
        STUDENT_MAP.put(student.getStudentId(), student);
        return JsonResponse.buildResponse(HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Modify an existing student")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successfully modified a student"),
            @ApiResponse(responseCode = "400", description = "bad request - improper student entity"),
            @ApiResponse(responseCode = "204", description = "no content - the student does not exist")
    })
    public ResponseEntity<JsonResponse> updateStudent(@RequestBody Student student) {
        String violations = ModelValidator.getModelViolations(student);
        if (violations != null){
            return JsonResponse.buildResponse(HttpStatus.BAD_REQUEST, violations);
        }
        if (!STUDENT_MAP.containsKey(student.getStudentId())) {
            return JsonResponse.buildResponse(HttpStatus.NO_CONTENT);
        }
        STUDENT_MAP.put(student.getStudentId(), student);
        return JsonResponse.buildResponse(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a student")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "successfully deleted the student"),
            @ApiResponse(responseCode = "204", description = "no content - the student does not exist")
    })
    public ResponseEntity<JsonResponse> deleteStudent(@PathVariable(value = "id") Integer id) {
        if (STUDENT_MAP.remove(id) == null) {
            return JsonResponse.buildResponse(HttpStatus.NOT_FOUND);
        }
        return JsonResponse.buildResponse(HttpStatus.OK);
    }
}