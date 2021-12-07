package com.ttps.laboratorio.controller;

import com.ttps.laboratorio.dto.request.ContactDTO;
import com.ttps.laboratorio.service.ContactService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "contact")
public class ContactController {

	private final ContactService contactService;

	public ContactController(ContactService contactService) {
		this.contactService = contactService;
	}

	/**
	 * View a list of all contacts.
	 *
	 * @return Returns a list of all contacts with "200 OK".
	 */
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping
	public ResponseEntity<?> listContacts() {
		return ResponseEntity.ok(contactService.getAllContacts());
	}

	/**
	 * Registers a new contact on the data base.
	 *
	 * @param contactDTO contact information
	 * @return status
	 */
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PostMapping
	public ResponseEntity<?> createContact(@Valid @RequestBody ContactDTO contactDTO) {
		contactService.createContact(contactDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping
	public ResponseEntity<?> updateContact(@RequestParam(name = "contactId") @NonNull Long contactID,
																				 @Valid @RequestBody @NonNull ContactDTO contactDTO) {
		contactService.updateContact(contactID, contactDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
