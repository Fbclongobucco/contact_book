package com.buccodev.contact_book.services;

import com.buccodev.contact_book.dto.ContactDTO;
import com.buccodev.contact_book.dto.UserDTO;
import com.buccodev.contact_book.entities.Users;
import com.buccodev.contact_book.repository.UserRepository;
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

import java.util.List;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    public Users saveUser(UserDTO userDTO) {

        try {

            var user = new Users(null, userDTO.name(), passwordEncoder.encode(userDTO.password()), userDTO.email());

            return userRepository.save(user);

        } catch (DataIntegrityViolationException | ConstraintViolationException e) {

            throw new DataBaseExcepions(e.getMessage());

        }

    }

    public UserDTO findUsersById(Long id){

            var user =  userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));

           List<ContactDTO> contactDTOS = user.getContacts().stream().map(x -> new ContactDTO(x.getName(), x.getNumber())).toList();

        return new UserDTO(user.getName(), user.getPassword(), user.getEmail(), contactDTOS);
    }

    public void updateUser(Long id, UserDTO userDTO){

        var user =  userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));

        user.setName(userDTO.name());
        user.setPassword(userDTO.password());
        user.setEmail(userDTO.email());

        try{

            userRepository.save(user);

        } catch (DataIntegrityViolationException | ConstraintViolationException e){

            throw new DataBaseExcepions(e.getMessage());

        }

    }

    public void deleteUsersById(Long id){

        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){

            throw  new DataBaseExcepions(e.getMessage());

        }


    }

    public List<UserDTO> getAllUsers(Integer page, Integer size){

        Pageable pageable = PageRequest.of(page, size);

        Page<Users> users = userRepository.findAll(pageable);

        return users.stream().map(x -> new UserDTO(x.getName(), x.getPassword(), x.getEmail(), x.getContacts().stream()
                .map(c -> new ContactDTO(c.getName(), c.getNumber())).toList())).toList();

    }

    public Boolean validateUser(String email, String password){

            var user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException(email));

            return passwordEncoder.matches(password, user.getPassword());

    }


}
