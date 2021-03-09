package ua.lviv.iot.spring.first.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.lviv.iot.spring.first.rest.business.SubjectService;
import ua.lviv.iot.spring.first.rest.model.Subject;

import java.util.List;

@RequestMapping("/subjects")
@RestController
public class SubjectController {

        private final SubjectService subjectService;

        @Autowired
        public SubjectController(SubjectService subjectService) {
                this.subjectService = subjectService;
        }

        @GetMapping
        public List<Subject> getAllSubjects() {
            return subjectService.findAll();
        }
}
