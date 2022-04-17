package jh.hw.service;

import jh.hw.model.Student;
import jh.hw.security.JWTUtil;
import jh.hw.utils.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    JWTUtil jwtUtil;

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
        insertDefaultValues();
        Student student = studentRepository.findStudentByUsername(username);
        if (student == null || !password.equals(student.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String tokenName = authTokenName;
        String tokenValue = jwtUtil.generateToken("User Details", "user", username);
        try {
            studentRepository.save(student);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        response.addCookie(new Cookie(tokenName, tokenValue));
        return ResponseEntity.status(HttpStatus.OK).header(tokenName, tokenValue).build();
    }

    private void insertDefaultValues() {
        if (!studentRepository.existsById(1)) {
            Student student = new Student(1, "Dallas", "Texas", LocalDate.of(1999, Month.JANUARY, 1), "d@llas.com");
            student.setUsername("dtex1");
            student.setPassword("mypassword");
            try {
                studentRepository.save(student);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
}