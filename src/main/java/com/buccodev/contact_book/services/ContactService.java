package com.buccodev.contact_book.services;

import com.buccodev.contact_book.dto.ContactDTO;
import com.buccodev.contact_book.entities.Contact;
import com.buccodev.contact_book.repository.ContactRepository;
import com.buccodev.contact_book.services.exceptions.DataBaseExcepions;
import com.buccodev.contact_book.services.exceptions.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ContactRepository contactRepository;

    public Contact saveUser(ContactDTO contactDTO) {

        try {

            var contact = new Contact(null, contactDTO.name(), contactDTO.number());

            return contactRepository.save(contact);

        } catch (DataIntegrityViolationException | ConstraintViolationException e) {

            throw new DataBaseExcepions(e.getMessage());

        }

    }

    public ContactDTO findContactById(Long id){

        var contact =  contactRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));

        return new ContactDTO(contact.getName(), contact.getNumber());
    }

    public void updateUser(Long id, ContactDTO contactDTO){

        var contact =  contactRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));

        contact.setName(contactDTO.name());
        contact.setNumber(contactDTO.number());

        try{

            contactRepository.save(contact);

        } catch (DataIntegrityViolationException | ConstraintViolationException e){

            throw new DataBaseExcepions(e.getMessage());

        }

    }

    public void deleteContactById(Long id){

        try {
            contactRepository.deleteById(id);

        } catch (DataIntegrityViolationException e){

            throw  new DataBaseExcepions(e.getMessage());

        }


    }

    public Page<Contact> getAllContact(Integer page, Integer size){

        Pageable pageable = PageRequest.of(page, size);

        return contactRepository.findAll(pageable);

    }




}
