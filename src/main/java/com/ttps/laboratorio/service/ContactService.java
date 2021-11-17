package com.ttps.laboratorio.service;

import com.ttps.laboratorio.dto.ContactDTO;
import com.ttps.laboratorio.entity.Contact;
import com.ttps.laboratorio.exception.NotFoundException;
import com.ttps.laboratorio.repository.IContactRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

	private final IContactRepository contactRepository;

	public ContactService(IContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	/**
	 * Gets all contacts registered.
	 * 
	 * @return List of all the contacts
	 */
	public List<Contact> getAllContacts() {
		return new ArrayList<>(contactRepository.findAll());
	}

	/**
	 * Creates new contact.
	 * 
	 * @param request contact information
	 */
	public Contact createContact(ContactDTO request) {
		Contact contact = new Contact();
		contact.setName(request.getName());
		contact.setEmail(request.getEmail());
		contact.setPhoneNumber(request.getPhoneNumber());
		contactRepository.save(contact);
		return contact;
	}

	/**
	 * Updates an existing contact
	 * 
	 * @param contactID  id from the health insurance to search
	 * @param contactDTO new data to change
	 */
	public void updateContact(Long contactID, ContactDTO contactDTO) {
		Contact contact = contactRepository.findById(contactID)
				.orElseThrow(() -> new NotFoundException("A contact with the id " + contactID + " does not exist."));
		contact.setName(contactDTO.getName());
		contact.setEmail(contactDTO.getEmail());
		contact.setPhoneNumber(contactDTO.getPhoneNumber());
		contactRepository.save(contact);
	}
}
