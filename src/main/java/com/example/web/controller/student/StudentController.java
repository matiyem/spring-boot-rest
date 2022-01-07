package com.example.web.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * created by Atiye Mousavi
 * Date: 9/13/2021
 * Time: 10:22 AM
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService service;

    @GetMapping("/")
    public List<Student> read() {
        return service.readAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> read(@PathVariable("id") Long id) {
        Student foundStudent = service.read(id);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundStudent);
        }
    }
    @PostMapping("/")
    public ResponseEntity<Student> create(@RequestBody Student student){
        Student createdStudent=service.create(student);
        URI uri= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdStudent.getId())
                .toUri();
        return ResponseEntity.created(uri).body(createdStudent);

    }
    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@RequestBody Student student,@PathVariable("id") Long id){
        Student updateStudent= service.update(id,student);
        if (updateStudent==null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(updateStudent);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
