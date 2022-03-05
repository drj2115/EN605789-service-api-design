package jh.hw;
 
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.HeaderParam  ;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;  
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MediaType;


import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

/**
 * @ 605.785
 *
 */

@Path("/testservice")
public class TestService {


  /*  this method is not annotated purposely for testing if it will be ever called */

  public String noAnnotationTestService() {
    return "Hello 605.785 class from getTestService NOT-ANNOTATED method !";
  }

 
  @GET
  public String getTestService() {
    return "Hello 605.785 class from getTestService method annotated with @GET !";
  }




  @GET
  @Path("/ok")
  public Response getOkResponse() {
    String message = "This is a text response from getTestService method!";
    return Response
      .status(Response.Status.OK)
      .entity(message)
      .build();
}

  @POST
  @Consumes ("text/plain") 
  public String postResponse(String str) {
    String message = "This is a text response from postResponse, just received ...... " + str;
     return message;
}

  @GET
  @Path("ping")
  public String getServerTime() {
        System.out.println("RESTful Service 'TestService' is running ==> ping");
        return "received ping on " + new Date().toString();
  }

  @GET
  @Path("/all_students")
  @Produces({MediaType.APPLICATION_JSON})  //add MediaType.APPLICATION_XML if you want XML as well (don't forget @XmlRootElement)
  public List<Student> getAllStudents() throws Exception{      
        List<Student> students = new ArrayList<>();
        Student s = new Student();
        s.setId("123");
        s.setFirstName("Leonid");
        s.setLastName("Felikson");
//        s.setAddress("Leonid's address");
        students.add(s);
        
        System.out.println("getAllStudents(): found "+students.size()+" student(s)");
        
        return students; 
}
    
  @POST
  @Consumes({MediaType.APPLICATION_JSON}) 
  @Produces({MediaType.TEXT_PLAIN})
  @Path("/post")
  public String postStudent(Student stud) throws Exception{

        System.out.println("First Name = " + stud.getFirstName());
        System.out.println("Last Name  = " + stud.getLastName());
//        System.out.println("Address    = " + stud.getAddress());
        
        return "ok";
  }

  @GET
  @Path("/not_ok")
  public Response getNOkTextResponse() {

     String message = "There was an internal server error";

     return Response
       .status(Response.Status.INTERNAL_SERVER_ERROR)
       .entity(message)
       .build();
  }

  @GET
  @Path("/text_plain")
  public Response getTextResponseTypeDefined() {

     String message = "This is a plain text response";

     return Response
      .status(Response.Status.OK)
      .entity(message)
      .type(MediaType.TEXT_PLAIN)
      .build();
}

  @GET
  @Path("/text_plain_annotation")
  @Produces({ MediaType.TEXT_PLAIN })
  public Response getTextResponseTypeAnnotated() {

    String message = "This is a plain text response via annotation";

    return Response
      .status(Response.Status.OK)
      .entity(message)
      .build();
}

  @GET
  @Path("/pojo_json")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPojoResponse() {

//    Student student = new Student (987, "Leonid", "Felikson", "Leonid's address");
Student student = new Student();
student.setId("987");
student.setLastName("steve");
student.setFirstName("win");
       System.out.println("++++++++++++++ " +  student.getFirstName() + "   " + student.getLastName());
    return Response
      .status(Response.Status.OK)
      .entity(student)
      .type(MediaType.APPLICATION_JSON)
      .build();

  }

  @GET
  @Path("/pojo_xml")
  @Produces(MediaType.APPLICATION_XML)
  public Response getPojoResponseXML() {

//    Student student = new Student(123, "Leonid_XML", "Felikson_XML", "address_XML");
        Student student = new Student();
      student.setId("987");
      student.setLastName("steve");
      student.setFirstName("windsadfasd");

       System.out.println("++++ " + student.getFirstName() +  "  " + student.getFirstName());

    return Response
      .status(Response.Status.OK)
      .entity(student)
      .type(MediaType.APPLICATION_XML)
      .build();

  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("/header_elements/{id}")
  public Response getListOfHeaderElements( @Context              HttpHeaders  httpHeaders,
                                           @HeaderParam("Host1") String       host,
                                           @PathParam("id")      int          id          ){
    
    String userAgent   = httpHeaders.getRequestHeader("User-Agent").get(0);
    String contentType = httpHeaders.getRequestHeader("Content-Type").get(0);
    
    return Response.status(200)
               .entity("User-Agent :- " + userAgent + " \nContent-Type :- " + contentType + 
                       " \n ... id:   " + id        + " \n ... Host1:     " + host)  
               .build();
   }

}







