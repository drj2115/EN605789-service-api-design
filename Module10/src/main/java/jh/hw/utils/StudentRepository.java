package jh.hw.utils;

import jh.hw.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {
    Student findStudentByToken(String token);
    Student findStudentByUsername(String username);
    List<Student> findAllBy();
}