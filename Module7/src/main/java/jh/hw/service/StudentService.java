package jh.hw.service;

import jh.hw.model.Student;
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
    public ResponseEntity createStudent(@RequestBody @Valid Student student) {
        STUDENT_MAP.put(student.getStudentId(), student);
        return ResponseEntity.ok().build();
    }
/*

    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    public Response createStudent(final Student student) {
        String violations = ModelValidator.getModelViolations(student);
        if (violations != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(violations).build();
        }
        if (STUDENT_MAP.containsKey(student.getId())) {
            return Response.notModified().build();
        }
        STUDENT_MAP.put(student.getId(), student);
        return Response.ok().build();
    }

    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    public Response updateStudent(final Student student) {
        String violations = ModelValidator.getModelViolations(student);
        if (violations != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(violations).build();
        }
        if (!STUDENT_MAP.containsKey(student.getId())) {
            return Response.noContent().build();
        }
        STUDENT_MAP.put(student.getId(), student);
        return Response.ok().build();
    }

    @DELETE
    @Path("/id/{id}/")
    public Response deleteStudent(@PathParam("id") final String id) {
        if (STUDENT_MAP.remove(id) == null) {
            return Response.noContent().build();
        }
        return Response.ok().build();
    }
 */
}