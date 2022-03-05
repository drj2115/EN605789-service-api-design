package jh.hw;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/students/")
public class StudentService {
    static final Map<String, Student> students = new HashMap<>();

    @GET
    @Path("/id/{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent(@PathParam("id") final String id) {
        Student student = students.get(id);
        if (student == null) {
            return Response.noContent().build();
        }
        return Response.ok().entity(student).build();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudents() {
        if (students.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok().entity(students.values()).build();
    }

    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    public Response createStudent(final Student student) {
        if (students.containsKey(student.getId())) {
            return Response.notModified().build();
        }
        students.put(student.getId(), student);
        return Response.ok().build();
    }

    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    public Response updateStudent(final Student student) {
        if (!students.containsKey(student.getId())) {
            return Response.noContent().build();
        }
        students.put(student.getId(), student);
        return Response.ok().build();
    }

    @DELETE
    @Path("/id/{id}/")
    public Response deleteStudent(@PathParam("id") final String id) {
        if (students.remove(id) == null) {
            return Response.noContent().build();
        }
        return Response.ok().build();
    }
}