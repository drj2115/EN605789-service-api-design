package org.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student {

   private int    id;
   private String firstName;
   private String lastName;
   private String address;

   /* NO-ARG CONSTRUCTOR */ 
   Student() {
      System.out.println("Call Student default, no-args constructor  ");
   }

   /* CONSTRUCTOR with ARGS */
   Student (int id, String firstname, String lastname, String address) {
       this.id = id;
       this.firstName = firstname;
       this.lastName = lastname;
       this.address = address;
       System.out.println("Call Student constructor with args .....+++++ ");
    }

   public int getId() {
      return id;
   }
   public void setId(int id) {
      this.id = id;
   }
   public String getFirstName() {
      return firstName;
   } 
   public void setFirstName(String firstName) { 
      this.firstName = firstName; 
   }
   public String getLastName() { 
      return lastName; 
   } 
   public void setLastName(String lastName) {
      this.lastName = lastName; 
   } 
   public String getAddress() { 
      return address; 
   }
   public void setAddress(String address) { 
      this.address = address; 
   } 
}

