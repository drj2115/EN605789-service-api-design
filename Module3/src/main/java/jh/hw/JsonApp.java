package jh.hw;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonApp.class);

    public static void main(String[] args) throws Exception {

        /* Convert JSON to Object. */
        byte[] jsonData = Files.readAllBytes(Paths.get("Module3/jh_cs_courses.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        Program csProgram = objectMapper.readValue(jsonData, Program.class);
        LOGGER.info("Program JSON to object\n{}", csProgram);

        /* Convert Object to JSON. */
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        StringWriter csProgramObjToJson = new StringWriter();
        objectMapper.writeValue(csProgramObjToJson, csProgram);
        LOGGER.info("Program object to JSON\n{}", csProgramObjToJson);
    }
}