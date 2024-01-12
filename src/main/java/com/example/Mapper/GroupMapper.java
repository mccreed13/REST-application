package com.example.Mapper;

import com.example.Entity.Group;
import com.example.Entity.GroupDto;
import com.example.Repository.StudentRepository;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper implements Mapper<Group, GroupDto>{
    private final StudentRepository studentRepository;

    public GroupMapper(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public GroupDto mapEntityToDto(Group source) throws RuntimeException {
        return new GroupDto(source.getId(), source.getName());
    }

    @Override
    public Group mapDtoToEntity(GroupDto source) throws RuntimeException {
        return new Group(source.getId(), source.getName(), studentRepository.getStudentsByGroupId(source.getId()));
    }
}
