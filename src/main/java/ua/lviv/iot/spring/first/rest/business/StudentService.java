package ua.lviv.iot.spring.first.rest.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.spring.first.rest.dataaccess.StudentRepository;
import ua.lviv.iot.spring.first.rest.model.Student;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getAllByFirstName(String firstName) {
        return studentRepository.findAllByFirstName(firstName);
    }

    public List<Student> getAllByFirstNameAndLastName(String firstName, String lastName) {
        return studentRepository.findAllByFirstNameAndLastName(firstName, lastName);
    }

    public Student addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByFullName(student.getFullName());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("FullName taken");
        }
        studentRepository.save(student);
        return student;
    }
}
