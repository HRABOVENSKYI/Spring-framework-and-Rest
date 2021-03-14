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

@RequestMapping("/students")
@RestController
public class StudentController {

    private final Map<Integer, Student> students = new HashMap<>();

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents(
            final @RequestParam(value = "firstName", required = false) String firstName) {
        if (firstName == null) {
            return studentService.getStudents();
        }
        return studentService.getAllByFirstName(firstName);
    }

    @GetMapping("/{id}")
    public Student getStudent(final @PathVariable("id") Integer studentId) {
        return students.get(studentId);
    }

    @PostMapping
    public Student registerNewStudent(final @RequestBody Student student) {
        return studentService.addNewStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Integer studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping("/{id}")
    public Student updateStudent(final @PathVariable("id") Integer studentId,
                                 final @RequestParam(required = false) String firstName,
                                 final @RequestParam(required = false) String fullName) {
        return studentService.updateStudent(studentId, firstName, fullName);
    }
}