package com.example.Service;

import com.example.Entity.Student;
import com.example.Entity.StudentDto;
import com.example.Mapper.StudentMapper;
import com.example.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public Collection<StudentDto> getStudentsByGroup(Long id) {
        return studentMapper.mapEntitiesToDtos(studentRepository.getStudentsByGroupId(id));
    }

    public Collection<StudentDto> listAll() {
        return studentMapper.mapEntitiesToDtos(studentRepository.findAll());
    }

    public StudentDto getStudentById(Long id) {
        Optional<Student> student =studentRepository.findById(id);
        return student.map(studentMapper::mapEntityToDto).orElse(null);
    }

    public Student getStudentByName(String name) {
        return studentRepository.findByName(name);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        if (studentRepository.save(student).equals(student)) {
            return studentRepository.findByName(student.getName());
        }
        return null;
    }

    public void deleteStudent(Student student) {
        studentRepository.delete(student);
    }

    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    public Collection<StudentDto> getFreeStudentList() {
        return studentMapper.mapEntitiesToDtos(studentRepository.getFreeStudentList());
    }
}
