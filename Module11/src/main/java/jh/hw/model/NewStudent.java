package jh.hw.model;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/* Helper class so that Student username and password display correctly in Swagger API. */
public class NewStudent {
    @NotNull
    @Valid
    Student student;

    @NotEmpty
    String username;

    @NotEmpty
    String password;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}