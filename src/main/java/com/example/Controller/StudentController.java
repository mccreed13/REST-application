package com.example.Controller;

import com.example.Entity.Student;
import com.example.Entity.StudentDto;
import com.example.Service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@Tag(name = "Student", description = "operation with students")
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get by id",
            description = "Get Student by identifier",
            parameters = {@Parameter(name = "id", description = "Student id", example = "1")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
    })
    ResponseEntity<StudentDto> getStudent(@PathVariable("id") Long id) {
        StudentDto student = studentService.getStudentById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(
            summary = "Get all Students items",
            description = "Get all Students"
    )
    Iterable<StudentDto> getStudents() {
        return studentService.listAll();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete by id",
            description = "Delete Student by identifier",
            parameters = {@Parameter(name = "id", description = "Student id", example = "1")}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Success. No content", content = @Content)
    })
    void delete(@PathVariable("id") Long id) {
        studentService.deleteStudentById(id);
    }

    @PostMapping
    @Operation(
            summary = "Create new Student",
            description = "Create new Student with all parameters"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Some parameters are incorrect", content = @Content),
    })
    ResponseEntity<Student> create(@RequestBody Student studentDto) {
        if(studentDto.getId() != null || studentDto.getName() == null){
            return ResponseEntity.badRequest().build();
        }
        if(studentService.getStudentByName(studentDto.getName()) != null){
            return ResponseEntity.badRequest().build();
        }
        Student student = studentService.createStudent(new Student(studentDto.getName(), studentDto.getGroup()));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(student.getId()).toUri();
        return ResponseEntity.created(uri).body(student);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Change Student by id",
            description = "Change Student by identifier",
            parameters = {@Parameter(name = "id", description = "Student id", example = "1")}

    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Changed"),
            @ApiResponse(responseCode = "400", description = "Some parameters are incorrect", content = @Content),
    })
    public ResponseEntity<Student> edit(@PathVariable("id") Long id, @RequestBody Student studentDto){
        if(studentDto.getId() != null && !studentDto.getId().equals(id)){
            return ResponseEntity.badRequest().build();
        }
        Student student = new Student(id, studentDto.getName(), studentDto.getGroup());
        return ResponseEntity.ok(studentService.updateStudent(student));
    }

    @GetMapping("/free")
    @Operation(
            summary = "Get students without group.",
            description = "Get students without group."
    )
    Collection<StudentDto> getFreeStudents() {
        return studentService.getFreeStudentList();
    }

}
