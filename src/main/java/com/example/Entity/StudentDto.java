package com.example.Entity;

public class StudentDto {
    private Long id;
    private String name;
    private Long group;

    public StudentDto(Long id, String name, Long group) {
        this.id = id;
        this.name = name;
        this.group = group;
    }

    public StudentDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
    }
}
