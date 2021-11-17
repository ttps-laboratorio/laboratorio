package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.dto.StudyDTO;
import com.ttps.laboratorio.entity.Study;
import com.ttps.laboratorio.service.StudyService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "study")
public class StudyController {

  private final StudyService studyService;

  public StudyController(StudyService studyService) {
    this.studyService = studyService;
  }

  @PreAuthorize("hasRole('EMPLOYEE')")
  @GetMapping("/{id}")
  public ResponseEntity<Study> getStudy(@PathVariable(name = "id") @NonNull Long studyId) {
    Study study = studyService.getStudy(studyId);
    return ResponseEntity.ok(study);
  }

  /**
   * View a list of all studys.
   * @return  Returns a list of all studys with "200 OK".
   */
  @PreAuthorize("hasRole('EMPLOYEE')")
  @GetMapping()
  public ResponseEntity<?> listStudies() {
    return ResponseEntity.ok(studyService.getAllStudies());
  }

  /**
   * Modifies an Study on the database.
   * @param studyDTO study information
   * @return status
   */
  @PreAuthorize("hasRole('EMPLOYEE')")
  @PutMapping("/{id}")
  public ResponseEntity<?> updateStudy(@PathVariable(name = "id") @NonNull Long studyID,
                                         @Valid @RequestBody @NonNull StudyDTO studyDTO) {
    studyService.updateStudy(studyID, studyDTO);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
