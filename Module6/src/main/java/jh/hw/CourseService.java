package jh.hw;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/courses/")
public class CourseService {
    static final Map<String, Course> COURSE_MAP = new HashMap<>();

    @GET
    @Path("/courseNumber/{courseNumber}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourse(@PathParam("courseNumber") final String courseNumber) {
        Course course = COURSE_MAP.get(courseNumber);
        if (course == null) {
            return Response.noContent().build();
        }
        return Response.ok().entity(course).build();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCourses() {
        if (COURSE_MAP.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok().entity(COURSE_MAP.values()).build();
    }

    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    public Response createCourse(final Course course) {
        String violations = ModelValidator.getModelViolations(course);
        if (violations != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(violations).build();
        }
        if (COURSE_MAP.containsKey(course.getCourseNumber())) {
            return Response.notModified().build();
        }
        COURSE_MAP.put(course.getCourseNumber(), course);
        return Response.ok().build();
    }

    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    public Response updateCourse(final Course course) {
        String violations = ModelValidator.getModelViolations(course);
        if (violations != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(violations).build();
        }
        if (!COURSE_MAP.containsKey(course.getCourseNumber())) {
            return Response.noContent().build();
        }
        COURSE_MAP.put(course.getCourseNumber(), course);
        return Response.ok().build();
    }

    @DELETE
    @Path("/courseNumber/{courseNumber}/")
    public Response deleteCourse(@PathParam("courseNumber") final String courseNumber) {
        if (COURSE_MAP.remove(courseNumber) == null) {
            return Response.noContent().build();
        }
        return Response.ok().build();
    }
}