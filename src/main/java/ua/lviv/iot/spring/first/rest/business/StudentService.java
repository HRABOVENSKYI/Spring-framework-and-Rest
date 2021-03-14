package ua.lviv.iot.spring.first.rest.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.lviv.iot.spring.first.rest.dataaccess.StudentRepository;
import ua.lviv.iot.spring.first.rest.model.Student;

import java.util.List;
import java.util.Objects;
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

    public Student addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByFullName(student.getFullName());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("FullName taken");
        }
        studentRepository.save(student);
        return student;
    }

    public void deleteStudent(Integer studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException("Student with ID " + studentId + " does not exists.");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public Student updateStudent(Integer studentId, String firstName, String fullName) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with ID " + studentId + " does not exists."));

        if (firstName != null && firstName.length() > 0 &&
                !Objects.equals(student.getFirstName(), firstName)) {
            student.setFirstName(firstName);
        }

        if (fullName != null && fullName.length() > 0 &&
                !Objects.equals(student.getFullName(), fullName)) {
            Optional<Student> studentOptional = studentRepository
                    .findStudentByFullName(fullName);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("Email taken. Try to use another one.");
            }
            student.setFullName(fullName);
        }

        return studentRepository.getStudentById(studentId);
    }
}
