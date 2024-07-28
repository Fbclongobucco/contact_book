package com.buccodev.contact_book.services;

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

        return new UserDTO(user.getName(), user.getPassword(), user.getEmail());
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

    public Page<Users> getAllUsers(Integer page, Integer size){

        Pageable pageable = PageRequest.of(page, size);

        return userRepository.findAll(pageable);

    }

    public Boolean validateUser(String email, String password){

            var user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException(email));

            return passwordEncoder.matches(password, user.getPassword());

    }


}
