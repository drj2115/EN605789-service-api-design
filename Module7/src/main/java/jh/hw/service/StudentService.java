package jh.hw.service;

import jh.hw.model.Student;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/student")
public class StudentService {
    static final Map<Integer, Student> STUDENT_MAP = new HashMap<>();

    static {
        Student student = new Student(1, "Dallas", "Jones", "2020-09-30", "steve@gmail.com");
        STUDENT_MAP.put(student.getStudentId(), student);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Student> getStudentById(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(STUDENT_MAP.get(id));
    }

/*
    @GET
    @Path("/id/{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent(@PathParam("id") final String id) {
        Student student = STUDENT_MAP.get(id);
        if (student == null) {
            return Response.noContent().build();
        }
        return Response.ok().entity(student).build();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudents() {
        if (STUDENT_MAP.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok().entity(STUDENT_MAP.values()).build();
    }

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