package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.dto.ContactDTO;
import com.ttps.laboratorio.dto.HealthInsuranceDTO;
import com.ttps.laboratorio.service.ContactService;
import com.ttps.laboratorio.service.HealthInsuranceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    /**
     * View a list of all contacts.
     * @return  Returns a list of all contacts with "200 OK".
     */
    @PreAuthorize("hasRole('CONFIGURATOR')")
    @GetMapping
    public ResponseEntity<?> listContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    /**
     * Registers a new contact on the data base.
     * @param contactDTO contact information
     * @return status
     */
    @PreAuthorize("hasRole('CONFIGURATOR')")
    @PostMapping
    public ResponseEntity<?> createContact(@Valid @RequestBody ContactDTO contactDTO) {
        contactService.createContact(contactDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CONFIGURATOR')")
    @PutMapping
    public ResponseEntity<?> updateContact(@RequestParam(name = "contactId") @NonNull Long contactID, @Valid @RequestBody @NonNull ContactDTO contactDTO) {
        contactService.updateContact(contactID, contactDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
