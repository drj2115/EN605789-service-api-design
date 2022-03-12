package jh.hw.service;

import jh.hw.model.Student;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value = "/student", produces = "application/json")
public class StudentService {
    static final Map<Integer, Student> STUDENT_MAP = new HashMap<>();

    static {
        Student student = new Student(1, "Dallas", "Jones", "2020-01-03","steve@gmail.com");
        STUDENT_MAP.put(student.getStudentId(), student);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudentById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(STUDENT_MAP.get(id));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(STUDENT_MAP.values());
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity createStudent(@RequestBody Student student) {
        if (STUDENT_MAP.containsKey(student.getStudentId())) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        STUDENT_MAP.put(student.getStudentId(), student);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity updateStudent(@RequestBody Student student) {
        if (!STUDENT_MAP.containsKey(student.getStudentId())) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        STUDENT_MAP.put(student.getStudentId(), student);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteStudent(@PathVariable(value = "id") Integer id) {
        if (STUDENT_MAP.remove(id) == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }
}