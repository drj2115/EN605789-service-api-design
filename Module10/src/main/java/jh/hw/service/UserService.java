package jh.hw.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserService {

    private static final Map<String, String> USER_DB = Collections.synchronizedMap(new HashMap<>());

    static {
        USER_DB.put("dallas", "rules");
    }

    @GetMapping(value = "login")
    public ResponseEntity<String> login(@RequestParam(value = "username") String username,
                                        @RequestParam(value = "password") String password,
                                        HttpServletResponse response) {

        if (!password.equals(USER_DB.get(username))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String tokenName = "blah";
        String tokenValue = "blahblah";
        response.addCookie(new Cookie(tokenName, tokenValue));
        return ResponseEntity.status(HttpStatus.OK).header(tokenName, tokenValue).build();
    }
}