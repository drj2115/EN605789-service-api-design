package jh.hw.module6.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jh.hw.module6.resource.Course;
import jh.hw.module6.resource.Student;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class JaxRsClient {
    public static final String API_BASE_ENDPOINT = "http://localhost:8080/Module6/api/";
    public static final String API_STUDENTS_ENDPOINT = API_BASE_ENDPOINT + "students/";
    public static final String API_COURSES_ENDPOINT = API_BASE_ENDPOINT + "courses/";

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        try {
            testStudentService(client);
            testCourseService(client);
            System.out.println("Test success - no errors!");
        } catch (IncorrectResponseException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    private static void testStudentService(Client client) throws IncorrectResponseException {
        Student student1 = new Student("1", "Steve", "Winwood");
        Response response = client.target(API_STUDENTS_ENDPOINT).request().post(Entity.entity(student1, MediaType.APPLICATION_JSON));
        verifyResponse(response, Response.Status.OK, "Create a new valid student entry");

        response = client.target(API_STUDENTS_ENDPOINT).request().post(Entity.entity(student1, MediaType.APPLICATION_JSON));
        verifyResponse(response, Response.Status.NOT_MODIFIED, " Create a duplicate entry");

        Student student2 = new Student("2", "Steve", "Perry");
        response = client.target(API_STUDENTS_ENDPOINT).request().post(Entity.entity(student2, MediaType.APPLICATION_JSON));
        verifyResponse(response, Response.Status.OK, "Create a new valid student entry");

        response = client.target(API_STUDENTS_ENDPOINT + "id/1").request().get();
        verifyResponse(response, Response.Status.OK, student1, "Read valid entry by id 1");
        response = client.target(API_STUDENTS_ENDPOINT + "id/2").request().get();
        verifyResponse(response, Response.Status.OK, student2, "Read valid entry by id 2");

        response = client.target(API_STUDENTS_ENDPOINT + "all").request().get();
        verifyResponse(response, Response.Status.OK, "Read all entries");

        Student student3 = new Student(student1.getId(), student1.getFirstName(), "Perry");
        response = client.target(API_STUDENTS_ENDPOINT).request().put(Entity.entity(student3, MediaType.APPLICATION_JSON));
        verifyResponse(response, Response.Status.OK, "Update an existing student entry");
        response = client.target(API_STUDENTS_ENDPOINT + "id/1").request().get();
        verifyResponse(response, Response.Status.OK, student3, "Verify the update of an existing student entry");

        Student student4 = new Student("4", "Steve", "Miller");
        response = client.target(API_STUDENTS_ENDPOINT).request().put(Entity.entity(student4, MediaType.APPLICATION_JSON));
        verifyResponse(response, Response.Status.NO_CONTENT, "Update a non-existing student entry");

        response = client.target(API_STUDENTS_ENDPOINT + "id/1").request().delete();
        verifyResponse(response, Response.Status.OK, "Delete an existing student entry");

        response = client.target(API_STUDENTS_ENDPOINT + "id/4").request().delete();
        verifyResponse(response, Response.Status.NO_CONTENT, "Delete a non-existing student entry");
    }

    private static void testCourseService(Client client) throws IncorrectResponseException {
        Course course1 = new Course("1", "Introduction to the Music of Journey");
        Response response = client.target(API_COURSES_ENDPOINT).request().post(Entity.entity(course1, MediaType.APPLICATION_JSON));
        verifyResponse(response, Response.Status.OK, "Create a new valid course entry");

        response = client.target(API_COURSES_ENDPOINT).request().post(Entity.entity(course1, MediaType.APPLICATION_JSON));
        verifyResponse(response, Response.Status.NOT_MODIFIED, "Create a duplicate entry");

        Course course2 = new Course("2", "Musical Decline - Journey Without Steve Perry");
        response = client.target(API_COURSES_ENDPOINT).request().post(Entity.entity(course2, MediaType.APPLICATION_JSON));
        verifyResponse(response, Response.Status.OK, "Create a new valid course entry");

        response = client.target(API_COURSES_ENDPOINT + "courseNumber/1").request().get();
        verifyResponse(response, Response.Status.OK, course1, "Read valid entry by courseNumber 1");
        response = client.target(API_COURSES_ENDPOINT + "courseNumber/2").request().get();
        verifyResponse(response, Response.Status.OK, course2, "Read valid entry by courseNumber 2");

        response = client.target(API_COURSES_ENDPOINT + "all").request().get();
        verifyResponse(response, Response.Status.OK, "Read all entries");

        Course course3 = new Course(course1.getCourseNumber(), "Introduction to the Music of Steve Winwood");
        response = client.target(API_COURSES_ENDPOINT).request().put(Entity.entity(course3, MediaType.APPLICATION_JSON));
        verifyResponse(response, Response.Status.OK, "Update an existing course entry");
        response = client.target(API_COURSES_ENDPOINT + "courseNumber/1").request().get();
        verifyResponse(response, Response.Status.OK, course3, "Verify the update of an existing course entry");

        Course course4 = new Course("4", "Introduction to the Music of the Steve Miller Band");
        response = client.target(API_COURSES_ENDPOINT).request().put(Entity.entity(course4, MediaType.APPLICATION_JSON));
        verifyResponse(response, Response.Status.NO_CONTENT, "Update a non-existing course entry");

        response = client.target(API_COURSES_ENDPOINT + "courseNumber/1").request().delete();
        verifyResponse(response, Response.Status.OK, "Delete an existing course entry");

        response = client.target(API_COURSES_ENDPOINT + "courseNumber/4").request().delete();
        verifyResponse(response, Response.Status.NO_CONTENT, "Delete a non-existing course entry");
    }

    public static <T> void  verifyResponse(Response response, Response.Status expectedStatus, T expectedEntity, String message) throws IncorrectResponseException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            T responseEntity = (T)objectMapper.readValue(response.readEntity(String.class), expectedEntity.getClass());
            if (!responseEntity.equals(expectedEntity)) {
                throw new IncorrectResponseException(String.format("%s\nExpected: [%s]\nActual: [%s]", message, expectedEntity.toString(), responseEntity.toString()));
            }
            verifyResponse(response, expectedStatus, message);
        } catch (JsonProcessingException e) {
            throw new IncorrectResponseException(e.getMessage());
        }
    }

    public static void verifyResponse(Response response, Response.Status expectedStatus, String message) throws IncorrectResponseException {
        if (response.getStatus() != expectedStatus.getStatusCode()) {
            throw new IncorrectResponseException(String.format("%s\nExpected: [%s]\nActual: [%s]", message, expectedStatus.getStatusCode(), response.getStatus()));
        }
    }

    public static class IncorrectResponseException extends Exception {
        public IncorrectResponseException(String string) {
            super(string);
        }
    }
}