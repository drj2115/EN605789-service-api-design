package jh.hw.security;

import jh.hw.model.Student;
import jh.hw.utils.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    StudentRepository studentRepository;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        Object username = usernamePasswordAuthenticationToken.getCredentials();
        Student student = studentRepository.findStudentByUsername((String)username);
        if (student == null) {
            throw new AuthenticationCredentialsNotFoundException("Invalid username - " + username);
        }
        return new User(student.getUsername(), student.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList("USER"));
    }
}