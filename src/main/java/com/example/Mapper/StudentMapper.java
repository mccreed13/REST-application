package com.example.Mapper;

import com.example.Entity.Student;
import com.example.Entity.StudentDto;
import com.example.Repository.GroupRepository;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper implements Mapper<Student, StudentDto>{

    private final GroupRepository groupRepository;

    public StudentMapper(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public StudentDto mapEntityToDto(Student source) throws RuntimeException {
        StudentDto target = new StudentDto();
        target.setId(source.getId());
        target.setName(source.getName());
        target.setGroup(source.getGroup().getId());
        return target;
    }

    @Override
    public Student mapDtoToEntity(StudentDto source) throws RuntimeException {
        return new Student(source.getId(), source.getName(), groupRepository.findById(source.getGroup()).orElseThrow());
    }
}
