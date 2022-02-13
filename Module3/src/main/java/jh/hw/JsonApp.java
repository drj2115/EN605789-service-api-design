package jh.hw;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonApp {
    public static void main(String[] args) throws Exception {
        //to read data from JSON file to String *************************************

        byte[] jsonData = Files.readAllBytes(Paths.get("Module3/jh_cs_courses.json"));

        //create ObjectMapper instance **********************************************

        ObjectMapper objectMapper = new ObjectMapper();

        //convert json string to object *********************************************

        Program csProgram = objectMapper.readValue(jsonData, Program.class);

        System.out.println("Program Object\n"+csProgram);

        //convert Object to json string *********************************************

//        Student stud1 = createStudent();
//
//        //configure Object mapper for pretty print **********************************
//
//        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//
//        //writing to console, can write to any output stream such as file ***********
//
//        StringWriter stringstud = new StringWriter();
//        objectMapper.writeValue(stringstud, stud1);
//        System.out.println("Student JSON is\n"+stringstud);
    }
}