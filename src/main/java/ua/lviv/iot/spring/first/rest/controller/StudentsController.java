package ua.lviv.iot.spring.first.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.spring.first.rest.business.StudentService;
import ua.lviv.iot.spring.first.rest.model.Student;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RequestMapping("/students")
@RestController
public class StudentsController {

    private Map<Integer, Student> students = new HashMap<>();

    private AtomicInteger idCounter = new AtomicInteger();

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getStudents() {
        return new LinkedList<Student>(students.values());
    }

    @GetMapping("/{id}")
    public Student getStudent(final @PathVariable("id") Integer studentId) {
        return students.get(studentId);
    }

    @PostMapping
    public Student createStudent(final @RequestBody Student student) {
        System.out.println(studentService.createStudent(student));

        student.setId(idCounter.incrementAndGet());
        students.put(student.getId(), student);
        return student;
    }

    @PutMapping("/{id}")
    public ResponseEntity updateStudent(final @PathVariable("id") Integer studentId, final @RequestBody Student student) {
        if (students.remove(studentId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            student.setId(studentId);
            students.put(student.getId(), student);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudent(@PathVariable("id") Integer studentId) {
        HttpStatus status = students.remove(studentId) == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return ResponseEntity.status(status).build();
    }
}