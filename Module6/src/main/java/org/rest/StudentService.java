package org.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/studentservice/")
@Produces("text/xml")
public class StudentService {
    int currentId = 123;

    Map<Integer, Student> students = new HashMap<Integer, Student>();

    public StudentService() {
        init();
    }


  @GET
//  @Produces(MediaType.TEXT_PLAIN)
  public String getTestService() {
    return "Hello from StudentService, dear 605.785 students !!! ";
  }

  @GET
  @Path("/ok")
  public Response getOkResponse() {

    String message = "This is a text response, dear 605.785 students !!! ";

    return Response
      .status(Response.Status.OK)
      .entity(message)
      .build();
}


    @GET
    @Path("/students/{id}/")
    @Produces (MediaType.APPLICATION_JSON)
    public Student getStudent(@PathParam("id") String id) {
        System.out.println("This is from getStudents, to produces JSON +++++ invoking getStudent, Student id is: " + id);
        Integer idNumber = new Integer(id);
        Student c = students.get(idNumber); 
        return c;
    }

    @PUT
    @Path("/students/")
    public Response updateStudent(Student student) {
        System.out.println("This is from updateStudents, Student name is: " + student.getFirstName() + "   " + student.getLastName() + "   " + student.getAddress());
        Student c = students.get(student.getId());
        Response r;
        if (c != null) {
            students.put(student.getId(), student);
            r = Response.ok().build();
        } else {
            r = Response.notModified().build();
        }

        return r;
    }

    @POST
    @Path("/students/")
    @Consumes ({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.TEXT_PLAIN)
    public String addStudent(Student student) {
        System.out.println("++++ invoking addStudent, Student name is: " + student.getFirstName() + "   " + student.getLastName() + "   " + student.getAddress());
        
        int id = ++currentId;

        student.setId(id);

        students.put(student.getId(), student);

//        String returnString = 
        return String.valueOf(student.getId()) +  student.getFirstName() + student.getLastName();
    }

    @DELETE
    @Path("/students/{id}/")
    public Response deleteStudent(@PathParam("id") String id) {
        System.out.println("----invoking deleteStudent, Student id is: " + id);
//        long idNumber = int.parseint(id);
        Student c = students.get(id);

        Response r;
        if (c != null) {
            r = Response.ok().build();
            students.remove(id);
        } else {
            r = Response.notModified().build();
        }

        return r;
    }

    final void init() {
        Student c = new Student();
        c.setFirstName("Joe");
        c.setLastName("Doe"); 
        c.setAddress("somewhere"); 
        c.setId(123);
        students.put(c.getId(), c);

        System.out.println("----initiate Student from demo1 app .....  " + c.getId() + "    " + 
                        c.getFirstName() + "   " + c.getLastName() + "   " + c.getAddress());
    }

}
