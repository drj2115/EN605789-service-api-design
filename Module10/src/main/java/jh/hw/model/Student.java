package jh.hw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Student {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd";

    @Id
    @NotNull
    private Integer studentId;

    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    @DateTimeFormat(pattern = DATE_TIME_FORMAT)
    private LocalDate dateOfBirth;

    @NotBlank
    @Pattern(regexp = "[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z0-9]+")
    private String email;

    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String token;

    public Student() {
    }

    public Student(Integer studentId, String firstName, String lastName, LocalDate dateOfBirth, String email) {
        setStudentId(studentId);
        setFirstName(firstName);
        setLastName(lastName);
        setDateOfBirth(dateOfBirth);
        setEmail(email);
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + getStudentId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", dateOfBirth='" + getDateOfBirth() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student)o;
        return Objects.equals(studentId, student.studentId) &&
               Objects.equals(firstName, student.firstName) &&
               Objects.equals(lastName, student.lastName) &&
               Objects.equals(dateOfBirth, student.dateOfBirth) &&
               Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, firstName, lastName, dateOfBirth, email);
    }
}