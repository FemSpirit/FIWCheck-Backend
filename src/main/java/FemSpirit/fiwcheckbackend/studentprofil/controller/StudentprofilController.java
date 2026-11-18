package FemSpirit.fiwcheckbackend.studentprofil.controller;

import FemSpirit.fiwcheckbackend.studentprofil.service.StudentprofilService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/studentprofil")

public class StudentprofilController {

    private final StudentprofilService studentprofilService;
    public StudentprofilController(StudentprofilService studentprofilService) {
        this.studentprofilService = studentprofilService;
    }
}
