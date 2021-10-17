package com.ttps.laboratorio.service;

import com.ttps.laboratorio.repository.IContactRepository;

public class ContactService {

  private final IContactRepository contactRepository;

  public ContactService (IContactRepository contactRepository) {
    this.contactRepository = contactRepository;
  }
}
