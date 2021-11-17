package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "study")
public class StudyController {

    private final StudyService studyService;

    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    /**
     * View a list of all studies.
     * @return  Returns a list of all studies with "200 OK".
     */
    @PreAuthorize("hasRole('CONFIGURATOR')")
    @GetMapping
    public ResponseEntity<?> listStudies() {
        return ResponseEntity.ok(studyService.getAllStudies());
    }

}
