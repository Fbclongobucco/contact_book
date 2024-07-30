package com.buccodev.contact_book.config;

import com.buccodev.contact_book.entities.Contact;
import com.buccodev.contact_book.entities.Users;
import com.buccodev.contact_book.repository.ContactRepository;
import com.buccodev.contact_book.repository.UserRepository;
import com.buccodev.contact_book.services.ContactService;
import com.buccodev.contact_book.services.UserService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class SeedConfig implements CommandLineRunner {

    @Autowired
    public ContactRepository contactRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public Faker faker;


    @Override
    public void run(String... args) throws Exception {

        for( int i = 0; i < 12; i++ ){

            Users users = new Users(null, faker.name().name(), faker.internet().password(), faker.internet().emailAddress()+i);

            userRepository.save(users);

            for(int j = 0; j < 10; j++){
                Contact contact = new Contact(null, faker.name().name()+j+new Random().hashCode(), faker.number().digits(11));

                contact.setUsers(users);


                contactRepository.save(contact);

                users.getContacts().add(contact);

                userRepository.save(users);
            }

        }


    }
}
