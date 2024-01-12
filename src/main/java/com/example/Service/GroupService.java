package com.example.Service;

import com.example.Entity.Group;
import com.example.Entity.GroupDto;
import com.example.Mapper.GroupMapper;
import com.example.Repository.GroupRepository;
import com.example.Repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final StudentRepository studentRepository;

    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.studentRepository = studentRepository;
    }

    public Collection<GroupDto> listAll() {
        return groupMapper.mapEntitiesToDtos(groupRepository.findAll());
    }

    public GroupDto getGroupById(Long id) {
        Optional<Group> group =groupRepository.findById(id);
        return group.map(groupMapper::mapEntityToDto).orElse(null);
    }

    public Group getGroupByName(String name) {
        return groupRepository.findByName(name);
    }

    public GroupDto createGroup(Group group) {
        return groupMapper.mapEntityToDto(groupRepository.save(group));
    }

    public GroupDto updateGroup(Group group) {
        if (groupRepository.save(group).equals(group)) return groupMapper.mapEntityToDto(groupRepository.findByName(group.getName()));
        return null;
    }

    public void deleteGroupById(Long id) {
        studentRepository.deleteAllByGroupId(id);
        groupRepository.deleteById(id);
    }
}
