package jh.hw.service;

import jh.hw.model.JsonResponse;
import jh.hw.model.Student;
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
@RequestMapping(value = "/student", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentService {
    static final Map<Integer, Student> STUDENT_MAP = new HashMap<>();

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudentById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(STUDENT_MAP.get(id));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(STUDENT_MAP.values());
    }

    @RequestMapping(method = RequestMethod.POST)
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

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<JsonResponse> updateStudent(@RequestBody Student student) {
        String violations = ModelValidator.getModelViolations(student);
        if (violations != null) {
            return JsonResponse.buildResponse(HttpStatus.BAD_REQUEST, violations);
        }
        if (!STUDENT_MAP.containsKey(student.getStudentId())) {
            return JsonResponse.buildResponse(HttpStatus.NO_CONTENT);
        }
        STUDENT_MAP.put(student.getStudentId(), student);
        return JsonResponse.buildResponse(HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<JsonResponse> deleteStudent(@PathVariable(value = "id") Integer id) {
        if (STUDENT_MAP.remove(id) == null) {
            return JsonResponse.buildResponse(HttpStatus.NOT_FOUND);
        }
        return JsonResponse.buildResponse(HttpStatus.OK);
    }
}