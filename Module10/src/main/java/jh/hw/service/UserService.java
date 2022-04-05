package jh.hw.service;

import jh.hw.model.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class UserService {

    public static final Map<String, Student> USER_DB = Collections.synchronizedMap(new HashMap<>());

    static {
        Student student = new Student(1, "Dallas", "Texas", LocalDate.of(1990, Month.JANUARY, 1), "a@b");
        student.setPassword("mypassword");
        USER_DB.put("dtex1", student);
    }

    @Value("${security.auth.tokenName:JHUTOKEN}")
    private String authTokenName;

    @GetMapping(value = "login")
    public ResponseEntity<String> getLogin(@RequestParam(value = "username") String username,
                                        @RequestParam(value = "password") String password,
                                        HttpServletResponse response) {
        return postLogin(username, password, response);
    }

    @PostMapping(value = "login")
    public ResponseEntity<String> postLogin(@RequestParam(value = "username") String username,
                                        @RequestParam(value = "password") String password,
                                        HttpServletResponse response) {
        Student student = USER_DB.get(username);
        if (student == null || !password.equals(student.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String tokenName = authTokenName;
        String tokenValue = UUID.randomUUID().toString();
        student.setToken(tokenValue);
        USER_DB.put(username, student);
        response.addCookie(new Cookie(tokenName, tokenValue));
        return ResponseEntity.status(HttpStatus.OK).header(tokenName, tokenValue).build();
    }
}