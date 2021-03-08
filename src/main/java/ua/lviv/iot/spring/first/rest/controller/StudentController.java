package ua.lviv.iot.spring.first.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.spring.first.rest.business.StudentService;
import ua.lviv.iot.spring.first.rest.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RequestMapping("/students")
@RestController
public class StudentController {

    private Map<Integer, Student> students = new HashMap<>();

    private AtomicInteger idCounter = new AtomicInteger();

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getStudents(
            final @RequestParam(value = "firstName", required = false) String firstName) {
        if (firstName == null) {
            return studentService.findAll();
        }
        return studentService.getAllByFirstName(firstName);
    }

    @GetMapping("/{id}")
    public Student getStudent(final @PathVariable("id") Integer studentId) {
        return students.get(studentId);
    }

    @PostMapping
    public Student createStudent(final @RequestBody Student student) {
        return studentService.createStudent(student);
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