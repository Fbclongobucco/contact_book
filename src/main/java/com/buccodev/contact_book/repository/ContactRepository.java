package com.buccodev.contact_book.repository;

import com.buccodev.contact_book.entities.Contact;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
