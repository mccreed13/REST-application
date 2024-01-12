package com.example.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "students")
public class Student {
    @Id
    private Long id;

    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id")
    private Group group;

    public Student(String name, Group group){
        this.name = name;
        this.group=group;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + group;
    }
}
