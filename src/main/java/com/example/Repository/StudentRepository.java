package com.example.Repository;

import com.example.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    void deleteAllByGroupId(Long id);

    List<Student> getStudentsByGroupId(Long id);

    Student findByName(String name);

    @Query("select s from Student s where s.group is NULL")
    List<Student> getFreeStudentList();
}
