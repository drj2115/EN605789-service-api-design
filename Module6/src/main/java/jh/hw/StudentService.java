package jh.hw;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/students/")
public class StudentService {
    static final Map<String, Student> STUDENT_MAP = new HashMap<>();

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
}