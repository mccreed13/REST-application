package com.example.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "groups")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@NamedQuery(name = "Group.findByName", query = "select g from Group g where g.name = ?1")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "group")
    private List<Student> students;

    public Group(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Group(String name) {
        this.id = 0L;
        this.name = name;
    }


    @Override
    public String toString() {
        return id + "," + name;
    }
}
