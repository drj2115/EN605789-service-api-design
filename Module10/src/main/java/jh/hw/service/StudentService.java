package jh.hw.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jh.hw.model.JsonResponse;
import jh.hw.model.Student;
import jh.hw.utils.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(value = "student", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Students", description = "The Student API")
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("{id}")
    @Operation(summary = "Get a student")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "successful request")
    })
    public ResponseEntity<Student> getStudentById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(studentRepository.findById(id).orElse(null));
    }

    @GetMapping
    @Operation(summary = "Get all students")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "successful request")
    })
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentRepository.findAllBy());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a student")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "successfully created a student"),
        @ApiResponse(responseCode = "400", description = "bad request - improper student entity"),
        @ApiResponse(responseCode = "304", description = "not modified - the student already exists")
    })
    public ResponseEntity<JsonResponse> createStudent(@RequestBody @Valid Student student) {
        if (studentRepository.existsById(student.getStudentId())) {
            return JsonResponse.buildResponse(HttpStatus.NOT_MODIFIED);
        }
        studentRepository.save(student);
        return JsonResponse.buildResponse(HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Modify an existing student")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "successfully modified a student"),
        @ApiResponse(responseCode = "400", description = "bad request - improper student entity"),
        @ApiResponse(responseCode = "204", description = "no content - the student does not exist")
    })
    public ResponseEntity<JsonResponse> updateStudent(@RequestBody @Valid Student student) {
        if (!studentRepository.existsById(student.getStudentId())) {
            return JsonResponse.buildResponse(HttpStatus.NO_CONTENT);
        }
        studentRepository.save(student);
        return JsonResponse.buildResponse(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a student")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "successfully deleted the student"),
        @ApiResponse(responseCode = "204", description = "no content - the student does not exist")
    })
    public ResponseEntity<JsonResponse> deleteStudent(@PathVariable(value = "id") Integer id) {
        if (!studentRepository.existsById(id)) {
            return JsonResponse.buildResponse(HttpStatus.NOT_FOUND);
        }
        studentRepository.deleteById(id);
        return JsonResponse.buildResponse(HttpStatus.OK);
    }
}